/*
 * $Id: LocksDAO.java,v 1.1 2005/04/19 06:54:16 ays Exp $
 * Created on 2005.24.2
 */
package lv.bank.cards.core.linkApp.dao;

import lv.bank.cards.core.entity.linkApp.PcdPpCard;

public interface PPCardsDAO extends DAO {
    PcdPpCard findPcdPPCardByCardNumber(String card);

    PcdPpCard findPcdPPCardByCreditCard(String card);
}
