package lv.bank.cards.link.lv;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.CardDAO;
import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.cms.dto.IzdCondCardDAO;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.lv.DeliveryDetailsHelper;
import lv.bank.cards.link.Constants;
import lv.bank.cards.link.Order;
import lv.bank.cards.link.OrderValidatorBase;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.util.Date;

@Slf4j
public class OrderValidator extends OrderValidatorBase {

    public OrderValidator(CommonDAO commonDAO, CardDAO cardDAO, CardsDAO cardsDAO, ClientDAO clientDAO, Mapper mapper) {
        super(commonDAO, cardDAO, cardsDAO, clientDAO, mapper);
    }

    @Override
    public String getDefaultCountry() {
        return lv.bank.cards.core.utils.Constants.DEFAULT_COUNTRY_LV;
    }

    @Override
    public String getDeliveryBranch() {
        return lv.bank.cards.core.utils.Constants.DELIVERY_BRANCH_LV_888;
    }

    @Override
    public void validateOrder(Order order) throws DataIntegrityException {

        validateAndPrepareOrderBase(order);

        if (OrderProcessor.isRepeatedOrder(order)) {
            validateAndPrepareRepeatedOrder(order);
        } else {
            validateAndPrepareOrder(order);
        }
    }

    protected void validateAndPrepareRepeatedOrder(Order order) throws DataIntegrityException {

        log.info("processRepeatedOrder incoming order = {}", order);
        final String personId = order.getOwnerPersonId();

        IzdCard card;
        try {
            card = cardDAO.getIzdCardByOrderId(order.getOrderId());
            if (card == null) {
                throw new DataIntegrityException("Cannot find card by order id: " + order.getOrderId());
            } else {
                log.info("Found card {} by order id {}", card.getCard(), order.getOrderId());
                order.setCardNumber(card.getCard());
            }
        } catch (SQLException e) {
            log.error("processOrder", e);
            throw new DataIntegrityException(e.getMessage());
        }

        if (personId != null && !personId.trim().equals(card.getIdCard())) {
            throw new DataIntegrityException("Given person ID " + personId + " don't match with found person ID " + card.getIdCard());
        }
    }

    protected void validateAndPrepareOrder(Order order) throws DataIntegrityException {

        String branch = getMapper().getBranchIdByExternalId(order.getBranchToDeliverAt());

        if (branch == null) {
            throw new DataIntegrityException("There is no branch in NORDLB_BRANCHES for " + order.getBranchToDeliverAt());
        }

        if (getDeliveryBranch().equals(order.getBranchToDeliverAt())) {
            DeliveryDetailsHelper details = getMapper().orderToDeliveryDetailsHelper(order);
            details.checkData();
        }

        if (order.getCardName().length() > 24) {
            throw new DataIntegrityException("Cardholder name is too long. Should be 24 symbols or shorter.");
        }

        if (order.getAction().equals(Constants.CARD_RENEW_ACTION)) {

            IzdCard izdCard = (IzdCard) cardDAO.getObject(IzdCard.class, order.getCardNumber());

            if (izdCard == null) {
                throw new DataIntegrityException("Did not find renewed card: " + order.getCardNumber());
            }

            if ("2".equals(izdCard.getStatus1())) {
                // Need to create new card because this is hard blocked
                if (izdCard.getCard().startsWith("4652281")) {
                    order.setAuthLevel("2");
                    order.setBin("465228");
                    order.setAction(Constants.CARD_CREATE_ACTION);
                } else {
                    order.setAction(Constants.CARD_REPLACE_ACTION);
                }
            }

            IzdCondCardDAO condCard = new IzdCondCardDAO(cardDAO.getIzdCondCardByCard(order.getCardNumber()));

            log.info("Currently processing card renewal order. Client specified period {}. Issuing can renew this card only for period of {} months",
                    order.getCardValidityPeriod(), condCard.getCardValidity());

            int period = Integer.parseInt(order.getCardValidityPeriod()) * 12;
            if (condCard.getCardValidity() != period) {
                throw new DataIntegrityException("Can't renew this card, as card ordered for period " + period +
                        ", but in card condition specified period is " + condCard.getCardValidity());
            }

            if (izdCard.getExpiry1() != null && izdCard.getExpiry1().before(new Date())) {
                order.setAction(Constants.CARD_REPLACE_ACTION);
                order.setAutoBlockCard("1");
            }
        }

        if (order.getAction().equals(Constants.CARD_CREATE_ACTION)) {

            if (StringUtils.isBlank(order.getClientCountry())) {
                throw new DataIntegrityException("Missing client address country");
            }

            if (StringUtils.isNotBlank(order.getRiskLevel())) {
                String bin = order.getBin();
                String riskLevel = order.getRiskLevel();
                if (!cardDAO.isRiskLevelLinkedToBin(bin, riskLevel)) {
                    throw new DataIntegrityException("Risk Level [" + riskLevel + "] is not linked to BIN [" + bin + "]");
                }
            }

            if ((order.getClientType().startsWith("2"))) {
                // ...Check the length of company name
                if (order.getOwnerName().length() > 24) {
                    throw new DataIntegrityException("Own_name field (company name) too long. Should be 24 symbols or shorter.");
                }
                // ...And check for any disallowed characters there
                if (!order.getOwnerName().matches("[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZÜÕÖÄĀČĒĢĪĶĻŅŠŪŽ&,\\./\\-\\s]+")) {
                    throw new DataIntegrityException("Company name contains characters that are not allowed: " + order.getOwnerName());
                }
            }
        }
    }

    public Mapper getMapper() {
        return (Mapper) super.mapper;
    }

}
