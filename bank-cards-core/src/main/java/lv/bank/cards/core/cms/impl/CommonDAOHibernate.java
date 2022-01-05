package lv.bank.cards.core.cms.impl;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.cms.dao.CommonDAO;
import lv.bank.cards.core.entity.cms.IzdBinTable;
import lv.bank.cards.core.entity.cms.IzdBranch;
import lv.bank.cards.core.entity.cms.IzdCompany;
import lv.bank.cards.core.entity.cms.IzdCountry;
import lv.bank.cards.core.entity.cms.IzdInFile;
import lv.bank.cards.core.entity.cms.IzdStopCause;
import lv.bank.cards.core.entity.cms.NordlbFileId;
import lv.bank.cards.core.entity.cms.NordlbFileIdPK;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class CommonDAOHibernate extends BaseDAOHibernate implements CommonDAO {

    @SuppressWarnings("unchecked")
    @Override
    public Integer getFileID(String bankc, String groupc, String fileType, String jjj) {
        // 1) Try to find record in the
        Integer fileId;

        List<NordlbFileId> l = sf.getCurrentSession().createCriteria(NordlbFileId.class).add(Restrictions.eq("comp_id.fileType", fileType))
                .add(Restrictions.eq("comp_id.groupc", groupc))
                .add(Restrictions.eq("comp_id.JDay", jjj)).list();

        NordlbFileId thisFileId = null;
        if (!l.isEmpty()) {
            thisFileId = l.get(0);
            fileId = thisFileId.getId().toBigInteger().intValue() + 1;
            thisFileId.setId(new BigDecimal(fileId));
            sf.getCurrentSession().update(thisFileId);
        } else {
            fileId = 1;
            thisFileId = new NordlbFileId();
            thisFileId.setId(new BigDecimal(1));
            NordlbFileIdPK pk = new NordlbFileIdPK();
            pk.setBankC(bankc);
            pk.setFileType(fileType);
            pk.setGroupc(groupc);
            pk.setJDay(jjj);
            thisFileId.setComp_id(pk);
            sf.getCurrentSession().save(thisFileId);
        }
        return fileId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getBranchIdByExternalId(String externalId) {
        List<String> l = sf.getCurrentSession().createQuery("select n.izdBranch.comp_id.branch from NordlbBranch as n where n.externalId=:externalId")
                .setString("externalId", externalId).list();

        if (!l.isEmpty())
            return l.get(0);

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IzdCompany findIzdCompanyByRegCodeUR(String regCode) throws DataIntegrityException {
        List<IzdCompany> c = sf.getCurrentSession().createCriteria(IzdCompany.class).add(Restrictions.eq("regCodeUr", regCode))
                .list();
        if ((c == null) || (c.size() == 0))
            return null;
        else if (c.size() == 1)
            return c.get(0);
        else
            throw new DataIntegrityException("There are " + c.size() + " companies with regCodeUR = [" + regCode + "]");
    }

    @SuppressWarnings("unchecked")
    @Override
    public IzdCompany getIzdCompanyByCode(String code, String bankc) throws DataIntegrityException {
        List<IzdCompany> c = sf.getCurrentSession().createCriteria(IzdCompany.class).add(Restrictions.eq("comp_id.code", code))
                .add(Restrictions.eq("comp_id.bankC", bankc)).list();
        if ((c == null) || (c.size() == 0))
            return null;
        else if (c.size() == 1)
            return (IzdCompany) c.iterator().next();
        else
            throw new DataIntegrityException("There are " + c.size() + " companies with code = [" + code + "]");
    }

    @SuppressWarnings("unchecked")
    @Override
    public IzdCountry getIzdCountryByShortCountryCode(String code) throws DataIntegrityException {
        List<IzdCountry> c = sf.getCurrentSession().createCriteria(IzdCountry.class).add(Restrictions.eq("countryShort", code)).list();
        for (IzdCountry co : c) {
            if (co.getCountry().length() == 3)
                return co; // Return 3 symbol country
        }
        if (c != null && !c.isEmpty())
            return c.get(0); // else return any country
        else
            throw new DataIntegrityException("No countries found for short country code=[" + code + "]");
    }

    @SuppressWarnings("unchecked")
    @Override
    public IzdStopCause findStopCauseByCause(String cause) throws DataIntegrityException {
        List<IzdStopCause> c = sf.getCurrentSession().createCriteria(IzdStopCause.class).add(Restrictions.eq("comp_id.cause", cause)).list();
        if ((c != null) && (c.size() == 1))
            return c.get(0);
        throw new DataIntegrityException(((c == null) || (c.size() == 0)) ? ("No stopCauses found for cause=[" + cause + "]") : ("There are " + c.size() + " causes found instead of one"));
    }

    @Override
    public IzdBinTable findBinByCardOrBinCode(String card, String bin){
        if(StringUtils.isBlank(card) && StringUtils.isBlank(bin))
            return null;
        if(StringUtils.isBlank(bin)){
            bin = StringUtils.substring(card, 0, 6);
        }
        List<?> list = sf.getCurrentSession().createCriteria(IzdBinTable.class).add(Restrictions.eq("comp_id.binCode", bin)).list();
        if(list.isEmpty())
            return null;
        else
            return (IzdBinTable)list.get(0);
    }

    @Override
    public String getExternalBranchId(String id) {
        if(StringUtils.isBlank(id))
            return null;
        List<?> list = sf.getCurrentSession().createCriteria(IzdBranch.class).add(Restrictions.eq("comp_id.branch", id)).list();
        if(list.isEmpty())
            return null;
        else
            return (String)list.get(0);
    }

    @Override
    public IzdBranch getIzdBranchByRegCodeUR(String regCode) throws DataIntegrityException {
        List<?> c = sf.getCurrentSession().createCriteria(IzdBranch.class).add(Restrictions.eq("regKodsUr", regCode)).list();
        if ((c == null) || (c.size() == 0))
            return null;
        else if (c.size() == 1)
            return (IzdBranch)c.get(0);
        else
            throw new DataIntegrityException("There are "+c.size()+" branches with regCodeUR = ["+regCode+"]");
    }

    @Override
    @SuppressWarnings("unchecked")
    public IzdInFile getIzdInFileByFilename(String filename) throws DataIntegrityException {
        List<IzdInFile> c = sf.getCurrentSession().createCriteria(IzdInFile.class).add(Restrictions.eq("inFile", filename)).list();

        if ((c != null) && (c.size() == 1))
            return c.get(0);
        else if ((c == null) || (c.size() == 0))
            return null;

        throw new DataIntegrityException("There are "+c.size()+" files found instead of one");
    }

    @Override
    public SQLQuery getDeliveryBranchDataHandoff(String bankCode, String groupCode) {
        String sql = "select code, name from izd_u_def10 where bank_c = '" + bankCode + "' and groupc = '" + groupCode + "'";
        SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
        return sqlQuery;
    }

    @Override
    public SQLQuery getTokensDataHandoff(String requestorIdApple, String requestorIdGoogle, String datetime, String country) {

        final String YES = "Y";
        final String NO = "N";

        String sql = "select" +
                "   c.region " +
                "   ,rawtohex(dbms_obfuscation_toolkit.md5(INPUT_STRING => c.card)) as card" +
                "   ,case nvl(t.requestor_id,'x')" +
                "        when '" + requestorIdApple + "' then 'apple' " +
                "        when '" + requestorIdGoogle + "' then 'google'" +
                "        else 'Undefined - ' || nvl(t.requestor_id,'null')" +
                "    end as wallet" +
                "   ,t.token_ref_id" +
                "   ,t.create_date" +
                "   ,t.token_status" +
                "   ,'" + NO + "' as deleted" +
                " from" +
                "    izd_cards c" +
                "   ,izd_card_tokens t" +
                " where" +
                "    c.card = t.card" +
                " and nvl(t.requestor_id,'x') in ('" + requestorIdApple + "','" + requestorIdGoogle + "')" +
                " and c.region = '" + country + "' " +
                " and t.ctime >= to_date('" + datetime + "', 'yyyy-mm-dd hh24:mi:ss')" +
                " and t.token_type in ('02','06')" +
                " union" +
                " select distinct" +
                "    c.region" +
                "   ,rawtohex(dbms_obfuscation_toolkit.md5(INPUT_STRING => c.card)) as card" +
                "   ,case nvl(j.requestor_id,'x')" +
                "        when '" + requestorIdApple + "' then 'apple' " +
                "        when '" + requestorIdGoogle + "' then 'google'" +
                "        else 'Undefined - ' || nvl(j.requestor_id,'null')" +
                "    end as wallet" +
                "   ,j.token_ref_id" +
                "   ,j.create_date" +
                "   ,j.token_status" +
                "   ,'" + YES + "' as deleted" +
                " from" +
                "    izd_cards c" +
                "   ,izd_card_tokens_jn j" +
                " where" +
                "    c.card = j.card" +
                " and nvl(j.requestor_id,'x') in ('" + requestorIdApple + "','" + requestorIdGoogle + "')" +
                " and j.jn_operation = 'DEL'" +
                " and not exists(" +
                "    select 1 from izd_card_tokens t where t.token_pan = j.token_pan" +
                "    )" +
                " and c.region = '" + country + "' " +
                " and j.jn_datetime >= to_date('" + datetime + "', 'yyyy-mm-dd hh24:mi:ss')" +
                " and j.token_type in ('02','06')";

        SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addScalar("region", StringType.INSTANCE);//0
        sqlQuery.addScalar("card", StringType.INSTANCE);//1
        sqlQuery.addScalar("wallet", StringType.INSTANCE);//2
        sqlQuery.addScalar("token_ref_id", StringType.INSTANCE);//3
        sqlQuery.addScalar("create_date", TimestampType.INSTANCE);//4
        sqlQuery.addScalar("token_status", StringType.INSTANCE);//5
        sqlQuery.addScalar("deleted", StringType.INSTANCE);//6
        return sqlQuery;
    }

    @Override
    public SQLQuery getTokensTransDataHandoff(String requestorIdApple, String requestorIdGoogle, String datetime, String country, String bankCode, String groupCode) {

        String sql = "select" +
                "    c.region" +
                "   ,rawtohex(dbms_obfuscation_toolkit.md5(INPUT_STRING => c.card)) as card" +
                "   ,case nvl(s.requestor_id,'x')" +
                "        when '" + requestorIdApple + "' then 'apple' " +
                "        when '" + requestorIdGoogle + "' then 'google'" +
                "        else 'Undefined - ' || nvl(s.requestor_id,'null')" +
                "    end as wallet" +
                "   ,s.rec_date" +
                " from" +
                "    izd_cards c" +
                "   ,izd_slip s" +
                " where" +
                "    c.card = s.card" +
                " and s.card is not null" +
                " and nvl(s.token_flag,'x') = '1'" +
                " and nvl(s.requestor_id,'x') in ('" + requestorIdApple + "','" + requestorIdGoogle + "')" +
                " and s.bank_c = '" + bankCode + "'" +
                " and s.groupc = '" + groupCode + "'" +
                " and c.region = '" + country + "' " +
                " and s.rec_date >= to_date('" + datetime + "', 'yyyy-mm-dd hh24:mi:ss')";

        SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addScalar("region", StringType.INSTANCE);//0
        sqlQuery.addScalar("card", StringType.INSTANCE);//1
        sqlQuery.addScalar("wallet", StringType.INSTANCE);//2
        sqlQuery.addScalar("rec_date", DateType.INSTANCE);//3

        return sqlQuery;
    }

}
