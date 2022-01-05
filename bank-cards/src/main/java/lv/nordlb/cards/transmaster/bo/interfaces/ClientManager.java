package lv.nordlb.cards.transmaster.bo.interfaces;

import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.utils.DataIntegrityException;

import javax.ejb.Local;

@Local
public interface ClientManager {
    String COMP_NAME = "java:comp/env/ejb/ClientManager";
    String JNDI_NAME = "java:app/bankCards/ClientManagerBean";

    //	public IzdClient findByCif(String cif, String country) throws DataIntegrityException;
    IzdClient findByCif(String cif, String account, String country) throws DataIntegrityException;

    IzdClient saveOrUpdate(IzdClient ic) throws DataIntegrityException;

    String findClientNoByCardNo(String cardNo);

    IzdClient findClientByClientNoAndBank(String client, String bankC);
}
