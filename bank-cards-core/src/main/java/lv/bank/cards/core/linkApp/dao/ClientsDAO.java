package lv.bank.cards.core.linkApp.dao;


import lv.bank.cards.core.entity.linkApp.PcdClient;

public interface ClientsDAO extends DAO {

    PcdClient findClientByCif(String Cif);

    PcdClient getClient(String id);

}
