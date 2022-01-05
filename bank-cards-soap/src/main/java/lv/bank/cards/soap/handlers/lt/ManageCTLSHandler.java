package lv.bank.cards.soap.handlers.lt;

import lombok.Setter;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIException;
import lv.bank.cards.core.vendor.api.cms.db.CMSCallAPIWrapper;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import org.apache.commons.lang.StringUtils;

public class ManageCTLSHandler extends SubRequestHandler {

    public static final String CONTACTLESS_CHIP_TAG = "BF5B";
    public static final String CONTACTLESS_ENABLED = "DF01020000";
    public static final String CONTACTLESS_DISABLED = "DF0102C000";

    public static final int CONTACTLESS_BLOCKED = 0;
    public static final int CONTACTLESS_ACTIVE = 1;
    public static final int CONTACTLESS_PENDING_ACTIVATION = 2;
    public static final int CONTACTLESS_PENDING_BLOCKING = 3;

    @Setter
    private CMSCallAPIWrapper wrap;
    @Setter
    private CardsDAO cardsDAO;

    public ManageCTLSHandler() {
        super();
        wrap = new CMSCallAPIWrapper();
        cardsDAO = new CardsDAOHibernate();
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String card = getStringFromNode("/do/card");
        String contactless = getStringFromNode("/do/contactless");

        if (!CardUtils.cardCouldBeValid(card))
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);

        PcdCard pcdCard = cardsDAO.findByCardNumber(card);
        if (pcdCard == null)
            throw new RequestProcessingException(ErrorConstants.cantFindCard + " (pcd)", this);

        if (!StringUtils.isBlank(contactless)) {
            if (pcdCard.getContactless() != null &&
                    (pcdCard.getContactless().equals(CONTACTLESS_BLOCKED) || pcdCard.getContactless().equals(1))) {
                try {
                    if (Integer.toString(CONTACTLESS_ACTIVE).equals(contactless) &&
                            pcdCard.getContactless().equals(CONTACTLESS_BLOCKED)) {
                        pcdCard.setContactless(CONTACTLESS_PENDING_ACTIVATION);
                        wrap.setChipTagValue(pcdCard.getCard(), CONTACTLESS_CHIP_TAG, CONTACTLESS_ENABLED);
                    } else if (Integer.toString(CONTACTLESS_BLOCKED).equals(contactless) &&
                            pcdCard.getContactless().equals(CONTACTLESS_ACTIVE)) {
                        pcdCard.setContactless(CONTACTLESS_PENDING_BLOCKING);
                        wrap.setChipTagValue(pcdCard.getCard(), CONTACTLESS_CHIP_TAG, CONTACTLESS_DISABLED);
                    } else {
                        throw new RequestProcessingException("Unknown contactless status " + contactless);
                    }
                } catch (CMSCallAPIException e) {
                    throw new RequestProcessingException(e.getMessage(), this);
                }
            } else {
                throw new RequestProcessingException("Cannot change contactless status for this card because current stutus is not 0 or 1");
            }
        }
        createElement("manage-ctls").addText("done");
    }
}
