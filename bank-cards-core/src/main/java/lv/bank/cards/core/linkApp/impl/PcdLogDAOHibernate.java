package lv.bank.cards.core.linkApp.impl;

import lv.bank.cards.core.linkApp.dao.PcdLogDAO;
import lv.bank.cards.core.entity.linkApp.PcdLog;

import java.util.Date;

public class PcdLogDAOHibernate extends BaseDAOHibernate implements PcdLogDAO {

    @Override
    public Long write(String source, String operation, String operator, String text, String result) {
        PcdLog newLog = new PcdLog();
        newLog.setSource(source);
        newLog.setOperation(operation);
        newLog.setOperator(operator);
        newLog.setText(text);
        newLog.setResult(result);
        newLog.setRecDate(new Date());
        Long newLogId = (Long) sf.getCurrentSession().save(newLog);
        sf.getCurrentSession().flush();
        return newLogId;
    }
}
