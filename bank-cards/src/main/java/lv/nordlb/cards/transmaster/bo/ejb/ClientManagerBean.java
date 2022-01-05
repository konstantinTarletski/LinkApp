package lv.nordlb.cards.transmaster.bo.ejb;

import lv.bank.cards.core.cms.dao.ClientDAO;
import lv.bank.cards.core.cms.impl.ClientDAOHibernate;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.entity.cms.IzdClientPK;
import lv.bank.cards.core.utils.DataIntegrityException;
import lv.nordlb.cards.transmaster.bo.interfaces.ClientManager;

import javax.ejb.Stateless;

@Stateless
public class ClientManagerBean implements ClientManager {

    private ClientDAO clientDAO;

    public ClientManagerBean() {
        clientDAO = new ClientDAOHibernate();
    }
//
//	public IzdClient findByCif(String cif, String country) throws DataIntegrityException{ 
//		// cif should be uppercase !!!
//		return clientDAO.findByCif(cif.toUpperCase(), null, country);
//	}

    public IzdClient findByCif(String cif, String account, String country) throws DataIntegrityException {
        // cif should be uppercase !!!
        return clientDAO.findByCif(cif.toUpperCase(), account, country);
    }

    public IzdClient saveOrUpdate(IzdClient ic) throws DataIntegrityException {
        clientDAO.saveOrUpdate(ic);
        return ic;
    }

    public String findClientNoByCardNo(String cardNo) {
        return clientDAO.findClientNoByCardNo(cardNo);
    }

    @Override
    public IzdClient findClientByClientNoAndBank(String client, String bankC) {
        IzdClientPK izdClientPK = new IzdClientPK();
        izdClientPK.setClient(client);
        izdClientPK.setBankC(bankC);
        return (IzdClient) clientDAO.getObject(IzdClient.class, izdClientPK);
    }
}
