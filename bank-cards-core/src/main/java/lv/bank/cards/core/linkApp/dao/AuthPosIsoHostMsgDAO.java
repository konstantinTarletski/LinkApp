package lv.bank.cards.core.linkApp.dao;

import lv.bank.cards.core.linkApp.dto.BDCCountAndAmount;
import lv.bank.cards.core.entity.linkApp.PcdAuthBatch;
import lv.bank.cards.core.entity.linkApp.PcdAuthPosIsoHostMessage;
import lv.bank.cards.core.entity.linkApp.PcdAuthSource;

import java.util.HashMap;

public interface AuthPosIsoHostMsgDAO extends DAO {

    HashMap<String, BDCCountAndAmount> getReconsiled(PcdAuthBatch batch);

    PcdAuthBatch getNewBatchOpened(/*Date watermark, */String terminal, PcdAuthSource source);

    PcdAuthPosIsoHostMessage findPcdAuthPosIsoHostMessageById(long id);

    PcdAuthSource findPcdAuthSourceById(long id);

    PcdAuthSource findPcdAuthSourceByName(String name);

}
