package lv.bank.cards.core.linkApp.impl;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.linkApp.PcdBranch;
import lv.bank.cards.core.entity.linkApp.PcdBranchPK;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenization;
import lv.bank.cards.core.entity.linkApp.PcdCardPendingTokenizationPK;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.entity.linkApp.PcdCurrency;
import lv.bank.cards.core.entity.linkApp.PcdStopCause;
import lv.bank.cards.core.linkApp.dao.CardsDAO;
import lv.bank.cards.core.linkApp.dto.CardInfoDTO;
import lv.bank.cards.core.utils.Crypting3DES;
import lv.bank.cards.core.utils.DataIntegrityException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@SuppressWarnings("unchecked")
@Slf4j
public class CardsDAOHibernate extends BaseDAOHibernate implements CardsDAO {

    final static private int AUTHENTIFICATION_CODE_LENGTH = 8;

    public PcdCard getCardByTrackingNoLT(String trackingNo) throws DataIntegrityException {
        Iterator<?> i = sf.getCurrentSession().createCriteria(PcdCard.class)
                .add(Restrictions.eq("uAField3", trackingNo))
                .list().iterator();
        if (i.hasNext()) {
            PcdCard card = (PcdCard) i.next();
            if (i.hasNext())
                throw new DataIntegrityException("Found multiple cards by tracking number = " + trackingNo);
            return card;
        }
        return null;
    }

    @Override
    public PcdCard findByCardNumber(String card) {
        Iterator<?> i = sf.getCurrentSession().createCriteria(PcdCard.class)
                .add(Restrictions.eq("card", card)).list().iterator();
        // .createQuery("from PcdCard")
        // .iterate();
        if (i.hasNext())
            return (PcdCard) i.next();
        return null;
    }

    @Override
    public List<PcdStopCause> findStopCauseByActionCodeBankC(String action_code, String bank_c) {
        return (List<PcdStopCause>) sf.getCurrentSession().createCriteria(PcdStopCause.class).add(
                Restrictions.eq("statusCode", action_code)).add(
                Restrictions.eq("comp_id.bankC", bank_c)).list();
    }

    @Override
    public List<PcdStopCause> findActionCodeByStopCauseBankC(String cause, String bank_c) {
        return (List<PcdStopCause>) sf.getCurrentSession().createCriteria(PcdStopCause.class).add(
                Restrictions.eq("comp_id.cause", cause)).add(
                Restrictions.eq("comp_id.bankC", bank_c)).list();
    }

    @Override
    public List<String> getCardsByPersonalCode(String personalCode, String country) {
        String hqlQuery = "SELECT pcdCard.card as card "
                + "FROM PcdCard pcdCard, PcdClient pcdClient, PcdAgreement pcdAgreement "
                + "WHERE pcdClient.personCode='" + personalCode + "' "
                + "AND pcdClient.region = '" + country + "' "
                + "AND pcdClient.comp_id.client = pcdAgreement.client "
                + "AND pcdAgreement.agreement = pcdCard.agreement";
        Query q = sf.getCurrentSession().createQuery(hqlQuery);
        return (List<String>) q.list();
    }

    @SuppressWarnings("unchecked")
    public List<String> getCardsByPersonalCode(String personalCode) {
        String hqlQuery = "SELECT pcdCard.card as card "
                + "FROM PcdCard pcdCard, PcdClient pcdClient, PcdAgreement pcdAgreement "
                + "WHERE pcdClient.personCode='" + personalCode + "' "
                + "AND pcdClient.comp_id.client = pcdAgreement.client "
                + "AND pcdAgreement.agreement = pcdCard.agreement";
        Query q = sf.getCurrentSession().createQuery(hqlQuery);
        return q.list();
    }

    public PcdCard findByInOrderNumber(BigInteger inOrderNumber) throws DataIntegrityException {

        Iterator<?> i = sf.getCurrentSession().createCriteria(
                        PcdCard.class).add(
                        Restrictions.eq("inOrderNumber", inOrderNumber)).list()
                .iterator();
        PcdCard retData = null;
        while (i.hasNext()) {
            if (retData != null) {
                throw new DataIntegrityException(
                        "inOrderNumber [" + inOrderNumber + "] is not unique in pcdCards");
            }
            retData = (PcdCard) i.next();
        }

        return retData;
    }

    @Override
    public List<Object[]> findCardData(List<String> cards, List<String> accounts) {
        String sqlCards = "";
        String inCards = null;
        String sqlAccounts = "";
        if (cards.size() > 0) {
            sqlCards += "SELECT rownum grp, a.account_no account, c.card card, "
                    + "to_char(usefull_staff.card_expd(c.card), 'YYMM') expiry, "
                    + "usefull_staff.card_cvc2(c.card) cvc2, "
                    + "'C' type, c.base_supp baseSupp, c.status1 status, (case when c.U_ACODE11 = '0' then '0' else '1' end) as sms "
                    + "FROM pcd_cards c, pcd_cards_accounts ca, pcd_accounts a "
                    + "WHERE a.account_no in (select account_no from pcd_cards_accounts where ";
            inCards = "card IN (";
            for (String card : cards) {
                inCards += "'" + card + "',";
            }
            inCards = inCards.substring(0, inCards.length() - 1).concat(")");
            sqlCards += inCards;
            sqlCards += ") "
                    + " and a.account_no = ca.account_no and a.bank_c=c.bank_c "
                    + " and c.card = ca.card "
                    + " and ((c.status1 != '2' and to_date(to_char(last_day(usefull_staff.card_expd(c.card)), 'DDMMYY')||'86399', 'DDMMYYSSSSS') > sysdate) OR c." + inCards + ")";
        }
        if (accounts.size() > 0) {
            sqlAccounts += "SELECT rownum grp, a.account_no account, c.card card, "
                    + "    to_char(usefull_staff.card_expd(c.card), 'YYMM') expiry, "
                    + "    usefull_staff.card_cvc2(c.card) cvc2, "
                    + "    'A' type, c.base_supp baseSupp, c.status1 status, (case when c.U_ACODE11 = '0' then '0' else '1' end) as sms "
                    + "FROM pcd_cards c, pcd_cards_accounts ca, pcd_accounts a "
                    + " WHERE ( ";
            for (String account : accounts) {
                sqlAccounts += " a.account_no='" + account + "' OR ";
            }
            sqlAccounts = sqlAccounts.substring(0, sqlAccounts.length() - 3);
            sqlAccounts += ") and "
                    + "a.account_no = ca.account_no and a.bank_c=c.bank_c "
                    + "and c.card = ca.card "
                    + "and c.status1 != '2' "
                    + "and to_date(to_char(last_day(usefull_staff.card_expd(c.card)), 'DDMMYY')||'86399', 'DDMMYYSSSSS') > sysdate ";
        }
        String sql = sqlCards + ((sqlCards.length() > 0 && sqlAccounts.length() > 0) ? " UNION ALL "
                : "") + sqlAccounts + "ORDER BY account, status, baseSupp, card";
        SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addScalar("grp", StringType.INSTANCE);
        sqlQuery.addScalar("account", StringType.INSTANCE);
        sqlQuery.addScalar("card", StringType.INSTANCE);
        sqlQuery.addScalar("expiry", StringType.INSTANCE);
        sqlQuery.addScalar("cvc2", StringType.INSTANCE);
        sqlQuery.addScalar("type", StringType.INSTANCE);
        sqlQuery.addScalar("baseSupp", StringType.INSTANCE);
        sqlQuery.addScalar("status", StringType.INSTANCE);
        return (List<Object[]>) sqlQuery.list();
    }

    public List<CardInfoDTO> getCardInfoLT(String card, String cif) {
        List<CardInfoDTO> retData = new ArrayList<>();
        String hqlQuery = "SELECT upper(pcdCard.pcdAgreement.pcdCardGroup.name)||' '||pcdCard.pcdBin.specialCardName as prefixDesc, "
                + "pcdCard.card as card, pcdCard.mcName as embossingName, pcdCard.expiry1 as expiryDate, pcdCard.cvc21 as cvc, "
                + "pcdCard.pcdAgreement.pcdClient.clientB as pCif, pcdAccount.pcdAccParam.pcdCurrency.isoAlpha as billingCurrency, "
                + "pcdAccount.cardAcct as accountNumber, pcdCard.groupc as cardGroup, pcdAccount.endBal as endBal, "
                + "nvl(pcdCard.pcdAgreement.pcdClient.personCode, pcdCard.idCard) as personCode, "
                + "pcdCard.status1 as cardStatus1, pcdCard.cardName FROM PcdCard pcdCard, PcdCardsAccount pcdCardsAccount, PcdAccount pcdAccount  "
                + "WHERE "
                + ((card != null) ? "pcdCard.card='" + card + "' AND " : "")
                + ((cif != null) ? "pcdCard.pcdAgreement.pcdClient.clientB='"
                + cif + "' AND " : "")
                + "pcdCard.card = pcdCardsAccount.comp_id.card AND "
                + "pcdCardsAccount.comp_id.accountNo = pcdAccount.accountNo";
        Query q = sf.getCurrentSession().createQuery(hqlQuery);

        Iterator<?> i = q.iterate();
        while (i.hasNext()) {
            Object[] tuple = (Object[]) i.next();
            CardInfoDTO cardInfoDTO = new CardInfoDTO();
            cardInfoDTO.setPrefixDesc(((tuple[0] != null) ? ((String) tuple[0]) : ("")));
            cardInfoDTO.setCard(((tuple[1] != null) ? ((String) tuple[1]) : ("")));
            if (tuple[2] != null) {
                String mcName = (String) tuple[2];
                if (!mcName.contains("/")) {
                    if (tuple[12] != null)
                        cardInfoDTO.setEmbossingName((String) tuple[12]);
                    else
                        cardInfoDTO.setEmbossingName(mcName);
                } else
                    cardInfoDTO.setEmbossingName(mcName.substring(mcName.indexOf("/") + 1)
                            + " " + mcName.substring(0, mcName.indexOf("/")));
            } else
                cardInfoDTO.setEmbossingName("");
            cardInfoDTO.setExpiryDate(((tuple[3] != null) ? ((Date) tuple[3]) : (null)));
            cardInfoDTO.setCvc(((tuple[4] != null) ? ((String) tuple[4]) : ("")));
            cardInfoDTO.setCif(((tuple[5] != null) ? ((String) tuple[5]) : ("")));
            cardInfoDTO.setBillingCurrency(((tuple[6] != null) ? ((String) tuple[6]) : ("")));
            cardInfoDTO.setAccountNumber(((tuple[7] != null) ? ((String) tuple[7]) : ("")));
            cardInfoDTO.setGroupc(((tuple[8] != null) ? ((String) tuple[8]) : ("")));
            cardInfoDTO.setEndBal(((tuple[9] != null) ? ((Long) tuple[9]) : (null)));
            cardInfoDTO.setPersonCode(((tuple[10] != null) ? ((String) tuple[10]) : ("")));
            cardInfoDTO.setCardStatus1(((tuple[11] != null) ? ((String) tuple[11]) : ("")));
            retData.add(cardInfoDTO);
        }
        return retData;
    }

    @Override
    public List<CardInfoDTO> getCardInfo(String card, String cif, String country, boolean mode) {
        List<CardInfoDTO> retData = new ArrayList<>();
        String hqlQuery = "SELECT 1 as prefixDesc, "
                + "pcdCard.card as card, pcdCard.mcName as embossingName, pcdCard.expiry1 as expiryDate, pcdCard.cvc21 as cvc, "
                + "pcdCard.pcdAgreement.pcdClient.clientB as pCif, pcdAccount.pcdAccParam.pcdCurrency.isoAlpha as billingCurrency, "
                + "pcdAccount.cardAcct as accountNumber, pcdCard.groupc as cardGroup, pcdAccount.endBal as endBal, "
                + "pcdCard.pcdAgreement.pcdClient.personCode as clientPCode, pcdCard.idCard as personCode, "
                + "pcdCard.status1 as cardStatus1, pcdCard.cardName, pcdCard.design, pcdCard.uAField2, pcdCard.uBField1, "
                + "(SELECT pcdBranch.externalId FROM PcdBranch pcdBranch WHERE pcdBranch.comp_id.branch = pcdCard.UCod10), "
                + "pcdCard.pinBlock, pcdCard.MName, pcdCard.expiry2 as expiryDate2, repDistribut.name, "
                + "pcdCard.pcdAgreement.street,  pcdCard.pcdAgreement.city, pcdCard.pcdAgreement.country, "
                + "pcdCard.pcdAgreement.postInd, (SELECT pcdBranch.externalId FROM PcdBranch pcdBranch WHERE pcdBranch.comp_id.branch = pcdCard.pcdAgreement.branch) as dBranch, "
                + "pcdCard.UField8 as UField8, pcdCard.renew as Renew, pcdCard.contactless as Contactless, pcdCard.pcdAgreement.repLang as language, "
                + "pcdCard.uACode11 as smsFee "
                + "FROM PcdCard pcdCard, PcdCardsAccount pcdCardsAccount, PcdAccount pcdAccount "
                + "LEFT JOIN pcdCard.pcdAgreement.pcdRepDistribut repDistribut "
                + "WHERE "
                + ((card != null) ? "pcdCard.card='" + card + "' AND " : "")
                + ((cif != null) ? "pcdCard.pcdAgreement.pcdClient.clientB='" + cif + "' AND pcdCard.pcdAgreement.pcdClient.region = '" + country + "' AND " : "")
                + (mode ? "pcdCard.status1!='2' AND " : "")
                + "pcdCard.card = pcdCardsAccount.comp_id.card AND "
                + "pcdCardsAccount.comp_id.accountNo = pcdAccount.accountNo";
        Query q = sf.getCurrentSession().createQuery(hqlQuery);

        @SuppressWarnings("rawtypes")
        Iterator i = q.iterate();
        while (i.hasNext()) {
            Object[] tuple = (Object[]) i.next();
            CardInfoDTO cardInfoDTO = new CardInfoDTO();
            cardInfoDTO.setPrefixDesc(((tuple[1] != null) ? (getProductNameByCard((String) tuple[1])) : ("")));
            cardInfoDTO.setCard(((tuple[1] != null) ? ((String) tuple[1]) : ("")));
            if (tuple[2] != null) {
                String mcName = (String) tuple[2];
                if (!mcName.contains("/")) {
                    if (tuple[13] != null)
                        cardInfoDTO.setEmbossingName((String) tuple[13]);
                    else
                        cardInfoDTO.setEmbossingName(mcName);
                } else
                    cardInfoDTO.setEmbossingName(mcName.substring(mcName.indexOf("/") + 1)
                            + " " + mcName.substring(0, mcName.indexOf("/")));
            } else
                cardInfoDTO.setEmbossingName("");
            cardInfoDTO.setExpiryDate(((tuple[3] != null) ? ((Date) tuple[3]) : (null)));
            cardInfoDTO.setCvc(((tuple[4] != null) ? ((String) tuple[4]) : ("")));
            cardInfoDTO.setCif(((tuple[5] != null) ? ((String) tuple[5]) : ("")));
            cardInfoDTO.setBillingCurrency(((tuple[6] != null) ? ((String) tuple[6]) : ("")));
            cardInfoDTO.setAccountNumber(((tuple[7] != null) ? ((String) tuple[7]) : ("")));
            cardInfoDTO.setGroupc(((tuple[8] != null) ? ((String) tuple[8]) : ("")));
            cardInfoDTO.setEndBal(((tuple[9] != null) ? ((Long) tuple[9]) : (null)));
            cardInfoDTO.setClientPersonCode(((tuple[10] != null) ? ((String) tuple[10]) : ("")));
            cardInfoDTO.setPersonCode(((tuple[11] != null) ? ((String) tuple[11]) : ("")));
            cardInfoDTO.setCardStatus1(((tuple[12] != null) ? ((String) tuple[12]) : ("")));
            cardInfoDTO.setCardName(((tuple[13] != null) ? ((String) tuple[13]) : ("")));
            cardInfoDTO.setDesign(((tuple[14] != null) ? ((String) tuple[14]) : ("")));
            cardInfoDTO.setUAField2(((tuple[15] != null) ? ((String) tuple[15]) : ("")));
            cardInfoDTO.setUBField1(((tuple[16] != null) ? ((String) tuple[16]) : ("")));
            cardInfoDTO.setUCod10(((tuple[17] != null) ? ((String) tuple[17]) : ("")));
            cardInfoDTO.setPinBlock(((tuple[18] != null) ? ((String) tuple[18]) : ("")));
            cardInfoDTO.setPassword(((tuple[19] != null) ? ((String) tuple[19]) : ("")));
            cardInfoDTO.setExpiryDate2(((tuple[20] != null) ? ((Date) tuple[20]) : (null)));
            cardInfoDTO.setDistribMode(((tuple[21] != null) ? ((String) tuple[21]) : ("")));
            cardInfoDTO.setDStreet(((tuple[22] != null) ? ((String) tuple[22]) : ("")));
            cardInfoDTO.setDCity(((tuple[23] != null) ? ((String) tuple[23]) : ("")));
            cardInfoDTO.setDCountry(((tuple[24] != null) ? ((String) tuple[24]) : ("")));
            cardInfoDTO.setDPostInd(((tuple[25] != null) ? ((String) tuple[25]) : ("")));
            cardInfoDTO.setDBranch(((tuple[26] != null) ? ((String) tuple[26]) : ("")));
            cardInfoDTO.setUField8(((tuple[27] != null) ? ((String) tuple[27]) : ("")));
            cardInfoDTO.setRenew(((tuple[28] != null) ? ((String) tuple[28]) : ("")));
            cardInfoDTO.setContactless(((tuple[29] != null) ? ((Integer) tuple[29]) : null));
            cardInfoDTO.setLanguage(((tuple[30] != null) ? ((String) tuple[30]) : ("")));
            cardInfoDTO.setSmsFee((String) tuple[31]);
            retData.add(cardInfoDTO);
        }
        return retData;
    }

    @Override
    public SQLQuery findBinDataHandoff(String datetime) {
        String sql = "select bin_code,card_name from pcd_bins";
        //where pcd_bins.ctime>=to_date('"+ datetime + "','YYYY-MM-DD HH24:MI:SS')";
        SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addScalar("bin_code", StringType.INSTANCE);//0
        sqlQuery.addScalar("card_name", StringType.INSTANCE);//1
        return sqlQuery;
    }

    @Override
    public SQLQuery findCardsDataHandoff(String datetime, String country) {
        String sql = "select " +
                "    c.groupc GroupC, " +
                "    substr(c.card,1,6)||substr(c.card,-4) as CardNumber, " +
                "    ap.u_field5 as CardAccount, " +
                "    c.status1 as CardStatus, " +
                "    renew as CardRenewalStatus, " +
                "    u_cod9 as SalaryCard, " +
                "    REG_NR as VentureRegCode, " +
                "    ap.ccy_iso_alpha as Currency, " +
                "    c.cond_set as ConditionSet, " +
                "    c.rec_date as RegDate, " +
                "    c.expiry1 as ExpiryDate, " +
                "    rawtohex(my_md5(c.card)) as cardHash, " +
                "    ap.cond_set as AccCond, " +
                "    cl.cl_type as clType, " +
                "    DECODE(substr(c.card,1,7),'4921755','GC','4775735','GD', '  ') as galactico, " +
                "    c.CARD_NAME as CardHolderName, " +
                "    c.ID_CARD as CardHolderPersonalCode, " +
                "    (case when substr(c.card,7,1) = '8' then 1 else  0 end) as IsPlatinum, " +
                "    dnb_card_product_name(c.card) as cardproductname, " +
                "    cl.client_b as CIF, " +
                "    c.stop_cause as stopcause, " +
                "    c.chip_app_id as chipappid, " +
                "    c.contactless, " +
                "    case when instr(c.u_field7,':')>1 then substr(c.u_field7,3) else c.u_field7 end as smsphone, " +
                "    case when instr(c.u_field7,':')>1 then substr(c.u_field7,1,1) when c.u_field7 is null then null else 'f' end as smstemplate, " +
                "    case when c.renewed_card is not null then regexp_replace(c.renewed_card,'\\d{6}','******',7) end as renewedCardMasked, " +
                "    case when c.renewed_card is not null then rawtohex(my_md5(c.renewed_card)) end as renewedCardHash, " +
                "    u_cod10 as cardDeliveryBranchCode, " +
                "    c_type as cardType " +
                "from " +
                "    pcd_cards c, " +
                "    pcd_clients cl, " +
                "    pcd_acc_params ap, " +
                "    pcd_cards_accounts ca, " +
                "    pcd_agreements ag " +
                "where ca.account_no = ap.account_no " +
                "  and ca.card = c.card " +
                "  and c.agreement = ag.agreement " +
                "  and ag.client=cl.client " +
                "  and ag.bank_c=cl.bank_c " +
                "  and c.br_id!='196' " +
                "  and c.region = '" + country + "'" +
                "  and (c.status1!=2 " +
                "    or c.ctime>=to_date('" + datetime + "','YYYY-MM-DD HH24:MI:SS'))";

        SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
        return (SQLQuery) sqlQuery;
    }


    @Override
    public SQLQuery findCardsDataHandoffForLDWH(String datetime, String country) {
        String sql = "select " +
                "    c.groupc GroupC, " +
                "    substr(c.card,1,6)||substr(c.card,-4) as CardNumber, " +
                "    ap.u_field5 as CardAccount, " +
                "    c.status1 as CardStatus, " +
                "    renew as CardRenewalStatus, " +
                "    u_cod9 as SalaryCard, " +
                "    REG_NR as VentureRegCode, " +
                "    ap.ccy_iso_alpha as Currency, " +
                "    c.cond_set as ConditionSet, " +
                "    c.rec_date as RegDate, " +
                "    c.expiry1 as ExpiryDate, " +
                "    rawtohex(my_md5(c.card)) as cardHash, " +
                "    ap.cond_set as AccCond, " +
                "    cl.cl_type as clType, " +
                "    DECODE(substr(c.card,1,7),'4921755','GC','4775735','GD', '  ') as galactico, " +
                "    c.CARD_NAME as CardHolderName, " +
                "    c.ID_CARD as CardHolderPersonalCode, " +
                "    (case when substr(c.card,7,1) = '8' then 1 else  0 end) as IsPlatinum, " +
                "    dnb_card_product_name(c.card) as cardproductname, " +
                "    cl.client_b as CIF, " +
                "    c.stop_cause as stopcause, " +
                "    c.chip_app_id as chipappid, " +
                "    c.contactless, " +
                "    case when instr(c.u_field7,':')>1 then substr(c.u_field7,3) else c.u_field7 end as smsphone, " +
                "    case when instr(c.u_field7,':')>1 then substr(c.u_field7,1,1) when c.u_field7 is null then null else 'f' end as smstemplate " +
                "from " +
                "    pcd_cards c, " +
                "    pcd_clients cl, " +
                "    pcd_acc_params ap, " +
                "    pcd_cards_accounts ca, " +
                "    pcd_agreements ag " +
                "where ca.account_no = ap.account_no " +
                "  and ca.card = c.card " +
                "  and c.agreement = ag.agreement " +
                "  and ag.client=cl.client " +
                "  and ag.bank_c=cl.bank_c " +
                "  and c.br_id!='196' " +
                "  and c.region = '" + country + "'" +
                "  and (c.status1!=2 " +
                "    or c.ctime>=to_date('" + datetime + "','YYYY-MM-DD HH24:MI:SS'))";

        SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
        return (SQLQuery) sqlQuery;
    }

    @Override
    public SQLQuery findCondCardDataHandoff(String datetime) {
        String sql = "select "
                + "GROUPC as GroupC,"
                + "COND_SET as CondSet,"
                + "CCY as ccy,"
                + "NAME as Name,"
                + "CARD_FEE as CardFee,"
                + "CARD_FEE1 as CardFee1,"
                + "ANNUAL_FEE as AnnualFee,"
                + "ANNUAL_FEE1 as AnnualFee1,"
                + "REPL_FEE as ReplFee,"
                + "REPL_FEE1 as ReplFee1,"
                + "DUPLIC_FEE as DuplicFee,"
                + "DUPLIC_FEE1 as DuplicFee1,"
                + "BIN_CODE as BinCode"
                + " from pcd_cond_card";
//		+" where ctime>=to_date('"+datetime+"','YYYY-MM-DD HH24:MI:SS')"; 

        SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addScalar("GroupC");
        sqlQuery.addScalar("CondSet", StringType.INSTANCE);//0
        sqlQuery.addScalar("ccy", StringType.INSTANCE);//1
        sqlQuery.addScalar("Name", StringType.INSTANCE);//2
        sqlQuery.addScalar("CardFee");//3
        sqlQuery.addScalar("CardFee1");//4
        sqlQuery.addScalar("AnnualFee");//5
        sqlQuery.addScalar("AnnualFee1");//6
        sqlQuery.addScalar("ReplFee");//7
        sqlQuery.addScalar("ReplFee1");//8
        sqlQuery.addScalar("DuplicFee");//9
        sqlQuery.addScalar("DuplicFee1");//10
        sqlQuery.addScalar("Bincode", StringType.INSTANCE);//11
        return sqlQuery;
    }

    @Override
    public SQLQuery findCondAccntDataHandoff(String datetime) {
        String sql = "select "
                + "GROUPC as GroupC,"
                + "COND_SET as CondSet,"
                + "CCY as ccy,"
                + "NAME as Name,"
                + "DEB_INTR_BASE_P as DebIntrBaseP,"
                + "OLIMP_INTR as OlimpIntr,"
                + "STM_FEE as Stm_Fee,"
                + "DEB_INTR_OVER_P_B as DebIntrOverPB,"
                + "LATE_FEE as LateFee"
                + " from pcd_cond_accnt";
//		+" where ctime>=to_date('"+datetime+"','YYYY-MM-DD HH24:MI:SS')"; 

        SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addScalar("GroupC");
        sqlQuery.addScalar("CondSet", StringType.INSTANCE);//0
        sqlQuery.addScalar("ccy", StringType.INSTANCE);//1
        sqlQuery.addScalar("Name", StringType.INSTANCE);//2
        sqlQuery.addScalar("DebIntrBaseP");//3
        sqlQuery.addScalar("OlimpIntr");//4
        sqlQuery.addScalar("Stm_Fee");//5
        sqlQuery.addScalar("DebIntrOverPB");//6
        sqlQuery.addScalar("LateFee");//7
        return sqlQuery;
    }

    @Override
    public SQLQuery findServicesFeeDataHandoff(String datetime) {
        String sql = "select "
                + "BANK_C as BankC,"
                + "COMM_GRP as CommGrp,"
                + "ACNT_CCY as AcntCcy,"
                + "SERVICE_TYPE as ServiceType,"
                + "MIN_FEE as MinFee,"
                + "CONST_FEE as ConstFee,"
                + "PERCENT_FEE as PercentFee,"
                + "MAX_FEE as MaxFee,"
                + "DEBIT_CREDIT as DebitCredit,"
                + "GROUPC as Groupc,"
                + "COND_SET as CondSet,"
                + "COND_SET_SOURCE as CondSetSource,"
                + "CTIME as ctime,"
                + "USRID as usrid,"
                + "EVENT_AREA as EventArea,"
                + "REC_DATE_WHEN_ADDED as RecDateWhenAdded,"
                + "XT_SET_ID as xtsetid"
                + " from pcd_services_fee"
//		+" where ctime>=to_date('"+datetime+"','YYYY-MM-DD HH24:MI:SS')"
                + " where (Cond_Set_Source='C' and Cond_Set in (select distinct cond_set from pcd_cond_card)) "
                + " and ((SERVICE_TYPE='0010')or(SERVICE_TYPE='0110')or(SERVICE_TYPE='0910')or(SERVICE_TYPE='3110')or(SERVICE_TYPE='A010'))";

        SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addScalar("BankC", StringType.INSTANCE);//0
        sqlQuery.addScalar("CommGrp", StringType.INSTANCE);//1
        sqlQuery.addScalar("AcntCcy", StringType.INSTANCE);//2
        sqlQuery.addScalar("ServiceType", StringType.INSTANCE);//3
        sqlQuery.addScalar("MinFee");//4
        sqlQuery.addScalar("ConstFee");//5
        sqlQuery.addScalar("PercentFee");//6
        sqlQuery.addScalar("MaxFee");//7
        sqlQuery.addScalar("DebitCredit", StringType.INSTANCE);//8
        sqlQuery.addScalar("Groupc", StringType.INSTANCE);//9
        sqlQuery.addScalar("CondSet", StringType.INSTANCE);//10
        sqlQuery.addScalar("CondSetSource", StringType.INSTANCE);//11
        sqlQuery.addScalar("ctime", DateType.INSTANCE);//12
        sqlQuery.addScalar("usrid", StringType.INSTANCE);//13
        sqlQuery.addScalar("EventArea", StringType.INSTANCE);//14
        sqlQuery.addScalar("RecDateWhenAdded", DateType.INSTANCE);//15
        sqlQuery.addScalar("xtsetid");//16
        return sqlQuery;
    }

    @Override
    public SQLQuery getStopCausesDataHandoff(String datetime) {
        String sql = "select cause as causecode, name as causename from pcd_stop_causes";
        return sf.getCurrentSession().createSQLQuery(sql);
    }

    @Override
    public PcdCondAccnt findAccountConditionsByCardNumber(String card) {
        String sql = "select deb_intr_base_p, deb_intr_over_p_b "
                + "from pcd_cond_accnt "
                + "where cond_set||ccy in ( "
                + "  select cond_set||ccy_iso_alpha "
                + "  from pcd_acc_params "
                + "  where account_no in (select account_no from pcd_cards_accounts where card = :card) "
                + "  and groupc in (select groupc from pcd_cards where card = :card) "
                + ")";
        List<Object[]> results = sf.getCurrentSession().createSQLQuery(sql).setParameter("card", card).list();
        if (results.isEmpty()) {
            return null;
        }
        final Object[] row = results.get(0);
        return PcdCondAccnt.builder()
                .debIntrBaseP((BigDecimal) row[0])
                .debIntrOverPB((BigDecimal) row[1])
                .build();
    }

    @Override
    public Integer findAnnualFee(String set, String ccy, boolean which) {
        String sql = "select "
                + (which ? " annual_fee " : " annual_fee1 ")
                + "from pcd_cond_card "
                + "where cond_set='" + set
                + "' and ccy='" + ccy
                + "' and groupc='50'";
        Iterator<?> it = sf.getCurrentSession().createSQLQuery(sql).list().iterator();
        if (it.hasNext()) {
            return (int) ((BigDecimal) it.next()).doubleValue();
        } else return null;
    }

    @Override
    public Integer findCardFee(String set, String ccy, boolean which) {
        String sql = "select "
                + (which ? " card_fee " : " card_fee1 ")
                + "from pcd_cond_card "
                + "where cond_set='" + set
                + "' and ccy='" + ccy
                + "' and groupc='50'";
        Iterator<?> it = sf.getCurrentSession().createSQLQuery(sql).list().iterator();
        if (it.hasNext()) {
            return (int) ((BigDecimal) it.next()).doubleValue();
        } else return null;
    }

    @Override
    public List<PcdClient> findClientInfo(String id, String country) {
        return (List<PcdClient>) sf.getCurrentSession().createCriteria(PcdClient.class).add(Restrictions.and(
                Restrictions.or(Restrictions.eq("personCode", id), Restrictions.eq("regNr", id)),
                Restrictions.eq("region", country))).list();
    }

    @Override
    public List<PcdCard> getClientsCardsByCifOrPersonCode(String customerId, String country) {
        return (List<PcdCard>) sf.getCurrentSession().createCriteria(PcdCard.class, "card").createAlias("card.pcdAgreement", "agree").createAlias("agree.pcdClient", "client")
                .add(Restrictions.and(Restrictions.or(
                                Restrictions.eq("card.idCard", customerId),
                                Restrictions.eq("client.clientB", customerId),
                                Restrictions.and(
                                        Restrictions.eq("client.personCode", customerId),
                                        Restrictions.eq("client.clType", "1")
                                )
                        ),
                        Restrictions.ne("card.status1", "2"), // exclude closed cards
                        Restrictions.eq("card.region", country))).list();
    }

    @Override
    public List<PcdClient> getClientsFromCIF(String customerId) {
        String sql = "from PcdClient where clientB = :customerId or personCode = :customerId";
        return (List<PcdClient>) sf.getCurrentSession().createQuery(sql).setString("customerId", customerId).list();
    }

    @Override
    public String getNextPcdPinIDWithAuthentificationCode(String orderId) throws DataIntegrityException {
        SessionImpl session = (SessionImpl) sf.getCurrentSession();
        Connection connection = session.connection();
        try (PreparedStatement ps = connection.prepareStatement("SELECT PCD_PIN_ID_SEQ.nextval as nextval from dual")) {
            String pinId;
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal id = rs.getBigDecimal("nextval");
                    pinId = id.toString();
                } else {
                    throw new DataIntegrityException("Can't get nextval from sequence PCD_PIN_ID_SEQ in PCD");
                }
            }
            String crypted = Crypting3DES.encrypting(Double.toString(Math.random()).substring(2, 6));
            if (crypted == null || crypted.length() != 16) {
                throw new DataIntegrityException("Could not create crypted authentification code");
            }
            String orderIdInCode = StringUtils.leftPad(orderId, 10, ' ');
            log.info("getNextPcdPinIDWithAuthentificationCode, pinId = {}, orderIdInCode = {}, crypted= {}",
                    pinId, orderIdInCode, crypted);
            return pinId + orderIdInCode + crypted;
        } catch (SQLException e) {
            throw new DataIntegrityException("Nextval from sequence PCD_PIN_ID_SEQ (Generated value for IZD_CARDS_ADD_FIELDS.U_AFIELD1) is null. Check if this sequence is OK");
        }
    }

    @Override
    public String getNextPcdPinIDWithAuthentificationCode() throws DataIntegrityException {
        SessionImpl session = (SessionImpl) sf.getCurrentSession();
        Connection connection = session.connection();
        try {
            String pinId;
            PreparedStatement ps = connection.prepareStatement("SELECT PCD_PIN_ID_SEQ.nextval as nextval from dual");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BigDecimal id = rs.getBigDecimal("nextval");
                pinId = id.toString();
            } else {
                throw new DataIntegrityException("Can't get nextval from sequence PCD_PIN_ID_SEQ in PCD");
            }

            String crypted = Crypting3DES.encrypting(Crypting3DES.getRandomString(AUTHENTIFICATION_CODE_LENGTH));
            if (crypted == null || crypted.length() != 16) {
                throw new DataIntegrityException("Could not create crypted authentification code");
            }
            log.info("getNextPcdPinIDWithAuthentificationCode, pinId = {}, crypted= {}", pinId, crypted);
            return pinId + crypted;
        } catch (SQLException e) {
            throw new DataIntegrityException("Nextval from sequence PCD_PIN_ID_SEQ (Generated value for IZD_CARDS_ADD_FIELDS.U_AFIELD1) is null. Check if this sequence is OK");
        }
    }

    @Override
    public PcdCard getCardForTest() {
        Calendar tenDays = Calendar.getInstance();
        tenDays.add(Calendar.DAY_OF_YEAR, -10);
        return (PcdCard) sf.getCurrentSession().createCriteria(PcdCard.class).add(Restrictions.le("ctime", tenDays.getTime())).addOrder(Order.desc("ctime")).setMaxResults(1).list().get(0);
    }

    @Override
    public PcdCurrency getCurrencyByIsoNum(String isoNum) {
        return (PcdCurrency) sf.getCurrentSession().createCriteria(PcdCurrency.class).add(Restrictions.eq("isoNum", isoNum)).setMaxResults(1).list().get(0);
    }

    @Override
    public PcdBranch getBranch(String branch) {
        return (PcdBranch) sf.getCurrentSession().load(PcdBranch.class, new PcdBranchPK(branch, "23"));
    }

    @Override
    public String getProductNameByCard(String cardNumber) {
        String query = "SELECT dnb_card_product_name(c.card) as productname FROM pcd_cards c WHERE c.card=?";

        Iterator<?> it = sf.getCurrentSession().createSQLQuery(query).setString(0, cardNumber).list().iterator();
        if (it.hasNext()) {
            return (String) it.next();
        }
        return null;
    }

    @Override
    public Long getCardsOverviewForCLientTotal(String client, String accountNumber, String cardStatus,
                                               String deliveryBlock, String cardtype, String holderName, String lastDigits) {

        String hqlQuery = "SELECT COUNT(distinct pcdCard.card) " +
                getFromAndWhereForOverview(client, accountNumber, cardStatus, deliveryBlock, cardtype,
                        holderName, lastDigits);
        Query q = sf.getCurrentSession().createQuery(hqlQuery);
        Iterator<?> i = q.iterate();
        if (i.hasNext()) {
            return (Long) i.next();
        }
        return 0L;
    }

    private String getFromAndWhereForOverview(String client, String accountNumber, String cardStatus,
                                              String deliveryBlock, String cardtype, String holderName, String lastDigits) {
        String[] holderNames = new String[0];
        if (!StringUtils.isBlank(holderName))
            holderNames = holderName.split("\\s+");
        String query = "FROM PcdAccount pcdAccount, PcdCardsAccount pcdCardsAccount, "
                + "PcdCard pcdCard, PcdStopCause pcdStopCause, PcdCondCard pcdCondCard, PcdClient pcdClient, "
                + "PcdBranch pcdBranch WHERE "
                + (StringUtils.isBlank(client) ? "" : "pcdClient.clientB = '" + client + "' and ")
                + "pcdAccount.client = pcdClient.comp_id.client and "
                + (StringUtils.isBlank(accountNumber) ? "" : "pcdAccount.cardAcct='" + accountNumber + "' and ")
                + "pcdAccount.accountNo=pcdCardsAccount.comp_id.accountNo and "
                + "pcdCardsAccount.comp_id.card=pcdCard.card and "
                + "pcdStopCause.comp_id.cause=pcdCard.stopCause and "
                + "pcdStopCause.comp_id.bankC=pcdCard.bankC and "
                + "pcdCard.bankC=pcdCondCard.comp_id.bankC and "
                + "pcdCard.groupc=pcdCondCard.comp_id.groupc and "
                + "pcdCard.condSet=pcdCondCard.comp_id.condSet and "
                + "pcdAccount.pcdAccParam.pcdCurrency.isoAlpha=pcdCondCard.comp_id.ccy and "
                + "pcdCard.UCod10 = pcdBranch.comp_id.branch and "
                + "pcdBranch.comp_id.bankC = pcdCard.bankC and "
                + (StringUtils.isBlank(cardStatus) ? "pcdCard.status1 <> 2 and " : " pcdCard.status1 in " + cardStatus + " and ")
                + (StringUtils.isBlank(deliveryBlock) ? "" : " pcdCard.deliveryBlock = '" + deliveryBlock + "' and ")
                + (StringUtils.isBlank(lastDigits) ? "" : " pcdCard.card LIKE '%" + lastDigits + "' and ")
                + (StringUtils.isBlank(cardtype) ? "" : " pcdCard.card LIKE '" + cardtype + "%' and ");

        // Add name criteria
        for (String name : holderNames) {
            query += "pcdCard.cardName LIKE '%" + name + "%' and ";
        }
        query += "1 = 1 "; // Dummy predicate, just that we can put "and" at the end of each line

        return query;
    }

    @Override
    public List<Object[]> getCardsOverviewForCLient(String client, String accountNumber, String cardStatus,
                                                    String deliveryBlock, String cardtype, String holderName, String lastDigits, int fromRecord, int numberOfRecords) {
        List<Object[]> retData = new ArrayList<Object[]>();


        String hqlQuery = "SELECT distinct pcdCard.card as cardNumber, pcdCard.baseSupp as mainSupp, pcdCard.status1 as cardStatus, pcdCard.binCode as BINcode, "
                + "pcdCard.deliveryBlock as deliveryStatus, pcdCard.expiry1 as expiryDate, pcdCard.expiry2 as expiry2, pcdCard.condSet as conditionSet, "
                + "pcdCard.cardName as cardHolder, pcdStopCause.statusCode as blockReason, pcdCondCard.name as conditionSetName, "
                + "pcdCard.uAField2 as pinStatus, pcdCard.uBField1 as deliveryAddress, pcdBranch.regKodsUr as deliveryBranch, "
                + "pcdCard.deliveryBlock as deliveryBlock, pcdAccount.cardAcct as account, pcdCard.pinBlock as pinBlock, "
                + "pcdCard.renew as automaticRenewal, pcdCard.contactless as contactless "
                + getFromAndWhereForOverview(client, accountNumber, cardStatus, deliveryBlock, cardtype, holderName, lastDigits)
                + "ORDER BY pcdAccount.cardAcct, pcdCard.card";

        Query q = sf.getCurrentSession().createQuery(hqlQuery);
        q.setFirstResult(fromRecord);
        if (numberOfRecords != 0)
            q.setMaxResults(numberOfRecords);
        Iterator<?> i = q.iterate();
        while (i.hasNext()) {
            Object[] tuple = (Object[]) i.next();
            retData.add(tuple);
        }
        return retData;
    }

    @Override
    public String getCifByCardNumber(String card) {

        String hqlQuery = "SELECT pcdCard.pcdAgreement.pcdClient.clientB as customerNumber "
                + "FROM PcdCard pcdCard "
                + "WHERE pcdCard.card='"
                + card
                + "'";
        Query q = sf.getCurrentSession().createQuery(hqlQuery);
        Iterator<?> i = q.iterate();
        if (i.hasNext()) {
            try {
                String b = i.next().toString();
                return b;
            } catch (NullPointerException e) {
                return null;
            }
        } else return null;
    }

    @Override
    public void setAutomaticRenewFlag(String card, String renewFlag) {
        PcdCard pcdCard = (PcdCard) sf.getCurrentSession().load(PcdCard.class, card);
        pcdCard.setRenew(renewFlag);
        sf.getCurrentSession().saveOrUpdate(pcdCard);
    }

    @Override
    public void setRenewOld(String card, String renewOld) {
        PcdCard pcdCard = (PcdCard) sf.getCurrentSession().load(PcdCard.class, card);
        pcdCard.setRenewOld(renewOld);
        sf.getCurrentSession().saveOrUpdate(pcdCard);
    }

    @Override
    public Object[] getCardDetailsByCard(String cardNumber) {

        String hqlQuery = "SELECT pcdCard.card as card, "
                + "pcdCard.riskLevel as riskLevel, "
                + "pcdCard.UField7 as smsService, "
                + "pcdCard.pcdAgreement.pcdClient.RPhone as phoneNumber, "
                + "pcdCard.insuranceFlag as withInsurance, "
                + "pcdCard.maxima as maximaAllowance, "
                + "pcdCard.MName as password, "
                + "pcdCard.renew as automaticRenewal, "
                + "pcdCard.UField8 as cardDesignId, "
                + "pcdCard.cardName as cardLine1, "
                + "pcdCard.cmpgName as cardline2, "
                + "pcdCard.expiry1 as expiry1, "
                + "pcdCard.expiry2 as expiry2, "
                + "pcdBranch.regKodsUr as deliverToBranch, "
                + "pcdAccount.cardAcct as accountNumber, "
                + "pcdCard.pcdAgreement.pcdClient.clientB as customerNumber, "
                //+ "pcdCard.status1 as cardStatus, "
                + "pcdAccount.iban as iban, "
                + "pcdAccount.pcdAccParam.pcdCurrency.isoAlpha as currency, "
                + "pcdCard.brId as brId "
                + "FROM PcdAccount pcdAccount, PcdCardsAccount pcdCardsAccount, PcdCard pcdCard, PcdBranch pcdBranch "
                + "WHERE pcdCard.card='"
                + cardNumber
                + "' AND pcdAccount.accountNo=pcdCardsAccount.comp_id.accountNo AND pcdCardsAccount.comp_id.card=pcdCard.card "
                + "AND pcdCard.UCod10 = pcdBranch.comp_id.branch";
        Query q = sf.getCurrentSession().createQuery(hqlQuery);
        Iterator<?> i = q.iterate();
        if (i.hasNext())
            return (Object[]) i.next();
        else
            return null;

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> findAccountsWithoutCards(String datetime) {
        String sql =
                " SELECT distinct pcdaccount0_.card_acct AS account_number "
                        + " FROM pcd_accounts pcdaccount0_, pcd_acc_params pcdaccparams0_, pcd_cards_accounts pcdcardsac0_, pcd_cards pcdcard0_ "
                        + " WHERE pcdaccount0_.account_no=pcdaccparams0_.account_no AND "
                        + " pcdaccount0_.card_acct not like 'L%' AND "
                        + " pcdaccparams0_.status!=4 AND "
                        + " pcdaccount0_.account_no=pcdcardsac0_.account_no AND "
                        + " pcdcard0_.card=pcdcardsac0_.card AND "
                        + " pcdcard0_.status1=2 AND "
                        + " (pcdcard0_.ctime >=to_date('" + datetime + "', 'YYYY-MM-DD HH24:MI:SS') "
                        + " OR "
                        + " pcdaccount0_.ctime>=to_date('" + datetime + "', 'YYYY-MM-DD HH24:MI:SS') "
                        + " ) AND "
                        + " NOT exists (SELECT pcdcardsac1_.account_no "
                        + " FROM pcd_cards_accounts pcdcardsac1_, "
                        + " pcd_cards pcdcard2_ "
                        + " WHERE pcdcardsac1_.card=pcdcard2_.card "
                        + " AND pcdaccount0_.account_no=pcdcardsac1_.account_no "
                        + " AND (pcdcard2_.status1 = 0 "
                        + " OR pcdcard2_.status1 = 1 )) ";

        SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addScalar("account_number", StringType.INSTANCE);
        return sqlQuery.list();
    }

    @Override
    public SQLQuery findCardDataHandoffLT(String datetime) {
        String sql = "select  "
                + " GREATEST(pcd_cards.ctime, pcd_accounts.ctime, pcd_acc_params.ctime, pcd_agreements.ctime, pcd_clients.ctime, pcd_branches.ctime, nvl(pcd_companies.ctime, pcd_cards.ctime)) as business_date, "
                + " pcd_cards.card as card_nr, "
                + " pcd_accounts.card_acct as fc_account_no, " //14.06.2011 - deal_id replaced with fc_account_no for Lithuania branch(card_acct = account nr. in FlexCube)
                + " pcd_clients.client_b as customer_nr, " //07.06.2011 - pcd_clients.client replaced with pcd_clients.client_b for Lithuania branch (client_b = client nr. in FlexCube)
                + " pcd_cards.rec_date as emission_date, "
                + " pcd_cards.bin_code as bin_code, "
                + " pcd_cards.c_type as norm_charge, "
                + " pcd_cards.groupc as groupc, "
                + " pcd_cards.base_supp as base_supp, "
                + " pcd_cards.cond_set as cond_set,  "
                + " pcd_cards.risk_level as risk_level, "
                + " pcd_cards.status1 as crd_status1, "
                + " pcd_cards.status2 as crd_status2, "
                + " pcd_cards.expiry1 as expiry1, "
                + " pcd_cards.expiry2 as expirity2, "
                + " pcd_cards.renew as renew, "
                + " pcd_cards.card_name as card_name, "
                + " pcd_cards.stop_cause as stop_cause, "
                + " pcd_cards.u_field8 as ind_design, "
                + " pcd_branches.reg_kods_vid as branch, "
                + " pcd_cards.design_id as design_id, "
                + " pcd_cards.chip_app_id as chip_app_id, "
                + " pcd_cards.insurance_flag as insurance_flag, "
                + " pcd_cards.ins_begin_date as ins_begin_date, "
                + " pcd_cards.ins_end_date as ins_end_date, "
                + " pcd_accounts.account_no as account_no, "
                + " pcd_accounts.iban as iban, "
                + " pcd_acc_params.status as acc_status, "
                + " pcd_acc_params.ccy_iso_alpha as ccy, "
                + " pcd_acc_params.stop_date as acc_stop_date, "
                + " pcd_acc_params.created_date as acc_created_date, "
                + " pcd_clients.emp_code as emp_code,  "
                + " pcd_clients.emp_name as emp_name, "
                + " pcd_agreements.contract as contract, "
                + " pcd_cards.maxima as  maxima, "
                + " pcd_cards.mediator as mediator, "
                + " pcd_companies.okp_code as max_prior, "
                + " pcd_cards.operator as operator, "
                + " pcd_cards.creation_branch as creation_branch, "
                + " pcd_cards.delivery_block as delivery_block, "
                + " pcd_cards.issued_by as issued_by, "
                + " pcd_cards.issue_date as issue_date"
                + " from pcd_cards, pcd_accounts, pcd_cards_accounts, pcd_acc_params, pcd_agreements, pcd_clients, pcd_branches, pcd_companies "
                + " where (pcd_cards.ctime>to_date('" + datetime + "','YYYY-MM-DD HH24:MI:SS')  "
                + " or pcd_accounts.ctime>to_date('" + datetime + "','YYYY-MM-DD HH24:MI:SS')  "
                + " or pcd_acc_params.ctime>to_date('" + datetime + "','YYYY-MM-DD HH24:MI:SS')  "
                + " or pcd_agreements.ctime>to_date('" + datetime + "','YYYY-MM-DD HH24:MI:SS') "
                + " or pcd_clients.ctime>to_date('" + datetime + "','YYYY-MM-DD HH24:MI:SS') "
                + " or pcd_branches.ctime>to_date('" + datetime + "','YYYY-MM-DD HH24:MI:SS') "
                + " or pcd_companies.ctime>to_date('" + datetime + "','YYYY-MM-DD HH24:MI:SS') "
                + " ) "
                + " and pcd_cards.card = pcd_cards_accounts.card and pcd_cards_accounts.account_no = pcd_accounts.account_no "
                + " and pcd_accounts.account_no = pcd_acc_params.account_no and pcd_accounts.bank_c = pcd_cards.bank_c  "
                + " and pcd_cards.agreement = pcd_agreements.agreement  and pcd_cards.groupc  = pcd_agreements.groupc "
                + " and pcd_agreements.bank_c = pcd_cards.bank_c  and pcd_agreements.client = pcd_clients.client  "
                + " and pcd_clients.bank_c = pcd_cards.bank_c and pcd_branches.bank_c = pcd_cards.bank_c "
                + " and pcd_cards.br_id = pcd_branches.b_br_id  "
                + " and pcd_clients.emp_code = pcd_companies.code(+)"
                + " and pcd_clients.bank_c = pcd_companies.bank_c(+)";
        //+ " and rownum<100";

        SQLQuery sqlQuery = sf.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addScalar("business_date", TimestampType.INSTANCE);//0
        sqlQuery.addScalar("card_nr", StringType.INSTANCE);//1
        sqlQuery.addScalar("fc_account_no", StringType.INSTANCE);//2
        sqlQuery.addScalar("customer_nr", StringType.INSTANCE);//3
        sqlQuery.addScalar("emission_date", DateType.INSTANCE);//4
        sqlQuery.addScalar("bin_code", StringType.INSTANCE);//5
        sqlQuery.addScalar("norm_charge", StringType.INSTANCE);//6
        sqlQuery.addScalar("groupc", StringType.INSTANCE);//7
        sqlQuery.addScalar("base_supp", StringType.INSTANCE);//8
        sqlQuery.addScalar("cond_set", StringType.INSTANCE);//9
        sqlQuery.addScalar("risk_level", StringType.INSTANCE);//10
        sqlQuery.addScalar("crd_status1", StringType.INSTANCE);//11
        sqlQuery.addScalar("crd_status2", StringType.INSTANCE);//12
        sqlQuery.addScalar("expiry1", DateType.INSTANCE);//13
        sqlQuery.addScalar("expirity2", DateType.INSTANCE);//14
        sqlQuery.addScalar("renew", StringType.INSTANCE);//15
        sqlQuery.addScalar("card_name", StringType.INSTANCE);//16
        sqlQuery.addScalar("stop_cause", StringType.INSTANCE);//17
        sqlQuery.addScalar("ind_design", StringType.INSTANCE);//18
        sqlQuery.addScalar("branch", StringType.INSTANCE);//19
        sqlQuery.addScalar("design_id", BigDecimalType.INSTANCE);//20
        sqlQuery.addScalar("chip_app_id", BigDecimalType.INSTANCE);//21
        sqlQuery.addScalar("insurance_flag", StringType.INSTANCE);//22
        sqlQuery.addScalar("ins_begin_date", DateType.INSTANCE);//23
        sqlQuery.addScalar("ins_end_date", DateType.INSTANCE);//24
        sqlQuery.addScalar("account_no");//25
        sqlQuery.addScalar("iban", StringType.INSTANCE);//26
        sqlQuery.addScalar("acc_status", StringType.INSTANCE);//27
        sqlQuery.addScalar("ccy", StringType.INSTANCE);//28
        sqlQuery.addScalar("acc_stop_date", DateType.INSTANCE);//29
        sqlQuery.addScalar("acc_created_date", DateType.INSTANCE);//30
        sqlQuery.addScalar("emp_code", StringType.INSTANCE);//31
        sqlQuery.addScalar("emp_name", StringType.INSTANCE);//32
        sqlQuery.addScalar("contract", StringType.INSTANCE);//33
        sqlQuery.addScalar("maxima", StringType.INSTANCE);//34
        sqlQuery.addScalar("mediator", StringType.INSTANCE);//35
        sqlQuery.addScalar("max_prior", StringType.INSTANCE);//36
        sqlQuery.addScalar("operator", StringType.INSTANCE);//37
        sqlQuery.addScalar("creation_branch", StringType.INSTANCE);//38
        sqlQuery.addScalar("delivery_block", StringType.INSTANCE);//39
        sqlQuery.addScalar("issued_by", StringType.INSTANCE);//40
        sqlQuery.addScalar("issue_date", DateType.INSTANCE);//41
        return sqlQuery;
    }

    @Override
    public List<PcdCard> getCardsByCardholderPersonCode(String personCode, String country) {
        return (List<PcdCard>) sf.getCurrentSession().createCriteria(PcdCard.class, "card").add(Restrictions.and(
                Restrictions.eq("card.idCard", personCode),
                Restrictions.eq("card.region", country))
        ).list();
    }

    @Override
    public PcdCardPendingTokenization getPcdCardPendingTokenizationByWalletAccountId(String cardNumber, String walletAccountId) {
        return (PcdCardPendingTokenization) sf.getCurrentSession().createCriteria(PcdCardPendingTokenization.class)
                .add(Restrictions.eq("comp_id.card", cardNumber))
                .add(Restrictions.eq("walletAccountId", walletAccountId))
                .uniqueResult();
    }

    @Override
    public PcdCardPendingTokenization getPcdCardPendingTokenizationByCorrId(String cardNumber, String corrId) {
        return (PcdCardPendingTokenization) sf.getCurrentSession().createCriteria(PcdCardPendingTokenization.class)
                .add(Restrictions.eq("comp_id.card", cardNumber))
                .add(Restrictions.eq("corrId", corrId))
                .uniqueResult();
    }

    @Override
    public PcdCardPendingTokenization getPcdCardPendingTokenizationByKey(String cardNumber, String walletDeviceId) {
        PcdCardPendingTokenizationPK pk = new PcdCardPendingTokenizationPK();
        pk.setCard(cardNumber);
        pk.setWalletDeviceId(walletDeviceId);
        return (PcdCardPendingTokenization) sf.getCurrentSession().get(PcdCardPendingTokenization.class, pk);
    }

}
