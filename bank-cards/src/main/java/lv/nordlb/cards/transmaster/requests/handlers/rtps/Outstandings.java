package lv.nordlb.cards.transmaster.requests.handlers.rtps;

import lv.bank.cards.core.entity.cms.IzdLock;
import lv.bank.cards.core.entity.rtps.StipLocks;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.soap.ErrorConstants;
import lv.bank.cards.soap.exceptions.RequestFormatException;
import lv.bank.cards.soap.exceptions.RequestPreparationException;
import lv.bank.cards.soap.exceptions.RequestProcessingException;
import lv.bank.cards.soap.requests.ResponseElement;
import lv.bank.cards.soap.requests.SubRequest;
import lv.bank.cards.soap.requests.SubRequestHandler;
import lv.bank.cards.soap.service.CardService;
import lv.nordlb.cards.transmaster.bo.interfaces.IzdLockManager;
import lv.nordlb.cards.transmaster.fo.interfaces.TMFManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Outstandings extends SubRequestHandler {

    protected final TMFManager tmfManager;
    protected final IzdLockManager izdLockManager;

    public Outstandings() throws RequestPreparationException {
        super();
        try {
            tmfManager = (TMFManager) new InitialContext().lookup(TMFManager.JNDI_NAME);
            izdLockManager = (IzdLockManager) new InitialContext().lookup(IzdLockManager.JNDI_NAME);
        } catch (NamingException e) {
            throw new RequestPreparationException(e, this);
        }
    }

    @Override
    public void handle(SubRequest r) throws RequestFormatException, RequestProcessingException {
        super.handle(r);
        String card = getStringFromNode("/do/card");
        String mode = getStringFromNode("/do/show-all");

        if (!CardUtils.cardCouldBeValid(card)) {
            throw new RequestFormatException(ErrorConstants.invalidCardNumber, this);
        }
        outputLocksFromCmsAndRtps(r, card, mode);
    }

    private void outputLocksFromCmsAndRtps(SubRequest r, String card, String mode) {
        Set<Long> usedRowIdsFromCms = new HashSet<>();

        loadLocksFromCms(card, mode).stream()
                .peek(event -> outputCmsLock(r, event))
                .forEach(cmsLock -> usedRowIdsFromCms.add(cmsLock.getRowNumb()));

        loadLocksFromRtps(card, mode).stream()
                .filter(lock -> !usedRowIdsFromCms.contains(lock.getRowNumb()))
                .forEach(rtpsLock -> outputRtpsLock(r, rtpsLock));
    }

    private List<IzdLock> loadLocksFromCms(String card, String mode) {
        if (mode != null && mode.equals("1")) {
            return izdLockManager.findIzdLocksByCard(card, true);
        }
        return izdLockManager.findIzdLocksByCard(card, false);
    }

    private List<StipLocks> loadLocksFromRtps(String card, String mode) {
        if (mode != null && mode.equals("1")) {
            return tmfManager.findStipLocksByCardNumber(card, true);
        }
        return tmfManager.findStipLocksByCardNumber(card, false);
    }

    private void outputCmsLock(SubRequest r, IzdLock event) {
        final double authorizationAmount = (event.getLockingSign() == 0 ? -1 : 1) * event.getFld004() / Math.pow(10, Double.parseDouble(event.getIzdCcyFld049().getExp()));
        final double accountAmount = (event.getLockingSign() == 0 ? -1 : 1) * event.getFld006() / Math.pow(10, Double.parseDouble(event.getIzdCcyFld051().getExp()));

        ResponseElement thisAcc = createElement("outstanding");
        thisAcc.addAttribute("src", "cms");
        thisAcc.createElement("where", event.getFld043() == null ? "" : event.getFld043());
        thisAcc.createElement("when", r.getDateFormat().format(event.getRequestDate()));
        thisAcc.createElement("amt-trxn", Double.toString(authorizationAmount));
        thisAcc.createElement("amt-acc", Double.toString(accountAmount));
        thisAcc.createElement("ccy-trxn-num", event.getIzdCcyFld049().getCcyCode());
        thisAcc.createElement("ccy-acc-num", event.getIzdCcyFld051().getCcyCode());
        thisAcc.createElement("ccy-trxn-alpha", event.getFld049());
        thisAcc.createElement("ccy-acc-alpha", event.getFld051());
        thisAcc.createElement("appr-code", event.getFld038() == null ? "" : event.getFld038());
        thisAcc.createElement("stan", event.getFld011() == null ? "" : event.getFld011());
    }

    private void outputRtpsLock(SubRequest r, StipLocks event) {
        final double authorizationAmount = (-1) * event.getStipLocksMatch().getFld004().intValue() *
                (double) ((Math.abs(event.getAmount()) == event.getAmount()) ? 1 : -1)
                /
                Math.pow(10, event.getStipLocksMatch().getCurrencyCodeByFld049().getExpDot().doubleValue());
        final double accountAmount = (-1) * event.getAmount().intValue() / Math.pow(10, event.getStipLocksMatch().getCurrencyCodeByFld051().getExpDot().doubleValue());

        ResponseElement thisAcc = createElement("outstanding");
        thisAcc.addAttribute("src", "rtps");
        thisAcc.createElement("where", event.getStipLocksMatch().getFld043() == null ? "" : event.getStipLocksMatch().getFld043());
        thisAcc.createElement("when", r.getDateFormat().format(event.getRequestDate()));
        thisAcc.createElement("amt-trxn", Double.toString(authorizationAmount));
        thisAcc.createElement("amt-acc", Double.toString(accountAmount));
        thisAcc.createElement("ccy-trxn-num", event.getStipLocksMatch().getFld049());
        thisAcc.createElement("ccy-acc-num", event.getStipLocksMatch().getFld051());
        thisAcc.createElement("ccy-trxn-alpha", event.getStipLocksMatch().getCurrencyCodeByFld049().getCcyAlpha());
        thisAcc.createElement("ccy-acc-alpha", event.getStipLocksMatch().getCurrencyCodeByFld051().getCcyAlpha());
        thisAcc.createElement("appr-code", event.getStipLocksMatch().getFld038() == null ? "" : event.getStipLocksMatch().getFld038());
        thisAcc.createElement("stan", event.getStipLocksMatch().getFld011() == null ? "" : event.getStipLocksMatch().getFld011());
    }
}
