package lv.bank.cards.soap.handlers;

import lombok.Setter;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.impl.CardDAOHibernate;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DeliveryDetailsHelperBase;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import org.apache.commons.lang.StringUtils;

import static lv.bank.cards.core.utils.TextUtils.removeEscapeCharacter;

public abstract class UpdateCardDeliveryDetailsBaseHandler extends SubRequestHandler {

    @Setter
    protected CMSCallAPIWrapper wrap;
    @Setter
    protected CardDAO cardDAO;
    @Setter
    protected CardsDAO cardsDAO;

    public UpdateCardDeliveryDetailsBaseHandler() {
        super();
        cardsDAO = new CardsDAOHibernate();
        cardDAO = new CardDAOHibernate();
        wrap = new CMSCallAPIWrapper();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String card = getStringFromNode("/do/card");
        boolean escape = "TRUE".equals(StringUtils.upperCase(getStringFromNode("/do/remove-escape-char")));
        String dlv_address = removeEscapeCharacter(getStringFromNode("/do/delivery-details/dlv_address"), escape);
        String dlv_addr_code = removeEscapeCharacter(getStringFromNode("/do/delivery-details/dlv_addr_code"), escape);
        String dlv_addr_country = removeEscapeCharacter(getStringFromNode("/do/delivery-details/dlv_addr_country"), escape);
        String dlv_addr_city = removeEscapeCharacter(getStringFromNode("/do/delivery-details/dlv_addr_city"), escape);
        String dlv_addr_street1 = removeEscapeCharacter(getStringFromNode("/do/delivery-details/dlv_addr_street1"), escape);
        String dlv_addr_street2 = removeEscapeCharacter(getStringFromNode("/do/delivery-details/dlv_addr_street2"), escape);
        String dlv_addr_zip = removeEscapeCharacter(getStringFromNode("/do/delivery-details/dlv_addr_zip"), escape);
        String dlv_company = removeEscapeCharacter(getStringFromNode("/do/delivery-details/dlv_company"), escape);
        String dlv_language = removeEscapeCharacter(getStringFromNode("/do/delivery-details/dlv_language"), escape);
        String dlv_branch = getStringFromNode("/do/delivery-details/branch");

        if (!CardUtils.cardCouldBeValid(card))
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);

        if (StringUtils.isBlank(dlv_address) && StringUtils.isBlank(dlv_addr_code) && StringUtils.isBlank(dlv_addr_country) &&
                StringUtils.isBlank(dlv_addr_city) && StringUtils.isBlank(dlv_addr_street1) && StringUtils.isBlank(dlv_addr_street2) &&
                StringUtils.isBlank(dlv_addr_zip) && StringUtils.isBlank(dlv_company) && StringUtils.isBlank(dlv_language) && StringUtils.isBlank(dlv_branch)) {
            throw new RequestFormatException("Do not have delivery details", this);
        }

        if (StringUtils.isBlank(dlv_branch)) {
            throw new RequestFormatException("Delivery details branch is mandatory", this);
        }

        DeliveryDetailsHelperBase details = validateAndGetDeliveryDetailsHelper(dlv_language, dlv_addr_country, dlv_addr_city, dlv_addr_street1,
                dlv_addr_street2, dlv_addr_zip, dlv_company, dlv_addr_code, dlv_branch);

        PcdCard thisCard = cardsDAO.findByCardNumber(card);
        if (thisCard == null) {
            throw new RequestProcessingException(ErrorConstants.cantFindCard + " (pcd)", this);
        }

        String detailsString = details.getDetails();
        String branch = resolveBranch(dlv_branch);

        // Update in CMS
        CMSCallAPIWrapper.UpdateCardXML updateCardXML = wrap.new UpdateCardXML(card, thisCard.getBankC(), thisCard.getGroupc());
        updateCardXML.setElement("U_BFIELD1", detailsString);
        updateCardXML.setElement("U_COD10", branch);
        CMSCallAPIWrapper.UpdateDBWork work = wrap.new UpdateDBWork();
        work.setInputXML(updateCardXML.getDocument());
        String response = cardDAO.doWork(work);
        if (!"success".equals(response)) {
            throw new RequestProcessingException(StringUtils.substring(
                    StringUtils.substringBetween(response, "<ERROR_DESC>", "</ERROR_DESC>"), 0, 200), this);
        }

        // Update in LinkApp
        thisCard.setUBField1(detailsString);
        thisCard.setUCod10(branch);
        cardsDAO.saveOrUpdate(thisCard);

        ResponseElement cardElement = createElement("update-card-delivery-details");
        cardElement.createElement("card", card);
        details = getDeliveryDetailsHelperBaseFromString(detailsString);
        ResponseElement detailElement = cardElement.createElement("delivery-details");
        detailElement.createElement("dlv_address", details.getAddressString());
        detailElement.createElement("dlv_addr_country", details.getCountry());
        detailElement.createElement("dlv_addr_city", details.getRegion());
        detailElement.createElement("dlv_addr_street1", details.getCity());
        detailElement.createElement("dlv_addr_street2", details.getAddress());
        detailElement.createElement("dlv_addr_zip", details.getZipCode());
        detailElement.createElement("dlv_company", details.getAdditionalFields());
        detailElement.createElement("dlv_language", details.getLanguage());
        addToResponse(detailElement, dlv_branch, details);
    }

    public abstract String resolveBranch(String dlv_branch) throws RequestProcessingException;

    public abstract DeliveryDetailsHelperBase validateAndGetDeliveryDetailsHelper(
            String dlv_language, String dlv_addr_country, String dlv_addr_city, String dlv_addr_street1,
            String dlv_addr_street2, String dlv_addr_zip, String dlv_company, String dlv_addr_code, String dlv_branch
    ) throws RequestFormatException;

    public abstract DeliveryDetailsHelperBase getDeliveryDetailsHelperBaseFromString(String detailsString);

    public abstract void addToResponse(ResponseElement detailElement, String dlv_branch, DeliveryDetailsHelperBase details);

}
