package lv.bank.cards.dbsynchronizer.lt;

import lv.bank.cards.core.entity.cms.IzdAccParam;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.dbsynchronizer.CustomQueriesBase;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LT_PROFILE;
import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LT_TEST_PROFILE;

@Service
@Profile({COUNTRY_LT_PROFILE, COUNTRY_LT_TEST_PROFILE})
public class CustomQueries extends CustomQueriesBase {

    public static final String IzdAccount = "select account from IzdAccount account " +
            "where (" +
            "    (account.ctime between :lastWaterMark and :currentWaterMark)" +
            "    or " +
            "    (account.accountNo in (" +
            "         select izdClAcct.izdAccount.accountNo from IzdClAcct izdClAcct " +
            "         where izdClAcct.cardAcct = account.cardAcct " +
            "         and izdClAcct.ctime between :lastWaterMark and :currentWaterMark)) " +
            "    or " +
            "    (account.accountNo in (" +
            "         select accountAc.comp_id.accountNo from IzdAccntAc accountAc, DnbIzdAccntChng accntChng " +
            "         where accntChng.status='1' " +
            "         and accntChng.comp_id.procId=accountAc.comp_id.procId))" +
            ") " +
            "and account.accountNo not in (select ltl2eur.ltlAccountNo from IzdLtl2eurAccountsMap ltl2eur) " +
            "and account.izdAccParam.izdCardGroupCcy.comp_id.ccy <> 'LTL'";

    public static final String IzdAccParam = "select param from IzdAccParam param where (param.ctime between :lastWaterMark and :currentWaterMark or "
            + "param.izdAccount.ctime between :lastWaterMark and :currentWaterMark) and "
            + "param.accountNo not in (select map.ltlAccountNo from IzdLtl2eurAccountsMap map) and "
            + "param.izdCardGroupCcy.comp_id.ccy <> 'LTL'";

    @Override
    public Map<String, String> getCustomQueries() {
        Map<String, String> customQueries = new HashMap<>();
        customQueries.put(IzdAccount.class.getName(), IzdAccount);
        customQueries.put(IzdAccParam.class.getName(), IzdAccParam);
        return customQueries;
    }

}
