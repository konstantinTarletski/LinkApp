
package lv.bank.cards.dbsynchronizer;

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

import javax.ejb.CreateException;
import javax.naming.NamingException;
import java.rmi.RemoteException;

import static lv.bank.cards.dbsynchronizer.Profile.COUNTRY_LT_TEST_PROFILE;

@SpringBootTest
@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@AutoConfigureMockMvc
@ComponentScan({"lv.nordlb.cards.pcdabaNG.dbsync"})
@PropertySource(value = "${app.config.location.test-lt}")
@ActiveProfiles({COUNTRY_LT_TEST_PROFILE})
public class DBSynchronizerTestLT extends DBSynchronizerTestBase {


    @Test
    public void initDBSynchronizer() throws RemoteException, NamingException, CreateException {
        // To run test in Eclipse we need to set environment variable for JBoss location
        // JBoss must have specified settings for LinkApp and CMS DB
        // When run test by ANT this is done by adding environment variable from buid.properties file
        //assertNotNull("Must specify JBoss path", System.getenv("jboss_location"));
        //System.setProperty("DBSynTest", "true");

        //DBSynchronizer synchronizer = new DBSynchronizer();
        //assertNotNull(synchronizer);
        //assertNotNull(synchronizer.cmsSessionFactory.getCurrentSession());
        //dBSynchronizer.updateDB();
    }

	/*
	@Test
	public void checkUpdateQueries() throws RemoteException, NamingException, CreateException{
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
		
		assertNotNull("Must specify JBoss path", System.getenv("jboss_location"));
		System.setProperty("DBSynTest", "true");
		
		System.out.println("jboss : " + System.getenv("JBOSS_LOCATION"));
		
		DBSynchronizer synchronizer = new DBSynchronizer();
		
		
		PcdAccParam param = new PcdAccParam();
		param.setAccountNo(new BigDecimal(41241241));
		param.setPcdAccount(new PcdAccount());
		param.getPcdAccount().setAccountNo(new BigDecimal(41241241));
		
		synchronizer.pcdSessionFactory.getCurrentSession().save(param);
		
		Calendar past = Calendar.getInstance();
		past.set(2000, 01, 01);
		for(Class<? extends Serializable> clazz : map.keySet()){
			System.out.println("check " + clazz.getName());
			List<?> l = synchronizer.cmsSessionFactory.getCurrentSession()
				.getNamedQuery("get.updated.items."+clazz.getName()+".to."+map.get(clazz).getName())
				.setTimestamp("lastWaterMark", past.getTime())
				.setTimestamp("currentWaterMark", new Date())
				.setMaxResults(1)
				.list();
			assertEquals(1, l.size());
		}		
		System.out.println("check " + DnbIzdAccntChng.class.getName());
		List<?> l = synchronizer.cmsSessionFactory.getCurrentSession()
				.getNamedQuery("get.updated.items." + DnbIzdAccntChng.class.getName() + ".to." + PcdProcIds.class.getName())
				.setMaxResults(1).list();
		assertNotNull(l); // This table is empty in test DB
	}
	
	@Test
	public void pcdEntities() throws IOException, ClassNotFoundException{
		System.setProperty("DBSynTest", "true");
		JBossConfiguration pcdConfig = new JBossConfiguration(System.getenv("JBOSS_LOCATION") + "standalone/configuration/standalone.xml","PCD");
		Session session = null;
		Configuration destination_cfg = new Configuration()
				.addAnnotatedClass(PcdDbOwner.class)
				.addAnnotatedClass(PcdClient.class)
				.addAnnotatedClass(PcdAccount.class)
				.addAnnotatedClass(PcdAccParam.class)
				.addAnnotatedClass(PcdAgreement.class)
				.addAnnotatedClass(PcdBranch.class)
				.addAnnotatedClass(PcdCard.class)
				.addAnnotatedClass(PcdCardGroup.class)
				.addAnnotatedClass(PcdCardsAccount.class)
				.addAnnotatedClass(PcdCompany.class)
				.addAnnotatedClass(PcdLastUpdated.class)
				.addAnnotatedClass(PcdBin.class)
				.addAnnotatedClass(PcdRepLang.class)
				.addAnnotatedClass(PcdRepDistribut.class)
				.addAnnotatedClass(PcdCardType.class)
				.addAnnotatedClass(PcdProcIds.class)
				.addAnnotatedClass(PcdCurrency.class)
				.addAnnotatedClass(PcdSlip.class)
				.addAnnotatedClass(PcdStopCause.class)
				.addAnnotatedClass(PcdCardDesigns.class)
				.addAnnotatedClass(PcdBinCardDesigns.class)
				.addAnnotatedClass(PcdCondCard.class)
				.addAnnotatedClass(PcdCondAccnt.class)
				.addAnnotatedClass(PcdServicesFee.class)
				.addAnnotatedClass(PcdPpCard.class)
				.addAnnotatedClass(PcdCcyConv.class)
				.addAnnotatedClass(PcdLog.class)
				.addAnnotatedClass(PcdMerchant.class)
				.addAnnotatedClass(PcdAccumulator.class)
				.addAnnotatedClass(PcdAccumulatorType.class)
				.addAnnotatedClass(PcdRiskLevel.class);
		Properties props = new Properties();
		InputStream is = DBSynchronizer.class.getResourceAsStream("/DBSynchronizer_destination.properties");
		if (is == null) {
			System.out.println("a" + System.getProperty("DBSynTest"));
			if("true".equals(System.getProperty("DBSynTest"))){
				is = DBSynchronizer.class.getResourceAsStream("/resources/DBSynchronizer_destination.properties");
			}
		}
		props.load(is);
		props.putAll(pcdConfig.getConfig());
		destination_cfg.setProperties(props);
		ServiceRegistry dstServiceRegistry = new ServiceRegistryBuilder().applySettings(destination_cfg.getProperties()).buildServiceRegistry();
		session = destination_cfg.buildSessionFactory(dstServiceRegistry).openSession();

		try{
			session.beginTransaction();
			PcdAccParam pa = new PcdAccParam();
			pa.setStatus("1");
			pa.setPcdCurrency(new PcdCurrency());
			pa.getPcdCurrency().setIsoAlpha("EUR");
			pa.setPcdAccount(new PcdAccount());
			pa.getPcdAccount().setAccountNo(new BigDecimal(12314124));
			pa.setAccountNo(new BigDecimal(12314124));
			session.save(pa);
			session.flush();
			session.clear();
			session.getTransaction().commit();
		} catch (ConstraintViolationException e){
			if(e.getMessage().contains("ORA-02291")){
				System.out.println(e.getMessage());
			} else 
				throw e;
		}

		Iterator<PersistentClass> it = destination_cfg.getClassMappings();
		while(it.hasNext()){
			PersistentClass clazz = it.next();
			System.out.println("Check class " + clazz.getEntityName());
			Long count = (Long) session.createQuery("SELECT count(obj) FROM " + clazz.getEntityName() + " obj ").list().get(0);
			System.out.println(clazz.getEntityName() + " - " + count);
			if(count > 0){
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
	public void izdEntities() throws IOException, ClassNotFoundException{
		System.setProperty("DBSynTest", "true");
		JBossConfiguration pcdConfig = new JBossConfiguration(System.getenv("JBOSS_LOCATION") + "standalone/configuration/standalone.xml","CMS");
		Session session = null;
		Configuration destination_cfg = new Configuration()
				.addAnnotatedClass(IzdCardType.class)
				.addAnnotatedClass(IzdCondCard.class)
				.addAnnotatedClass(IzdCondAccnt.class)
				.addAnnotatedClass(IzdValidProductCcy.class)
				.addAnnotatedClass(IzdAccParam.class)
				.addAnnotatedClass(NordlbFileId.class)
				.addAnnotatedClass(NordlbBranch.class)
				.addAnnotatedClass(IzdBranch.class)
				.addAnnotatedClass(IzdClAcct.class)
				.addAnnotatedClass(IzdAgreement.class)
				.addAnnotatedClass(IzdCardGroupCcy.class)
				.addAnnotatedClass(IzdCcyTable.class)
				.addAnnotatedClass(IzdCompany.class)
				.addAnnotatedClass(IzdAccount.class)
				.addAnnotatedClass(IzdRepDistribut.class)
				.addAnnotatedClass(IzdRepLang.class)
				.addAnnotatedClass(IzdBinTable.class)
				.addAnnotatedClass(IzdClient.class)
				.addAnnotatedClass(IzdCardGroup.class)
				.addAnnotatedClass(IzdOfferedProduct.class)
				.addAnnotatedClass(IzdCategory.class)
				.addAnnotatedClass(IzdCard.class)
				.addAnnotatedClass(IzdDbOwner.class)
				.addAnnotatedClass(IzdStoplist.class)
				.addAnnotatedClass(DnbIzdAccntChng.class)
				.addAnnotatedClass(IzdAccntAc.class)
				//.addAnnotatedClass(IzdSlip.class)
				.addAnnotatedClass(IzdStopCause.class)
				.addAnnotatedClass(IzdCardDesigns.class)
				.addAnnotatedClass(IzdCardsPinBlocks.class)
				.addAnnotatedClass(IzdCardsAddFields.class)
				.addAnnotatedClass(IzdBinCardDesigns.class)
				.addAnnotatedClass(IzdLtl2eurAccountsMap.class)
				.addAnnotatedClass(IzdChipCmdHist.class);
		Properties props = new Properties();
		InputStream is = DBSynchronizer.class.getResourceAsStream("/DBSynchronizer_source.properties");
		if (is == null) {
			if("true".equals(System.getProperty("DBSynTest"))){
				is = DBSynchronizer.class.getResourceAsStream("/resources/DBSynchronizer_source.properties");
			}
		}
		props.load(is);
		props.putAll(pcdConfig.getConfig());
		destination_cfg.setProperties(props);
		ServiceRegistry dstServiceRegistry = new ServiceRegistryBuilder().applySettings(destination_cfg.getProperties()).buildServiceRegistry();
		session = destination_cfg.buildSessionFactory(dstServiceRegistry).openSession();
		Iterator<PersistentClass> it = destination_cfg.getClassMappings();
		while(it.hasNext()){
			PersistentClass clazz = it.next();
			System.out.println("Check class " + clazz.getEntityName());
			Long count = (Long) session.createQuery("SELECT count(obj) FROM " + clazz.getEntityName() + " obj ").list().get(0);
			System.out.println(clazz.getEntityName() + " - " + count);
			if(count > 0){
				String orderColumn = clazz.getEntityName().endsWith("NordlbFileId") || 
						clazz.getEntityName().endsWith("MerchantsView") || 
						clazz.getEntityName().endsWith("MerchantPar") ||
						clazz.getEntityName().endsWith("NordlbBranch") ||
						clazz.getEntityName().endsWith("IzdLtl2eurAccountsMap") ? "" : ".ctime";
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
	public void rtpsEntities() throws IOException, ClassNotFoundException{
		JBossConfiguration pcdConfig = new JBossConfiguration(System.getenv("jboss_location_lt") + "standalone/configuration/standalone.xml","RTPS");
		Session session = null;
		Configuration destination_cfg = new Configuration()
		.addAnnotatedClass(AnswerCode.class)
		.addAnnotatedClass(CardsException.class)
		.addAnnotatedClass(CurrencyCode.class)
		.addAnnotatedClass(ProcessingEntity.class)
//		.addAnnotatedClass(Regdir.class) 
		.addAnnotatedClass(StipAccount.class)
		.addAnnotatedClass(StipCard.class)
		.addAnnotatedClass(StipClient.class)
		.addAnnotatedClass(StipEventsN.class)
		.addAnnotatedClass(StipLocks.class)
		.addAnnotatedClass(StipLocksMatch.class)
		.addAnnotatedClass(StipRestracnt.class)
		.addAnnotatedClass(StipStoplist.class)
		.addAnnotatedClass(StipRmsStoplist.class)
		.addAnnotatedClass(StipParamList.class)
		.addAnnotatedClass(StipParamN.class);
		Properties props = new Properties();
		InputStream is = DBSynchronizer.class.getResourceAsStream("/DBSynchronizer_rtps.properties");
		if (is == null) {
			if("true".equals(System.getProperty("DBSynTest"))){
				is = DBSynchronizer.class.getResourceAsStream("/resources/DBSynchronizer_rtps.properties");
			}
		}
		props.load(is);
		props.putAll(pcdConfig.getConfig());
		destination_cfg.setProperties(props);
		ServiceRegistry dstServiceRegistry = new ServiceRegistryBuilder().applySettings(destination_cfg.getProperties()).buildServiceRegistry();
		session = destination_cfg.buildSessionFactory(dstServiceRegistry).openSession();
		Iterator<PersistentClass> it = destination_cfg.getClassMappings();
		while(it.hasNext()){
			PersistentClass clazz = it.next();
			System.out.println("Check class " + clazz.getEntityName());
			Long count = (Long) session.createQuery("SELECT count(obj) FROM " + clazz.getEntityName() + " obj ").list().get(0);
			System.out.println(clazz.getEntityName() + " - " + count);
			if(count > 0){
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
	public void testWsdlLocation(){
		String wsdlLoc = JBossConfiguration.getBankCardsWsdlLink(System.getenv("jboss_location") + "standalone/configuration/standalone.xml");
		assertNotNull(wsdlLoc);
	}
	
	@Test
	public void objectWasChanged(){
		Date date = new Date();
		IzdCard izdCard = new IzdCard();
		PcdCard pcdCard = new PcdCard();
		
		// Both null
		assertFalse(DBSynchronizer.objectWasChanged(izdCard.getExpiry2(), pcdCard.getExpiry2()));
		assertFalse(DBSynchronizer.objectWasChanged(null, (String)null));
		
		// Both the same 
		izdCard.setExpiry2(date);
		pcdCard.setExpiry2(date);
		assertFalse(DBSynchronizer.objectWasChanged(izdCard.getExpiry2(), pcdCard.getExpiry2()));
		pcdCard.setExpiry2(new java.sql.Date(date.getTime()));
		assertFalse(DBSynchronizer.objectWasChanged(izdCard.getExpiry2(), pcdCard.getExpiry2()));
		
		// One null 
		izdCard.setExpiry2(null);
		assertTrue(DBSynchronizer.objectWasChanged(izdCard.getExpiry2(), pcdCard.getExpiry2()));
		izdCard.setExpiry2(date);
		pcdCard.setExpiry2(null);
		assertTrue(DBSynchronizer.objectWasChanged(izdCard.getExpiry2(), pcdCard.getExpiry2()));
		
		// Only milliseconds changed, same date
		pcdCard.setExpiry2(new Date(date.getTime() + 900));
		assertFalse(DBSynchronizer.objectWasChanged(izdCard.getExpiry2(), pcdCard.getExpiry2()));
		
		// Different day
		pcdCard.setExpiry2(new Date(date.getTime() + (24 * 60 * 60 * 1000)));
		assertTrue(DBSynchronizer.objectWasChanged(izdCard.getExpiry2(), pcdCard.getExpiry2()));
		
	}
	*/
}

