
package lv.bank.cards.dbsynchronizer;

import lombok.extern.slf4j.Slf4j;
import lv.bank.cards.core.entity.cms.DnbIzdAccntChng;
import lv.bank.cards.core.entity.cms.IzdAccParam;
import lv.bank.cards.core.entity.cms.IzdAccount;
import lv.bank.cards.core.entity.cms.IzdAgreement;
import lv.bank.cards.core.entity.cms.IzdBinTable;
import lv.bank.cards.core.entity.cms.IzdBranch;
import lv.bank.cards.core.entity.cms.IzdCard;
import lv.bank.cards.core.entity.cms.IzdCardGroup;
import lv.bank.cards.core.entity.cms.IzdCardType;
import lv.bank.cards.core.entity.cms.IzdCcyConvEx;
import lv.bank.cards.core.entity.cms.IzdCcyTable;
import lv.bank.cards.core.entity.cms.IzdClient;
import lv.bank.cards.core.entity.cms.IzdCompany;
import lv.bank.cards.core.entity.cms.IzdCondAccnt;
import lv.bank.cards.core.entity.cms.IzdCondCard;
import lv.bank.cards.core.entity.cms.IzdDbOwner;
import lv.bank.cards.core.entity.cms.IzdRepDistribut;
import lv.bank.cards.core.entity.cms.IzdRepLang;
import lv.bank.cards.core.entity.cms.IzdServicesFee;
import lv.bank.cards.core.entity.cms.IzdStopCause;
import lv.bank.cards.core.entity.cms.MerchantPar;
import lv.bank.cards.core.entity.cms.MerchantsView;
import lv.bank.cards.core.entity.linkApp.PcdAccParam;
import lv.bank.cards.core.entity.linkApp.PcdAccount;
import lv.bank.cards.core.entity.linkApp.PcdAgreement;
import lv.bank.cards.core.entity.linkApp.PcdBin;
import lv.bank.cards.core.entity.linkApp.PcdBranch;
import lv.bank.cards.core.entity.linkApp.PcdCard;
import lv.bank.cards.core.entity.linkApp.PcdCardGroup;
import lv.bank.cards.core.entity.linkApp.PcdCardType;
import lv.bank.cards.core.entity.linkApp.PcdCcyConv;
import lv.bank.cards.core.entity.linkApp.PcdClient;
import lv.bank.cards.core.entity.linkApp.PcdCompany;
import lv.bank.cards.core.entity.linkApp.PcdCondAccnt;
import lv.bank.cards.core.entity.linkApp.PcdCondCard;
import lv.bank.cards.core.entity.linkApp.PcdCurrency;
import lv.bank.cards.core.entity.linkApp.PcdDbOwner;
import lv.bank.cards.core.entity.linkApp.PcdMerchant;
import lv.bank.cards.core.entity.linkApp.PcdMerchantPar;
import lv.bank.cards.core.entity.linkApp.PcdProcIds;
import lv.bank.cards.core.entity.linkApp.PcdRepDistribut;
import lv.bank.cards.core.entity.linkApp.PcdRepLang;
import lv.bank.cards.core.entity.linkApp.PcdServicesFee;
import lv.bank.cards.core.entity.linkApp.PcdStopCause;
import lv.bank.cards.dbsynchronizer.utils.ContactlessSqlQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LV_TEST_PROFILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@AutoConfigureMockMvc
@PropertySource(value = "${app.config.location.test-lv}")
@ComponentScan({"lv.nordlb.cards.pcdabaNG.dbsync", "lv.bank.cards.dbsynchronizer"})
@ActiveProfiles({COUNTRY_LV_TEST_PROFILE})
public class DBSynchronizerTestLV extends DBSynchronizerTestBase{

    //TODO move to base class
    public Session cmsSession() throws HibernateException {
        Session sess;
        try {
            log.info("getting current cmsSession");
            System.out.println("getting current pcdSession");
            sess = cmsSessionFactory.getCurrentSession();
        } catch (org.hibernate.HibernateException he) {
            log.info("Opening new cmsSession");
            System.out.println("Opening new cmsSession");
            sess = cmsSessionFactory.openSession();
        }
        return sess;
    }

    //TODO move to base class
    public Session pcdSession() throws HibernateException {
        Session sess;
        try {
            log.info("getting current pcdSession");
            System.out.println("getting current pcdSession");
            sess = pcdSessionFactory.getCurrentSession();
        } catch (org.hibernate.HibernateException he) {
            log.info("Opening new pcdSession");
            System.out.println("Opening new pcdSession");
            sess = pcdSessionFactory.openSession();
        }
        return sess;
    }

    //TODO move to base class
    public Session rtpsSession() throws HibernateException {
        Session sess;
        try {
            log.info("getting current rtpsSession");
            System.out.println("getting current rtpsSession");
            sess = rtpsSessionFactory.getCurrentSession();
        } catch (org.hibernate.HibernateException he) {
            log.info("Opening new rtpsSession");
            System.out.println("Opening new rtpsSession");
            sess = rtpsSessionFactory.openSession();
        }
        return sess;
    }

    @Test
    public void checkContactlessQuery() {
        BigDecimal contactless = (BigDecimal) cmsSession().createSQLQuery(
                ContactlessSqlQuery.getContaclessQuery("4921754900929886"))
                .list().get(0);
        assertNull(contactless);
    }



    @Test
    public void initDBSynchronizer(){
        //dBSynchronizer.updateDB();
    }

    @Test
    public void checkUpdateQueries(){
        Map<Class<? extends Serializable>, Class<? extends Serializable>> map = new HashMap<>();
        map.put(IzdDbOwner.class, PcdDbOwner.class);
        map.put(IzdStopCause.class, PcdStopCause.class);
        map.put(IzdCardType.class, PcdCardType.class);
        map.put(IzdBinTable.class, PcdBin.class);
        map.put(IzdRepLang.class, PcdRepLang.class);
        map.put(IzdRepDistribut.class, PcdRepDistribut.class);
        map.put(IzdClient.class, PcdClient.class);
        map.put(IzdCardGroup.class, PcdCardGroup.class);
        map.put(IzdBranch.class, PcdBranch.class);
        map.put(IzdCompany.class, PcdCompany.class);
        map.put(IzdCcyTable.class, PcdCurrency.class);
        map.put(IzdAgreement.class, PcdAgreement.class);
        map.put(IzdAccount.class, PcdAccount.class);
        map.put(IzdAccParam.class, PcdAccParam.class);
        map.put(IzdCondCard.class, PcdCondCard.class);
        map.put(IzdCard.class, PcdCard.class);
        map.put(IzdServicesFee.class, PcdServicesFee.class);
        map.put(IzdCondAccnt.class, PcdCondAccnt.class);
        map.put(IzdCcyConvEx.class, PcdCcyConv.class);

        Map<Class<? extends Serializable>, Class<? extends Serializable>> all = new HashMap<>();
        all.put(MerchantsView.class, PcdMerchant.class);
        all.put(MerchantPar.class, PcdMerchantPar.class);

        Calendar past = Calendar.getInstance();
        past.set(2000, 01, 01);

        Session cmsSession = cmsSession();

        for (Map.Entry<Class<? extends Serializable>, Class<? extends Serializable>> entry : map.entrySet()) {
            Class<? extends Serializable> clazz = entry.getKey();
            System.out.println("check = " + clazz.getName() + " | " + entry.getValue());

            Query q;

            if (customQueries.getCustomQueries().containsKey(clazz.getName())) {
                q = cmsSession.createQuery(customQueries.getCustomQueries().get(clazz.getName()));
            } else {
                q = cmsSession.getNamedQuery("get.updated.items." + clazz.getName() + ".to." + entry.getValue().getName());
            }

            List<?> l = q.setTimestamp("lastWaterMark", past.getTime())
                    .setTimestamp("currentWaterMark", new Date())
                    .setMaxResults(1).list();
            assertEquals(1, l.size());
        }

        for (Map.Entry<Class<? extends Serializable>, Class<? extends Serializable>> entry : all.entrySet()) {
            Class<? extends Serializable> clazz = entry.getKey();
            System.out.println("check = " + clazz.getName() + " | " + entry.getValue());
            List<?> l = cmsSession
                    .getNamedQuery("get.all.items." + clazz.getName() + ".to." + entry.getValue().getName())
                    .setMaxResults(1)
                    .list();
            assertEquals(1, l.size());
        }

        System.out.println("check " + DnbIzdAccntChng.class.getName());
        List<?> l = cmsSession
                .getNamedQuery("get.updated.items." + DnbIzdAccntChng.class.getName() + ".to." + PcdProcIds.class.getName())
                .setMaxResults(1).list();
        assertNotNull(l); // This table is empty in test DB
    }
/*
    @Test
    public void pcdEntities() throws IOException, ClassNotFoundException {
        JBossConfiguration pcdConfig = new JBossConfiguration(System.getenv("jboss_location") + "standalone/configuration/standalone.xml", "PCD");
        Session session;
        Configuration destination_cfg = new Configuration()
                .addAnnotatedClass(PcdAccount.class)
                .addAnnotatedClass(PcdAccParam.class)
                .addAnnotatedClass(PcdAgreement.class)
                .addAnnotatedClass(PcdAtmAdvert.class)
                .addAnnotatedClass(PcdAtmAdvertSpecial.class)
                .addAnnotatedClass(PcdAuthBatch.class)
                .addAnnotatedClass(PcdAuthPosIsoHostMessage.class)
                .addAnnotatedClass(PcdAuthSource.class)
                .addAnnotatedClass(PcdBin.class)
                .addAnnotatedClass(PcdBranch.class)
                .addAnnotatedClass(PcdCard.class)
                .addAnnotatedClass(PcdCardCondExt.class)
                .addAnnotatedClass(PcdCardGroup.class)
                .addAnnotatedClass(PcdCardsAccount.class)
                .addAnnotatedClass(PcdCardType.class)
                .addAnnotatedClass(PcdCcyConv.class)
                .addAnnotatedClass(PcdClient.class)
                .addAnnotatedClass(PcdCompany.class)
                .addAnnotatedClass(PcdCondAccnt.class)
                .addAnnotatedClass(PcdCondCard.class)
                .addAnnotatedClass(PcdCurrency.class)
                .addAnnotatedClass(PcdDbOwner.class)
                .addAnnotatedClass(PcdDesign.class)
                .addAnnotatedClass(PcdLastUpdated.class)
                .addAnnotatedClass(PcdLog.class)
                .addAnnotatedClass(PcdMerchant.class)
                .addAnnotatedClass(PcdMerchantPar.class)
                .addAnnotatedClass(PcdPpCard.class)
                .addAnnotatedClass(PcdProcIds.class)
                .addAnnotatedClass(PcdRepDistribut.class)
                .addAnnotatedClass(PcdRepLang.class)
                .addAnnotatedClass(PcdServicesFee.class)
                .addAnnotatedClass(PcdSlip.class)
                .addAnnotatedClass(PcdStopCause.class)
                .addAnnotatedClass(RtcuRequestTemplate.class);

        Properties props = new Properties();

        try (InputStream is = DBSynchronizer.class.getResourceAsStream("/DBSynchronizer_destination.properties")) {
            if (is == null) {
                try (InputStream is2 = DBSynchronizer.class.getResourceAsStream("/resources/DBSynchronizer_destination.properties")) {
                    props.load(is2);
                }
            } else {
                props.load(is);
            }
        }
        props.putAll(pcdConfig.getConfig());
        destination_cfg.setProperties(props);
        ServiceRegistry dstServiceRegistry = new ServiceRegistryBuilder().applySettings(destination_cfg.getProperties()).buildServiceRegistry();
        session = destination_cfg.buildSessionFactory(dstServiceRegistry).openSession();
        Iterator<PersistentClass> it = destination_cfg.getClassMappings();
        while (it.hasNext()) {
            PersistentClass clazz = it.next();
            System.out.println("Check class " + clazz.getEntityName());
            Long count = (Long) session.createQuery("SELECT count(obj) FROM " + clazz.getEntityName() + " obj ").list().get(0);
            System.out.println(clazz.getEntityName() + " - " + count);
            if (count > 0) {
                assertNotNull(session);
                assertNotNull(clazz);
                List<?> l = session.createQuery("SELECT obj FROM " + clazz.getEntityName() + " obj ORDER BY obj DESC").setMaxResults(1).list();
                assertNotNull(l);
                assertNotNull(l.get(0));
                Class<?> cl = Class.forName(clazz.getEntityName());
                assertTrue(cl.isInstance(l.get(0)));
            } else {
                System.out.println("Don't have records for class " + clazz.getEntityName());
            }
            session.clear();
        }
    }

    @Test
    public void izdEntities() throws IOException, ClassNotFoundException {
        System.setProperty("DBSynTest", "true");
        JBossConfiguration pcdConfig = new JBossConfiguration(System.getenv("jboss_location") + "standalone/configuration/standalone.xml", "CMS");
        Session session;
        Configuration destination_cfg = new Configuration()
                .addAnnotatedClass(DnbIzdAccntChng.class)
                .addAnnotatedClass(IzdAccntAc.class)
                .addAnnotatedClass(IzdAccount.class)
                .addAnnotatedClass(IzdAccParam.class)
                .addAnnotatedClass(IzdAgreement.class)
                .addAnnotatedClass(IzdBinCardDesigns.class)
                .addAnnotatedClass(IzdBinTable.class)
                .addAnnotatedClass(IzdBranch.class)
                .addAnnotatedClass(IzdCard.class)
                .addAnnotatedClass(IzdCardDesigns.class)
                .addAnnotatedClass(IzdCardGroup.class)
                .addAnnotatedClass(IzdCardGroupCcy.class)
                .addAnnotatedClass(IzdCardsAddFields.class)
                .addAnnotatedClass(IzdCardsPinBlocks.class)
                .addAnnotatedClass(IzdCardType.class)
                .addAnnotatedClass(IzdCategory.class)
                .addAnnotatedClass(IzdCcyConvEx.class)
                .addAnnotatedClass(IzdCcyTable.class)
                .addAnnotatedClass(IzdClAcct.class)
                .addAnnotatedClass(IzdClient.class)
                .addAnnotatedClass(IzdCompany.class)
                .addAnnotatedClass(IzdCondAccnt.class)
                .addAnnotatedClass(IzdCondCard.class)
                .addAnnotatedClass(IzdConfig.class)
                .addAnnotatedClass(IzdCountry.class)
                .addAnnotatedClass(IzdDbOwner.class)
                .addAnnotatedClass(IzdInFile.class)
                .addAnnotatedClass(IzdLvl2eurAccountsMap.class)
                .addAnnotatedClass(IzdOfferedProduct.class)
                .addAnnotatedClass(IzdPreProcessingRow.class)
                .addAnnotatedClass(IzdRepDistribut.class)
                .addAnnotatedClass(IzdRepLang.class)
                .addAnnotatedClass(IzdServicesFee.class)
                .addAnnotatedClass(IzdStopCause.class)
                .addAnnotatedClass(IzdStoplist.class)
                .addAnnotatedClass(IzdTrtypeName.class)
                .addAnnotatedClass(IzdValidProductCcy.class)
                .addAnnotatedClass(MerchantPar.class)
                .addAnnotatedClass(MerchantsView.class)
                .addAnnotatedClass(NordlbBranch.class)
                .addAnnotatedClass(NordlbFileId.class)
                .addAnnotatedClass(IzdChipCmdHist.class);

        Properties props = new Properties();

        try (InputStream is = DBSynchronizer.class.getResourceAsStream("/DBSynchronizer_source.properties")) {
            if (is == null) {
                if ("true".equals(System.getProperty("DBSynTest"))) {
                    try (InputStream is2 = DBSynchronizer.class.getResourceAsStream("/resources/DBSynchronizer_source.properties")) {
                        props.load(is2);
                    }
                }
            } else {
                props.load(is);
            }
        }
        props.putAll(pcdConfig.getConfig());
        destination_cfg.setProperties(props);
        ServiceRegistry dstServiceRegistry = new ServiceRegistryBuilder().applySettings(destination_cfg.getProperties()).buildServiceRegistry();
        session = destination_cfg.buildSessionFactory(dstServiceRegistry).openSession();
        Iterator<PersistentClass> it = destination_cfg.getClassMappings();
        while (it.hasNext()) {
            PersistentClass clazz = it.next();
            System.out.println("Check class " + clazz.getEntityName());
            Long count = (Long) session.createQuery("SELECT count(obj) FROM " + clazz.getEntityName() + " obj ").list().get(0);
            System.out.println(clazz.getEntityName() + " - " + count);
            if (count > 0 && !clazz.getEntityName().endsWith("IzdBinCardDesigns")) {
                String orderColumn = clazz.getEntityName().endsWith("NordlbFileId") ||
                        clazz.getEntityName().endsWith("MerchantsView") ||
                        clazz.getEntityName().endsWith("MerchantPar") ||
                        clazz.getEntityName().endsWith("NordlbBranch") ||
                        clazz.getEntityName().endsWith("IzdLvl2eurAccountsMap") ? "" : ".ctime";
                assertNotNull(session);
                assertNotNull(clazz);
                List<?> l = session.createQuery("SELECT obj FROM " + clazz.getEntityName() + " obj ORDER BY obj" + orderColumn + " DESC").setMaxResults(1).list();
                assertNotNull(l);
                assertNotNull(l.get(0));
                Class<?> cl = Class.forName(clazz.getEntityName());
                assertTrue(cl.isInstance(l.get(0)));
            } else {
                System.out.println("Don't have records for class " + clazz.getEntityName());
            }
            session.clear();
        }
    }

    @Test
    public void rtpsEntities() throws IOException, ClassNotFoundException {
        JBossConfiguration pcdConfig = new JBossConfiguration(System.getenv("jboss_location") + "standalone/configuration/standalone.xml", "RTPS");
        Session session;
        Configuration destination_cfg = new Configuration()
                .addAnnotatedClass(AnswerCode.class)
                .addAnnotatedClass(CardsException.class)
                .addAnnotatedClass(CurrencyCode.class)
                .addAnnotatedClass(ProcessingEntity.class)
                .addAnnotatedClass(Regdir.class) // Table not found or not visible
                .addAnnotatedClass(StipAccount.class)
                .addAnnotatedClass(StipCard.class)
                .addAnnotatedClass(StipClient.class)
                .addAnnotatedClass(StipEventsN.class)
                .addAnnotatedClass(StipLocks.class)
                .addAnnotatedClass(StipLocksMatch.class)
                .addAnnotatedClass(StipRestracnt.class)
                .addAnnotatedClass(StipRmsStoplist.class)
                .addAnnotatedClass(StipStoplist.class)
                .addAnnotatedClass(StipParamList.class)
                .addAnnotatedClass(StipParamN.class);

        Properties props = new Properties();

        try (InputStream is = DBSynchronizer.class.getResourceAsStream("/DBSynchronizer_rtps.properties")) {
            if (is == null) {
                if ("true".equals(System.getProperty("DBSynTest"))) {
                    try (InputStream is2 = DBSynchronizer.class.getResourceAsStream("/resources/DBSynchronizer_rtps.properties")) {
                        props.load(is2);
                    }
                }
            } else {
                props.load(is);
            }
        }
        props.putAll(pcdConfig.getConfig());
        destination_cfg.setProperties(props);
        ServiceRegistry dstServiceRegistry = new ServiceRegistryBuilder().applySettings(destination_cfg.getProperties()).buildServiceRegistry();
        session = destination_cfg.buildSessionFactory(dstServiceRegistry).openSession();
        Iterator<PersistentClass> it = destination_cfg.getClassMappings();
        while (it.hasNext()) {
            PersistentClass clazz = it.next();
            System.out.println("Check class " + clazz.getEntityName());
            Long count = (Long) session.createQuery("SELECT count(obj) FROM " + clazz.getEntityName() + " obj ").list().get(0);
            System.out.println(clazz.getEntityName() + " - " + count);
            if (count > 0) {
                assertNotNull(session);
                assertNotNull(clazz);
                List<?> l = session.createQuery("SELECT obj FROM " + clazz.getEntityName() + " obj ORDER BY obj DESC").setMaxResults(1).list();
                assertNotNull(l);
                assertNotNull(l.get(0));
                Class<?> cl = Class.forName(clazz.getEntityName());
                assertTrue(cl.isInstance(l.get(0)));
            } else {
                System.out.println("Don't have records for class " + clazz.getEntityName());
            }
            session.clear();
        }
    }

    @Test
    public void testCMSFunctions() throws RemoteException, NamingException, CreateException {
        DBSynchronizer synchronizer = new DBSynchronizer();
        synchronizer.srcSession.beginTransaction();

        synchronizer.srcSession.doWork(new Work() {

            @Override
            public void execute(Connection connection) throws SQLException {
                CallableStatement cstmt = connection
                        .prepareCall("begin ?:=" + SCHEMA_NAME + ".IZD_API.SetChipTagValue(?,?,?); end; ");

                cstmt.registerOutParameter(1, Types.NUMERIC);
                cstmt.setString(2, "4775733283186428");
                cstmt.setString(3, "BF5B");
                cstmt.setString(4, "DF01020000");

                cstmt.execute();
//				int res = cstmt.getInt(1); // No longer true. If needed create new test with different card
//				assertEquals(0, res);
            }
        });

        synchronizer.srcSession.doWork(new Work() {

            @Override
            public void execute(Connection connection) throws SQLException {
                CallableStatement stmt = connection
                        .prepareCall("begin ?:=" + SCHEMA_NAME + ".IZD_API_CC.Client_Card_Api(?); end; ");
                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.registerOutParameter(2, Types.VARCHAR);
                stmt.setString(2, "<Changes_request><document><version/><DOC_NAME>card</DOC_NAME><OPERATION>update</OPERATION><EXTERNAL_ID>1</EXTERNAL_ID>"
                        + "<details><card>4652282001580916</card><BANK_C>23</BANK_C><GROUPC>50</GROUPC>"
                        + "<M_NAME>test2</M_NAME></details></document></Changes_request>");
                stmt.execute();
//				int responseCode = stmt.getInt(1);
//				assertEquals(0, responseCode);
                stmt.close();
            }
        });


        synchronizer.srcSession.doWork(new Work() {

            @Override
            public void execute(Connection connection) throws SQLException {
                CallableStatement cstmt = connection.prepareCall("begin ?:=" + SCHEMA_NAME + ".IZD_API.attach_agreement(?,?,?,?,?); end; ");

                cstmt.registerOutParameter(1, Types.NUMERIC);
                cstmt.setString(2, "29636400");
                cstmt.setLong(3, 664794L);
                cstmt.setLong(4, 367919L);
                cstmt.setString(5, "23");
                cstmt.setString(6, "50");

                cstmt.execute();
                int res = cstmt.getInt(1);
                assertEquals(12, res);
                cstmt.close();

            }
        });

        synchronizer.srcSession.doWork(new Work() {

            @Override
            public void execute(Connection connection) throws SQLException {
                CallableStatement cstmt = connection
                        .prepareCall("{call " + SCHEMA_NAME + ".IZD_API.getlasterror(?,?,?,?,?) }");
                cstmt.setInt(1, 1000);
                cstmt.setInt(2, 1000);
                cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
                cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
                cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);

                cstmt.execute();
                assertEquals("CMS-API01267", cstmt.getString(3));
                cstmt.close();
            }
        });
        synchronizer.srcSession.getTransaction().commit();
    }
*/


}

