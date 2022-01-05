/*
 * Created on 2005.10.6
 */
package lv.nordlb.cards.transmaster.requests.handlers.cms;

import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdRepLang;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;
import lv.nordlb.cards.pcdabaNG.interfaces.PcdabaNGManager;
import lv.nordlb.cards.transmaster.bo.interfaces.CardManager;
import org.apache.commons.lang.StringUtils;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author sibanovs
 */
public class ManageSMSDestination extends SubRequestHandler {
    private CardManager cardManager;
    private PcdabaNGManager pcdabaNGManager;

    public ManageSMSDestination() throws RequestPreparationException {
        super();
        try {
            cardManager = (CardManager) new InitialContext().lookup(CardManager.JNDI_NAME);
            pcdabaNGManager = (PcdabaNGManager) new InitialContext().lookup(PcdabaNGManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String inCard = getStringFromNode("/do/card");
        String inTemplate = getStringFromNode("/do/template");
        String inMobile = getStringFromNode("/do/mobile");
        String inRepLang = getStringFromNode("/do/lang-code");
        String outTemplate = "";
        String outMobile = "";

        if (!CardUtils.cardCouldBeValid(inCard))
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);

        PcdCard thisCard = pcdabaNGManager.getCardByCardNumber(inCard);
        if (thisCard == null)
            throw new RequestProcessingException(ErrorConstants.cantFindCard + " (pcd)", this);


        IzdCard izdCard = cardManager.getIzdCardByCardNumber(inCard);
        if (izdCard == null)
            throw new RequestProcessingException(ErrorConstants.cantFindCard + " (izd)", this);

        if (inMobile != null) {
            if (inMobile.length() == 0) {
                thisCard.setUField7(null);
                izdCard.setUField7(null);
            } else if ((inTemplate != null) && ((inTemplate.length() == 1))) {
                thisCard.setUField7(inTemplate + ":" + inMobile);
                izdCard.setUField7(inTemplate + ":" + inMobile);
            }
        }
        try {

            if (!StringUtils.isBlank(inRepLang) && !inRepLang.equals(izdCard.getIzdAgreement().getRepLang())) {
                izdCard.getIzdAgreement().setRepLang(inRepLang);
                cardManager.saveOrUpdate(izdCard.getIzdAgreement());

                //thisCard.getPcdAgreement().setRepLang(inRepLang);
                PcdRepLang pcdRepLang = pcdabaNGManager.getRepLangByLangCode(inRepLang);

                if (pcdRepLang != null) {
                    thisCard.getPcdAgreement().setPcdRepLang(pcdRepLang);
                    thisCard.getPcdAgreement().setRepLang(inRepLang);
                }

                pcdabaNGManager.saveOrUpdate(thisCard.getPcdAgreement());
            }

            pcdabaNGManager.saveOrUpdate(thisCard);
            cardManager.saveOrUpdate(izdCard);
        } catch (DataIntegrityException e) {
            throw new RequestProcessingException(ErrorConstants.dbException, this);
        }

        ResponseElement cardElement = createElement("card-sms-destination");


        if (thisCard.getUField7() != null) {
            if (thisCard.getUField7().contains(":")) {
                outTemplate = thisCard.getUField7().substring(0, 1);
                outMobile = thisCard.getUField7().substring(2);
            } else {
                outTemplate = "f";
                outMobile = thisCard.getUField7();
            }
        }
        cardElement.createElement("template", outTemplate);
        cardElement.createElement("mobile", outMobile);
        cardElement.createElement("card", inCard);
        cardElement.createElement("lang-name", thisCard.getPcdAgreement().getPcdRepLang().getName());
        cardElement.createElement("lang-code", thisCard.getPcdAgreement().getPcdRepLang().getComp_id().getLanCode());
    }

}
