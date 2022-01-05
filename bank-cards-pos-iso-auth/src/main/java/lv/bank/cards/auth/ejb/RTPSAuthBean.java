package lv.bank.cards.auth.ejb;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.auth.AuthRequestMaster;
import lv.bank.cards.auth.AuthResponse;
import lv.bank.cards.auth.AuthorisationException;
import lv.bank.cards.auth.RTPS.PosISOAuthorization;
import lv.bank.cards.auth.RTPS.PosISOAuthorization1100;
import lv.bank.cards.auth.RTPS.PosISOAuthorization1200;
import lv.bank.cards.auth.RTPS.PosISOAuthorization1420;
import lv.bank.cards.auth.RTPS.PosISOAuthorization1520;
import lv.bank.cards.auth.interfaces.RTPSAuth;
import lv.bank.cards.core.entity.linkApp.PcdAuthBatch;
import lv.bank.cards.core.entity.linkApp.PcdAuthPosIsoHostMessage;
import lv.bank.cards.core.entity.linkApp.PcdAuthSource;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.linkApp.dto.BDCCountAndAmount;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import org.apache.commons.lang.StringUtils;
import org.jboss.ws.api.annotation.WebContext;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static java.nio.charset.Charset.defaultCharset;

@WebService(targetNamespace = "http://auth.cards.bank.lv/", serviceName = "RTPSAuthWSWrapperService", portName = "RTPSAuthWSWrapperPort", wsdlLocation = "/META-INF/wsdl/RTPSAuthWSWrapperService.wsdl")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@WebContext(contextRoot = "BankCardsPosISOAuthWS", urlPattern = "/RTPSAuthWSWrapperPort")
@Stateless
@HandlerChain(file = "/META-INF/handlers.xml")
@NoArgsConstructor
@Slf4j
public class RTPSAuthBean implements RTPSAuth {

    /**
     * The session context
     */
    @EJB(lookup = PcdabaNGManager.JNDI_NAME)
    PcdabaNGManager pcdabaNGManager;

    @WebMethod(action = "simpleReversal")
    public String simpleReversal(String source, long originalId) throws AuthorisationException {
        log.info("simpleReversal [" + source + "," + originalId + "]");
        try {
            PcdAuthSource pcdAuthSource = pcdabaNGManager.getAuthSourceByName(source);
            if (pcdAuthSource == null) {
                String response = "<answer><result>failed</result><reason>No such source [" + source + "]</reason></answer>";
                log.warn(response);
                return response;
            }

            PcdAuthPosIsoHostMessage originalMsg = pcdabaNGManager.getAuthPosIsoMessageById(originalId);
            if (originalMsg.getSource().getSource().compareTo(source) != 0) {
                String response = "<answer><result>failed</result><reason>Original was made from another source [" + originalMsg.getSource().getSource() + "], not [" + source + "]</reason></answer>";
                log.warn(response);
                return response;
            }
            PosISOAuthorization posISOAuth = new PosISOAuthorization1420();

            PcdAuthPosIsoHostMessage pcdAuthPosIsoHostMessage = registerNewAuthorization(posISOAuth, pcdAuthSource);

            posISOAuth.setRequestField(2, originalMsg.getFld002()); // card number
            posISOAuth.setRequestField(4, originalMsg.getFld004().toString()); // original amount
            posISOAuth.setRequestField(11, originalMsg.getFld011()); // STAN
            posISOAuth.setRequestField(14, originalMsg.getFld014()); // Expirity Date
            posISOAuth.setRequestField(30, originalMsg.getFld004().toString()); // original amount

            String thisId = pcdAuthPosIsoHostMessage.getId().toString();
            if (thisId.length() < 8) {
                thisId = "00000000".substring(0, 8 - thisId.length()).concat(thisId);
            }
            ;
            thisId = thisId.substring(thisId.length() - 8);

            posISOAuth.setRequestField(37, new SimpleDateFormat("yyDDD").format(pcdAuthPosIsoHostMessage.getDatetime()).substring(1).concat(thisId));
            posISOAuth.setRequestField(38, originalMsg.getFld038()); // Approval code
            posISOAuth.setRequestField(41, originalMsg.getFld041()); // Terminal ID
            posISOAuth.setRequestField(56,
                    originalMsg.getMti()
                            + originalMsg.getFld011()
                            + originalMsg.getFld012()
                            + (originalMsg.getFld032() == null ? "00" : originalMsg.getFld032())); // Original data

            // Actual authorisation
            posISOAuth.authorize();


            ISOMsg rtpsResponse = posISOAuth.getResponse();

            AuthResponse authResponse = new AuthResponse();
            authResponse.setIso(AuthRequestMaster.fromISOMsg(rtpsResponse));
            if (posISOAuth.isSucessfullyCompleted()) {
                pcdAuthPosIsoHostMessage.setOriginalMsgId(originalMsg.getId());
                originalMsg.setReversalMsgId(pcdAuthPosIsoHostMessage.getId());
                pcdabaNGManager.saveOrUpdate(originalMsg);
            }

            // update info in DB
            updatePcdPosISOAuthData(pcdAuthPosIsoHostMessage, posISOAuth);

            String response = "<answer><trxn-id>" + pcdAuthPosIsoHostMessage.getId() + "</trxn-id>" +
                    "<result>" + (posISOAuth.isSucessfullyCompleted() ? "ok" : "failed") + "</result>" +
                    "<action-code>" + (posISOAuth.getResponse().hasField(39) ? posISOAuth.getResponse().getValue(39) : "") + "</action-code></answer>";
            log.info(response);
            return response;
        } catch (CreateException e) {
            log.error("simpleReversal", e);

            return e.getMessage();
        } catch (ISOException e) {
            String response = "<answer><result>failed</result><reason>" + e.getMessage() + "</reason></answer>";
            log.error(response);
            return response;
        }
    }

    @WebMethod(action = "simpleBDC")
    public String simpleBDC(String source) throws AuthorisationException {
        log.info("simpleBDC[" + source + "]");
        PosISOAuthorization posISOAuth = new PosISOAuthorization1520();

        try {
            PcdAuthSource pcdAuthSource = pcdabaNGManager.getAuthSourceByName(source);
            if (pcdAuthSource == null) {
                String response = "<answer><result>failed</result><reason>No such source [" + source + "]</reason></answer>";
                log.warn(response);
                return response;
            }

            posISOAuth.setRequestField(29, StringUtils.leftPad(pcdAuthSource.getFld29AsString(), 3, "0"));
            pcdAuthSource.incrementFld029();
            pcdabaNGManager.saveOrUpdate(pcdAuthSource);
            PcdAuthPosIsoHostMessage pcdAuthPosIsoHostMessage = registerNewAuthorization(posISOAuth, pcdAuthSource);

            //	 		String thisId = pcdMsg.getId().toString();

            // lets get the date of the last non-reconciled transactions

            PcdAuthBatch pcdBatch = pcdabaNGManager.getNewBatchOpened(/*pcdAuthPosIsoHostMessage.getDatetime(), */pcdAuthSource.getTerminalId(), pcdAuthSource);
            pcdBatch.setPcdAuthPosIsoHostMessage(pcdAuthPosIsoHostMessage);
            pcdBatch.setDatetime(pcdAuthPosIsoHostMessage.getDatetime());
            pcdabaNGManager.saveOrUpdate(pcdBatch);

            HashMap<String, BDCCountAndAmount> bdcData = pcdabaNGManager.getReconsiled(pcdBatch);

            // Assign correct values to corresponding fields and save the batch
            String thisId = pcdAuthPosIsoHostMessage.getId().toString();
            if (thisId.length() < 8) {
                thisId = "00000000".substring(0, 8 - thisId.length()).concat(thisId);
            }
            ;
            thisId = thisId.substring(thisId.length() - 8);

            posISOAuth.setRequestField(37, new SimpleDateFormat("yyDDD").format(pcdAuthPosIsoHostMessage.getDatetime()).substring(1)
                    .concat(thisId));

            posISOAuth.setRequestField(74, "0000000000");
            pcdBatch.setFld074(0L);

            posISOAuth.setRequestField(77, "0000000000");
            pcdBatch.setFld077(0L);

            /*
             * together with filling of fields we need calculate field 97
             */

            Long fld97value = 0L;

            //debets
            if (bdcData.containsKey("1200")) {
                BDCCountAndAmount data = bdcData.get("1200");
                Long sum = data.getSum();
                String count = data.getCount().toString();

                posISOAuth.setRequestField(76, "0000000000".substring(0, 10 - count.length()).concat(count));
                posISOAuth.setRequestField(88, "0000000000000000".substring(0, 16 - sum.toString().length()).concat(sum.toString()));

                pcdBatch.setFld076(data.getCount());
                pcdBatch.setFld088(sum);


                fld97value -= sum;
            } else {
                posISOAuth.setRequestField(76, "0000000000"); //debits reversal
                posISOAuth.setRequestField(88, "0000000000000000");
            }


            //debets reversal
            if (bdcData.containsKey("1420")) {
                BDCCountAndAmount data = bdcData.get("1420");

                Long sum = data.getSum();
                String count = data.getCount().toString();

                posISOAuth.setRequestField(75, "0000000000".substring(0, 10 - count.length()).concat(count));
                posISOAuth.setRequestField(87, "0000000000000000".substring(0, 16 - sum.toString().length()).concat(sum.toString()));

                pcdBatch.setFld075(data.getCount());
                pcdBatch.setFld087(sum);

                fld97value += sum;
            } else {
                posISOAuth.setRequestField(75, "0000000000"); //debits reversal
                posISOAuth.setRequestField(87, "0000000000000000");
            }


            posISOAuth.setRequestField(81, "0000000000");

            posISOAuth.setRequestField(86, "0000000000000000");
            posISOAuth.setRequestField(89, "0000000000000000");
            posISOAuth.setRequestField(90, "0000000000");


            String sign = ((fld97value < 0) ? "D" : "C");

            fld97value = Math.abs(fld97value);

            posISOAuth.setRequestField(97, sign + "0000000000000000".substring(0, 16 - fld97value.toString().length()).concat(fld97value.toString()));

            pcdBatch.setFld097x(sign);
            pcdBatch.setFld097(fld97value);

            // save stats to DB


            if (pcdAuthSource.getMerchantId() != null) {
                posISOAuth.setRequestField(42, pcdAuthSource.getMerchantId());
                pcdBatch.setMerchantId(pcdAuthSource.getMerchantId());
            }
            if (pcdAuthSource.getTerminalId() != null) {
                posISOAuth.setRequestField(41, pcdAuthSource.getTerminalId());
                pcdBatch.setTerminalId(pcdAuthSource.getTerminalId());
            }

            // Actual comms
            posISOAuth.authorize();

            pcdBatch.setAuthId(pcdAuthPosIsoHostMessage.getId());
            pcdAuthPosIsoHostMessage.setPcdAuthBatch(pcdBatch);

            updatePcdPosISOAuthData(pcdAuthPosIsoHostMessage, posISOAuth);

            pcdabaNGManager.saveOrUpdate(pcdBatch);

            //AuthResponse authResponse = new AuthResponse();
            //authResponse.setIso(AuthRequestMaster.fromISOMsg(posISOAuth.getResponse()));
            String response = "<answer><trxn-id>" + pcdAuthPosIsoHostMessage.getId() + "</trxn-id>"
                    + "<result>" + (posISOAuth.isSucessfullyCompleted() ? "ok" : "failed") + "</result>"
                    + "<action-code>" + (posISOAuth.getResponse().hasField(39) ? posISOAuth.getResponse().getValue(39) : "") + "</action-code>"
                    + "</answer>";
            log.info(response);
            return response;
        } catch (CreateException e) {

            log.error("simpleBDC", e);
            String response = "<answer><result>failed</result><reason>" + e.getMessage() + "</reason></answer>";
            log.warn(response, e);
            return response;
        }
    }

    @WebMethod(action = "simplePurchase")
    public String simplePurchase(String source, String card, String amount) throws AuthorisationException {
        log.info("simplePurchase [" + source + "," + card + "," + amount + "]");
        try {
            /*
             * We need to get an expiration date
             */
            PcdCard pcdCard = pcdabaNGManager.getCardByCardNumber(card);
            if (pcdCard == null) {
                String response = "<answer><result>failed</result><reason>No information available about card [" + card + "]</reason></answer>";
                log.warn(response);
                return response;
            }

            PosISOAuthorization posISOAuth = new PosISOAuthorization1200(Long.parseLong(amount), card, new SimpleDateFormat("yyMM").format(pcdCard.getExpiry1()));

            /*
             * Get source to fill in merchant and terminal
             */
            PcdAuthSource pcdAuthSource = pcdabaNGManager.getAuthSourceByName(source);
            if (pcdAuthSource == null) {
                String response = "<answer><result>failed</result><reason>No such source [" + source + "]</reason></answer>";
                log.warn(response);
                return response;
            }

            if (pcdAuthSource.getTerminalId() != null)
                posISOAuth.setRequestField(41, pcdAuthSource.getTerminalId());

            if (pcdAuthSource.getMerchantId() != null)
                posISOAuth.setRequestField(42, pcdAuthSource.getMerchantId());

            if (pcdAuthSource.getCmi() != null)
                posISOAuth.setRequestField(32, pcdAuthSource.getCmi());

            if (pcdAuthSource.getCurrency() != null)
                posISOAuth.setRequestField(49, pcdAuthSource.getCurrency());

            // Save to db message to get internal ID necessary for generation of other fields
            PcdAuthPosIsoHostMessage pcdAuthPosIsoHostMessage = registerNewAuthorization(posISOAuth, pcdAuthSource);

            String thisId = pcdAuthPosIsoHostMessage.getId().toString();
            if (thisId.length() < 8) {
                thisId = "00000000".substring(0, 8 - thisId.length()).concat(thisId);
            }
            thisId = thisId.substring(thisId.length() - 8);

            // Fill required fields
            posISOAuth.setRequestField(29, StringUtils.leftPad(pcdAuthSource.getFld29AsString(), 3, "0"));
            posISOAuth.setRequestField(37, new SimpleDateFormat("yyDDD").format(pcdAuthPosIsoHostMessage.getDatetime()).substring(1).concat(thisId));

            // Do all the fancy technical stuff
            posISOAuth.authorize();

            // Update information in database
            updatePcdPosISOAuthData(pcdAuthPosIsoHostMessage, posISOAuth);

            //AuthResponse authResponse = new AuthResponse();
            //ISOMsg rtpsResponse = posISOAuth.getResponse();
            //authResponse.setIso(AuthRequestMaster.fromISOMsg(rtpsResponse));
            String response = "<answer><trxn-id>" + pcdAuthPosIsoHostMessage.getId() + "</trxn-id>"
                    + "<result>" + (posISOAuth.isSucessfullyCompleted() ? "ok" : "failed") + "</result>"
                    + "<auth-id>" + (posISOAuth.getResponse().hasField(38) ? posISOAuth.getResponse().getValue(38) : "") + "</auth-id>"
                    + "<action-code>" + (posISOAuth.getResponse().hasField(39) ? posISOAuth.getResponse().getValue(39) : "") + "</action-code>"
                    + "</answer>";
            log.info(response);
            return response;

        } catch (CreateException e) {
            String response = "<answer><result>failed</result><reason>" + e.getMessage() + "</reason></answer>";
            log.error(response, e);
            return response;
        }

    }

    @WebMethod(action = "balanceInquiry")
    public String balanceInquiry(String source, String card) throws AuthorisationException {
        log.info("balanceInquiry [" + source + "," + card + "]");
        /*
         * We need to get an expiration date
         */
        PcdCard pcdCard = pcdabaNGManager.getCardByCardNumber(card);
        if (pcdCard == null) {
            String response = "<answer><result>failed</result><reason>No information available about card [" + card + "]</reason></answer>";
            log.warn(response);
            return response;
        }

        PosISOAuthorization posISOAuth = new PosISOAuthorization1100(card, new SimpleDateFormat("yyMM").format(pcdCard.getExpiry1()));

        /*
         * Get source to fill in merchant and terminal
         */
        PcdAuthSource pcdAuthSource = pcdabaNGManager.getAuthSourceByName(source);
        if (pcdAuthSource == null) {
            String response = "<answer><result>failed</result><reason>No such source [" + source + "]</reason></answer>";
            log.warn(response);
            return response;
        }

        if (pcdAuthSource.getTerminalId() != null)
            posISOAuth.setRequestField(41, pcdAuthSource.getTerminalId());

        if (pcdAuthSource.getMerchantId() != null)
            posISOAuth.setRequestField(42, pcdAuthSource.getMerchantId());

        if (pcdAuthSource.getCmi() != null)
            posISOAuth.setRequestField(32, pcdAuthSource.getCmi());

        if (pcdAuthSource.getCurrency() != null)
            posISOAuth.setRequestField(49, pcdAuthSource.getCurrency());

//			// Save to db message to get internal ID necessary for generation of other fields
//			PcdAuthPosIsoHostMessage pcdAuthPosIsoHostMessage = registerNewAuthorization(posISOAuth, pcdAuthSource);
//
//			String thisId = pcdAuthPosIsoHostMessage.getId().toString();
//			if (thisId.length() < 8) {
//				thisId="00000000".substring(0, 8 - thisId.length()).concat(thisId);
//			}
//			thisId = thisId.substring(thisId.length()-8);

        // Fill required fields
//			posISOAuth.setRequestField(29, thisId.substring(0,3));
//			posISOAuth.setRequestField(37, new SimpleDateFormat("yyDDD").format(pcdAuthPosIsoHostMessage.getDatetime()).substring(1).concat(thisId));

        // Do all the fancy technical stuff
        posISOAuth.authorize();

        // Update information in database
//			updatePcdPosISOAuthData(pcdAuthPosIsoHostMessage, posISOAuth);

        //AuthResponse authResponse = new AuthResponse();
        //ISOMsg rtpsResponse = posISOAuth.getResponse();
        //authResponse.setIso(AuthRequestMaster.fromISOMsg(rtpsResponse));
        String response = "<answer>"
                + "<result>" + (posISOAuth.isSucessfullyCompleted() ? "ok" : "failed") + "</result>"
                + "<auth-id>" + (posISOAuth.getResponse().hasField(38) ? posISOAuth.getResponse().getValue(38) : "") + "</auth-id>"
                + "<action-code>" + (posISOAuth.getResponse().hasField(39) ? posISOAuth.getResponse().getValue(39) : "") + "</action-code>"
                + "<amounts-additional>" + (posISOAuth.getResponse().hasField(54) ? posISOAuth.getResponse().getValue(54) : "") + "</amounts-additional>"
                + "</answer>";
        log.info(response);
        return response;

    }

    // create new record to get ID
    private PcdAuthPosIsoHostMessage registerNewAuthorization(PosISOAuthorization auth, PcdAuthSource source) throws CreateException, AuthorisationException {
        PcdAuthPosIsoHostMessage pcdAuthPosIsoHostMessage = new PcdAuthPosIsoHostMessage();
        pcdAuthPosIsoHostMessage.setSource(source);
        pcdAuthPosIsoHostMessage.setDatetime(new Date());
        pcdabaNGManager.saveOrUpdate(pcdAuthPosIsoHostMessage);
        return pcdAuthPosIsoHostMessage;
    }

    // fill fields of DB record and save to DB
    private void updatePcdPosISOAuthData(PcdAuthPosIsoHostMessage record, PosISOAuthorization attempt) throws AuthorisationException {
        try {
            ISOMsg request = attempt.getRequest();
            ISOMsg response = attempt.getResponse();

            record.setToRtps(new String(request.pack(), defaultCharset()));
            record.setToClnt(new String(response.pack(), defaultCharset()));

            if (request.hasField(2)) record.setFld002(request.getValue(2).toString());
            if (request.hasField(3)) record.setFld003(request.getValue(3).toString());
            if (request.hasField(4)) record.setFld004(Long.parseLong(request.getValue(4).toString()));
            //			if (request.hasField(6)) record.setFld006(Long.parseLong(request.getValue(6).toString()));
            if (response.hasField(11)) record.setFld011(response.getValue(11).toString());
            if (request.hasField(12)) record.setFld012(request.getValue(12).toString());
            if (request.hasField(14)) record.setFld014(request.getValue(14).toString());
            if (response.hasField(22)) record.setFld022(response.getValue(22).toString());
            if (response.hasField(32)) record.setFld032(response.getValue(32).toString());
            if (request.hasField(37)) record.setFld037(request.getValue(37).toString());
            if (response.hasField(38)) record.setFld038(response.getValue(38).toString());
            if (response.hasField(39)) record.setFld039(response.getValue(39).toString());
            if (request.hasField(41)) record.setFld041(request.getValue(41).toString());
            if (request.hasField(42)) record.setFld042(request.getValue(42).toString());


            record.setMti(request.getMTI());
            record.setRespMti(response.getMTI());

            pcdabaNGManager.saveOrUpdate(record);

        } catch (ISOException e) {
            log.error("updatePcdPosISOAuthData", e);
            throw new AuthorisationException(e);
        }
    }

}
