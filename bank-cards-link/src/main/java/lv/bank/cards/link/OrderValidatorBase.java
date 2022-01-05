package lv.bank.cards.link;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public abstract class OrderValidatorBase {

    protected final CommonDAO commonDAO;
    protected final CardDAO cardDAO;
    protected final CardsDAO cardsDAO;
    protected final ClientDAO clientDAO;
    protected final MapperBase mapper;

    public abstract void validateOrder(Order order) throws DataIntegrityException;

    public abstract String getDefaultCountry();

    public abstract String getDeliveryBranch();

    protected void validateAndPrepareOrderBase(Order order) throws DataIntegrityException {

        if (StringUtils.isEmpty(order.getCountry())) {
            order.setCountry(getDefaultCountry());
        }

        if (order.getAction().equals(Constants.CARD_CREATE_ACTION)) {
                if (StringUtils.isBlank(order.getIban()) &&
                        !Constants.APPLICATION_TYPE_CARD_AUTO_RENEW.equals(order.getApplicationType())) {
                    throw new DataIntegrityException("Order is missing IBAN data");
                }
        }

        if (order.getAction().equals(Constants.CARD_RENEW_ACTION)) {

            IzdCard izdCard = (IzdCard) cardDAO.getObject(IzdCard.class, order.getCardNumber());

            // Only active cards can be renewed
            Set<String> allowedCauses = new HashSet<>();
            allowedCauses.add("A");
            allowedCauses.add("B");
            allowedCauses.add("C");
            allowedCauses.add("D");

            if (!("0".equals(izdCard.getStatus1()))) {
                if (!allowedCauses.contains(izdCard.getStopCause())) {
                    throw new DataIntegrityException("Card stop cause is not allowed: " + izdCard.getStopCause());
                }
            }
        }

        if (StringUtils.isNotBlank(order.getMigratedCardNumber())) {
            PinRetainingHelper retainPin = new PinRetainingHelper(
                    order.getMigratedCardNumber(),
                    order.getMigratedCardPinBlock(),
                    order.getMigratedCardPinKeyId()
            );
            retainPin.checkData();
        }
    }
}
