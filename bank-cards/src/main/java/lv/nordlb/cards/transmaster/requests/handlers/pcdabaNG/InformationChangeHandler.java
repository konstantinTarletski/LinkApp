package lv.nordlb.cards.transmaster.requests.handlers.pcdabaNG;

import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCountry;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateAccountXML;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateCardXML;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper.UpdateDBWork;
import lv.bank.cards.core.vendor.api.cms.soap.CMSSoapAPIException;
import lv.bank.cards.core.vendor.api.cms.soap.interfaces.CMSSoapAPIWrapper;
import lv.bank.cards.core.vendor.api.cms.soap.types.RowTypeEditAgreementRequest;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;
import lv.nordlb.cards.transmaster.bo.interfaces.CommonManager;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class InformationChangeHandler extends SubRequestHandler {

    public final static String DEFAULT_PRODUCT = "0";

    private static final List<String> COND_SET_STARTS = Arrays.asList("1", "2", "5");

    private static final List<String> RENEW_ALLOWED_STATUSES = Arrays.asList("J", "E", "D", "R", "G");

    public static final String CONTACTLESS_CHIP_TAG = "BF5B";
    public static final String CONTACTLESS_ENABLED = "DF01020000";
    public static final String CONTACTLESS_DISABLED = "DF0102C000";

    public static final int CONTACTLESS_BLOCKED = 0;
    public static final int CONTACTLESS_ACTIVE = 1;
    public static final int CONTACTLESS_PENDING_ACTIVATION = 2;
    public static final int CONTACTLESS_PENDING_BLOCKING = 3;

    protected final PcdabaNGManager pcdabaNGManager;
    protected final CardManager cardManager;
    protected final CommonManager commonManager;
    protected final CMSSoapAPIWrapper soapAPIWrapper;
    //TODO replace it with soapAPIWrapper
    protected CMSCallAPIWrapper wrap;

    public InformationChangeHandler() throws RequestPreparationException {
        super();
        wrap = new CMSCallAPIWrapper();
        try {
            soapAPIWrapper = (CMSSoapAPIWrapper) new InitialContext().lookup(CMSSoapAPIWrapper.JNDI_NAME);
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
            cardManager = (CardManager) new InitialContext().lookup(CardManager.JNDI_NAME);
            commonManager = (CommonManager) new InitialContext().lookup(CommonManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        ResponseElement responseElement = createElement("information-change");

        for (Object cardObject : r.getReq().getRootElement().elements("card")) {
            String comment = null;
            Element cardElement = (Element) cardObject;
            String cardNumber = cardElement.attributeValue("pan");

            PcdCard pcdCard = pcdabaNGManager.getCardByCardNumber(cardNumber);

            if (pcdCard == null)
                throw new RequestProcessingException(ErrorConstants.cantFindCard + " (pcd)", this);

            IzdCard izdCard = cardManager.getIzdCardByCardNumber(pcdCard.getCard());
            if (izdCard == null)
                throw new RequestProcessingException(ErrorConstants.cantFindCard + " (izd)", this);

            // Update card in CMS
            UpdateCardXML updateCardXML = wrap.new UpdateCardXML(pcdCard.getCard(), pcdCard.getBankC(), pcdCard.getGroupc());
            boolean hasCardChanges = false;

            String password = getElementValue(cardElement, "card-password");
            if (!StringUtils.isBlank(password)) {
                pcdCard.setMName(password);
                updateCardXML.setElement("M_NAME", password);
                hasCardChanges = true;
            }

            RowTypeEditAgreementRequest updateAgreement = new RowTypeEditAgreementRequest();
            updateAgreement.setAGRENOM(new BigDecimal(pcdCard.getPcdAgreement().getAgreement()));
            updateAgreement.setPRODUCT(DEFAULT_PRODUCT);
            boolean hasAgreementChanges = false;

            UpdateAccountXML updateAccountXML = wrap.new UpdateAccountXML(izdCard.getIzdClAcct().getIzdAccount().getAccountNo().toString(),
                    pcdCard.getBankC(), pcdCard.getGroupc());
            boolean hasAccountChanges = false;

            String language = getElementValue(cardElement, "language");
            if (!StringUtils.isBlank(language)) {
                pcdCard.getPcdAgreement().setRepLang(language);
                //updateAgreementXML.setElement("REP_LANG", language);
                updateAgreement.setREPLANG(language);
                hasAgreementChanges = true;
            }

            String statementMode = getElementValue(cardElement, "statement-mode");
            if (!StringUtils.isBlank(statementMode)) {
                pcdCard.getPcdAgreement().setDistribMode(statementMode);
                //updateAgreementXML.setElement("DISTRIB_MODE", statementMode);
                updateAgreement.setDISTRIBMODE(statementMode);
                hasAgreementChanges = true;
                String condSet = izdCard.getIzdClAcct().getIzdAccount().getIzdAccParam().getCondSet();
                String newCondSet = condSet;
                if (!StringUtils.isBlank(condSet) && COND_SET_STARTS.contains(condSet.substring(0, 1))) {
                    if ("00".equals(statementMode)) {
                        newCondSet = "1" + condSet.substring(1);
                    } else if ("11".equals(statementMode)) {
                        newCondSet = "2" + condSet.substring(1);
                    } else if ("99".equals(statementMode)) {
                        newCondSet = "5" + condSet.substring(1);
                    }
                    if (!condSet.equals(newCondSet)) {
                        //Change cond set
                        for (PcdAccount a : pcdCard.getPcdAccounts()) {
                            if (a.getCardAcct().equals(izdCard.getIzdClAcct().getCardAcct()))
                                a.getPcdAccParam().setCondSet(newCondSet);
                        }
                        hasAccountChanges = true;
                        updateAccountXML.setElement("COND_SET", newCondSet);
                    }
                }

            }

            String street = getElementValue(cardElement, "stmt-street");
            if (!StringUtils.isBlank(street)) {
                pcdCard.getPcdAgreement().setStreet(street);
                updateAgreement.setSTREET(street);
                hasAgreementChanges = true;
            }

            String city = getElementValue(cardElement, "stmt-city");
            if (!StringUtils.isBlank(city)) {
                pcdCard.getPcdAgreement().setCity(city);
                updateAgreement.setCITY(city);
                hasAgreementChanges = true;
            }

            String zip = getElementValue(cardElement, "stmt-zip");
            if (!StringUtils.isBlank(zip)) {
                pcdCard.getPcdAgreement().setPostInd(zip);
                updateAgreement.setPOSTIND(zip);
                hasAgreementChanges = true;
            }

            String country = getElementValue(cardElement, "stmt-country");
            if (!StringUtils.isBlank(country)) {
                String countryToSave = null;
                if (country.length() == 2) {
                    try {
                        IzdCountry izdCountry = commonManager.getIzdCountryByShortCountryCode(country);
                        countryToSave = izdCountry.getCountry();
                    } catch (DataIntegrityException e) {
                        throw new RequestProcessingException(e);
                    }
                } else {
                    countryToSave = country;
                }
                pcdCard.getPcdAgreement().setCountry(countryToSave);
                updateAgreement.setCOUNTRY(countryToSave);
                hasAgreementChanges = true;
            }

            if (cardElement.element("sms-fee-conditions") != null) {
                String smsFee = getElementValue(cardElement, "sms-fee-conditions");
                if (!StringUtils.isBlank(smsFee))
                    updateCardXML.setElement("u_acode11", smsFee);
                else
                    updateCardXML.setElementToNull("u_acode11");
                pcdCard.setUACode11(smsFee);
                hasCardChanges = true;
            }

            String autoRenew = getElementValue(cardElement, "auto-renew");
            if (!StringUtils.isBlank(autoRenew)) {
                if ("0".equals(autoRenew)) {
                    if (RENEW_ALLOWED_STATUSES.contains(izdCard.getRenew())) {
                        pcdCard.setRenewOld(izdCard.getRenew());
                        pcdCard.setRenew("N");
                        updateCardXML.setElement("RENEW", "N");
                        hasCardChanges = true;
                    } else if ("N".equals(izdCard.getRenew())) {
                        comment = "Card is already marked as not renewable";
                    } else {
                        throw new RequestProcessingException("Change of status is prohibited during card embossing / PIN reminder processing");
                    }
                } else if ("1".equals(autoRenew)) {
                    if ("N".equals(izdCard.getRenew())) {
                        String newStatus = pcdCard.getRenewOld() == null ? "J" : pcdCard.getRenewOld();
                        pcdCard.setRenew(newStatus);
                        updateCardXML.setElement("RENEW", newStatus);
                        hasCardChanges = true;
                    } else {
                        throw new RequestProcessingException("Renew status must be N to set autorenew true");
                    }
                } else {
                    throw new RequestProcessingException("Unknown auto-renew status " + autoRenew);
                }
            }

            if (hasAgreementChanges) {
                try {
                    soapAPIWrapper.editAgreement(updateAgreement);
                } catch (CMSSoapAPIException e) {
                    throw new RequestProcessingException(e.getMessage(), this);
                }
            }

            if (hasCardChanges) {
                UpdateDBWork work = wrap.new UpdateDBWork();
                work.setInputXML(updateCardXML.getDocument());
                String response = cardManager.doWork(work);
                if (!"success".equals(response)) {
                    throw new RequestProcessingException(StringUtils.substring(
                            StringUtils.substringBetween(response, "<ERROR_DESC>",
                                    "</ERROR_DESC>"), 0, 200), this);
                }
            }

            if (hasAccountChanges) {
                UpdateDBWork work = wrap.new UpdateDBWork();
                work.setInputXML(updateAccountXML.getDocument());
                String response = cardManager.doWork(work);
                if (!"success".equals(response)) {
                    throw new RequestProcessingException(StringUtils.substring(
                            StringUtils.substringBetween(response, "<ERROR_DESC>",
                                    "</ERROR_DESC>"), 0, 200), this);
                }
            }

            String contactless = getElementValue(cardElement, "contactless");
            if (!StringUtils.isBlank(contactless)) {
                if (pcdCard.getContactless() != null &&
                        (pcdCard.getContactless().equals(CONTACTLESS_BLOCKED) ||
                                pcdCard.getContactless().equals(CONTACTLESS_ACTIVE))) {
                    try {
                        if (Integer.toString(CONTACTLESS_ACTIVE).equals(contactless) &&
                                pcdCard.getContactless().equals(CONTACTLESS_BLOCKED)) {
                            wrap.setChipTagValue(izdCard.getCard(), CONTACTLESS_CHIP_TAG, CONTACTLESS_ENABLED);
                            pcdCard.setContactless(CONTACTLESS_PENDING_ACTIVATION);
                        } else if (Integer.toString(CONTACTLESS_BLOCKED).equals(contactless) &&
                                pcdCard.getContactless().equals(CONTACTLESS_ACTIVE)) {
                            wrap.setChipTagValue(izdCard.getCard(), CONTACTLESS_CHIP_TAG, CONTACTLESS_DISABLED);
                            pcdCard.setContactless(CONTACTLESS_PENDING_BLOCKING);
                        } else {
                            comment = "Did not change contactless status";
                        }
                    } catch (CMSCallAPIException e) {
                        throw new RequestProcessingException(e.getMessage(), this);
                    }
                } else {
                    comment = "Cannot change contactless status if current status is not 0 or 1";
                }
            }
            ResponseElement cardResp = responseElement.createElement("card");
            cardResp.addAttribute("pan", cardNumber);
            cardResp.createElement("status", "OK");
            if (comment != null)
                cardResp.createElement("comment", comment);
        }
    }

    private String getElementValue(Element cardElement, String tag) {
        Element element = cardElement.element(tag);
        if (element != null)
            return element.getText();
        else
            return null;

    }
}
