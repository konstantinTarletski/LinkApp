package lv.nordlb.cards.pcdabaNG.interfaces;

import lv.bank.cards.core.entity.linkApp.PcdMerchantPar;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MerchantManager {

	String COMP_NAME="java:comp/env/ejb/MerchantManager";
	String JNDI_NAME="java:app/bankCards/MerchantManagerBean";

	 List<PcdMerchantPar> GetDisMerchantPar(String param);
}
