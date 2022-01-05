package lv.nordlb.cards.transmaster.fo.interfaces;

import lv.bank.cards.core.entity.rtps.StipCard;
import lv.bank.cards.core.entity.rtps.StipRmsStoplist;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StipCardManager {

    String COMP_NAME = "java:comp/env/ejb/StipCardManager";
    String JNDI_NAME = "java:app/bankCards/StipCardManagerBean";


    StipCard findStipCard(String cardNumber);

    StipCard loadStipCard(String cardNumber, String centreId);

    List<StipRmsStoplist> getStipRmsStoplist(String card, String centreId, Long priority);
}
