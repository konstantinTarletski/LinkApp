package lv.bank.cards.soap.handlers;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.rtps.StipAccount;
import lv.bank.cards.core.rtps.dao.StipAccountDAO;
import lv.bank.cards.core.rtps.dao.StipLocksDAO;
import lv.bank.cards.core.rtps.impl.StipAccountDAOHibernate;
import lv.bank.cards.core.rtps.impl.StipLocksDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.exceptions.RequestProcessingSoftException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;
import lv.bank.cards.soap.service.OtbService;
import lv.bank.cards.soap.service.dto.OtbDo;

import java.util.List;

@Slf4j
public class Otb extends SubRequestHandler {

    protected StipAccountDAO stipAccountDAO;
    protected StipLocksDAO stipLocksDAO;
    protected CardService helper;
    protected OtbService otbService;

    public Otb() {
        super();
        helper = new CardService();
        stipAccountDAO = new StipAccountDAOHibernate();
        stipLocksDAO = new StipLocksDAOHibernate();
        otbService = new OtbService(stipLocksDAO);
    }

    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        log.info("handle BEGIN");

        String card = getStringFromNode("/do/card");

        if (!CardUtils.cardCouldBeValid(card)) {
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);
        }

        String centreId = helper.getCentreIdByCard(card);

        if (centreId == null) {
            throw new RequestProcessingSoftException("Error getting centreId", this);
        }

        log.info("handle, findByCardAndCentreId({}, {})", card, centreId);

        List<StipAccount> accounts = stipAccountDAO.findByCardAndCentreId(card, centreId);

        log.info("Returned after findStipAccountsByCardNumberAndCentreId = {}", accounts);

        for (StipAccount acc : accounts) {

            ResponseElement thisAcc = createElement("account");

            thisAcc.createElement("account_id", acc.getComp_id().getAccountId());
            thisAcc.createElement("ccy-acc-alpha", acc.getCurrencyCode().getCcyAlpha());
            thisAcc.createElement("ccy-acc-num", acc.getCurrencyCode().getCcyNum());

            OtbDo oh = otbService.calculateOtb(acc);
            thisAcc.createElement("amt-initial", oh.getAmtInitial());
            thisAcc.createElement("amt-bonus", oh.getAmtBonus());
            thisAcc.createElement("amt-crd", oh.getAmtCrd());
            thisAcc.createElement("otb", oh.getOtb());
            thisAcc.createElement("amt-locked", oh.getAmtLocked());
        }
    }

}
