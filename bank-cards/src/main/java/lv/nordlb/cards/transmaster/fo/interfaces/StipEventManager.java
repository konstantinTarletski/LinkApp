package lv.nordlb.cards.transmaster.fo.interfaces;

import javax.ejb.Local;

@Local
public interface StipEventManager {
	public static final String COMP_NAME="java:comp/env/ejb/StipEventManager";
	public static final String JNDI_NAME="java:app/bankCards/StipEventManagerBean";

}
