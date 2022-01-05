package lv.bank.cards.dbsynchronizer.config;

import com.zaxxer.hikari.HikariDataSource;
import lv.bank.cards.core.utils.LinkAppProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "lv.bank.cards.dbsynchronizer.jpa.rtps",
        entityManagerFactoryRef = "rtpsEntityManagerFactory",
        transactionManagerRef = "rtpsTransactionManager"
)
@EnableTransactionManagement
public class RtpsConfiguration {

    public static final String PACKAGES_TO_SCAN = "lv.bank.cards.core.entity.rtps";

    @Value("${spring.datasource.rtps.dialect}")
    protected String dialect;

    @Bean(name = "rtpsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean rtpsEntityManagerFactory() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(hikariDataSourceRtps());
        emf.setJpaVendorAdapter(rtpsJpaVendorAdapter());
        emf.setPackagesToScan(PACKAGES_TO_SCAN);
        emf.setJpaProperties(hibernateProperties());
        emf.afterPropertiesSet();
        return emf;
    }

    public Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("spring.jpa.hibernate.ddl-auto", "validate");
        return properties;
    }

    @Bean(name = "rtpsEntityManager")
    public EntityManager cmsEntityManager(@Qualifier("rtpsEntityManagerFactory") LocalContainerEntityManagerFactoryBean rtpsEntityManagerFactory) {
        return rtpsEntityManagerFactory.getObject().createEntityManager();
    }

    @Bean(name = "rtpsJpaVendorAdapter")
    public JpaVendorAdapter rtpsJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.ORACLE);
        adapter.setShowSql(false);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform(dialect);
        return adapter;
    }

    @Bean(name = "dataSourcePropertiesRtps")
    @ConfigurationProperties(prefix = "spring.datasource.rtps")
    public DataSourceProperties dataSourcePropertiesRtps() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSourceRtpsHikari")
    public HikariDataSource hikariDataSourceRtps() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        DataSourceProperties props = dataSourcePropertiesRtps();
        props.setPassword(LinkAppProperties.decodePassword(props.getPassword()));
        HikariDataSource hikariDataSource = props.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        hikariDataSource.setPoolName("RTPS");
        return hikariDataSource;
    }

    //Transactions for "JPA" ("lv.bank.cards.dbsynchronizer.jpa.rtps") now not exist!!!
    @Bean(name = "rtpsTransactionManager")
    public PlatformTransactionManager rtpsTransactionManager(@Qualifier("rtpsEntityManagerFactory") LocalContainerEntityManagerFactoryBean rtpsEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(rtpsEntityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean(name = "rtpsSessionFactory")
    public LocalSessionFactoryBean rtpsSessionFactory(@Qualifier("dataSourceRtpsHikari") HikariDataSource dataSourceRtpsHikari) {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceRtpsHikari);
        sessionFactory.setPackagesToScan(PACKAGES_TO_SCAN);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    //Transactions for "DAO" ("lv.bank.cards.dbsynchronizer.dao.RtpsDao") !!!
    @Bean(name = "rtpsHibernateTransactionManager")
    public PlatformTransactionManager hibernateTransactionManager(@Qualifier("rtpsSessionFactory") LocalSessionFactoryBean rtpsSessionFactory) {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(rtpsSessionFactory.getObject());
        return transactionManager;
    }

}
