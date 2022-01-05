package lv.bank.cards.soap.service;

import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdStopCause;
import lv.bank.cards.core.entity.rtps.StipRmsStoplist;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.impl.CardsDAOHibernate;
import lv.bank.cards.core.rtps.dao.StipRmsStoplistDAO;
import lv.bank.cards.core.rtps.impl.StipRmsStoplistDAOHibernate;
import lv.bank.cards.core.utils.CardUtils;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.bank.cards.core.utils.LinkAppProperties;
import lv.bank.cards.soap.ErrorConstants;

import java.util.List;

public class CardService {

    protected CardsDAO cardsDAO;
    protected StipRmsStoplistDAO stipRmsStoplistDAO;

    public CardService() {
        cardsDAO = new CardsDAOHibernate();
        stipRmsStoplistDAO = new StipRmsStoplistDAOHibernate();
    }

    public boolean isBlockedForDelivery(String card) {
        String centreId = CardUtils.composeCentreId(LinkAppProperties.getCmsBankCode(), LinkAppProperties.getCmsGroupCode());
        List<StipRmsStoplist> rmsList = stipRmsStoplistDAO.getStipRmsStoplist(card, centreId, null);
        if (rmsList != null && !rmsList.isEmpty()) {
            for (StipRmsStoplist rms : rmsList) {
                if (rms.getAnswerCode() != null && "100".equals(rms.getAnswerCode().getActionCode())
                        && rms.getDescription() != null
                        && rms.getDescription().startsWith("Card blocked for delivery")) {
                    return true;
                }
            }
        }
        return false;
    }

    public PcdCard getPcdCard(String card) {
        return cardsDAO.findByCardNumber(card);
    }

    public String getCentreIdByCard(String card) {
        PcdCard pcdCard = getPcdCard(card);
        if (pcdCard == null) return null;
        return CardUtils.composeCentreIdFromPcdCard(pcdCard);
    }

    public String getCauseByActionCodeBankC(String action_code, String bank_c) throws DataIntegrityException {
        List<PcdStopCause> res = cardsDAO.findStopCauseByActionCodeBankC(action_code, bank_c);
        if (res.size() == 0) {
            throw new DataIntegrityException(ErrorConstants.cantFindCauseForActionCode);
        }
        if (res.size() > 1) {
            throw new DataIntegrityException(ErrorConstants.manyCausesForActionCode);
        }
        return res.get(0).getComp_id().getCause();
    }

    public String getActionCodeByCauseBankC(String cause, String bank_c) throws DataIntegrityException {
        List<PcdStopCause> res = cardsDAO.findActionCodeByStopCauseBankC(cause, bank_c);
        if (res.size() == 0) {
            throw new DataIntegrityException(ErrorConstants.cantFindActionCodeForCause);
        }
        if (res.size() > 1) {
            throw new DataIntegrityException(ErrorConstants.manyActionCodesForCause);
        }
        return res.get(0).getStatusCode();
    }

    public PcdCard saveOrUpdate(PcdCard pcdCard) {
        return (PcdCard) cardsDAO.saveOrUpdate(pcdCard);
    }

}
