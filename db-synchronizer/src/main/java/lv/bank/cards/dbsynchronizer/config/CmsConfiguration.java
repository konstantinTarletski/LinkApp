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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

@EnableJpaRepositories(
        basePackages = "lv.bank.cards.dbsynchronizer.jpa.cms",
        entityManagerFactoryRef = "cmsEntityManagerFactory",
        transactionManagerRef = "cmsTransactionManager"
)
@Configuration
@EnableTransactionManagement
public class CmsConfiguration {

    public static final String PACKAGES_TO_SCAN = "lv.bank.cards.core.entity.cms";

    @Value("${spring.datasource.cms.dialect}")
    protected String dialect;

    @Bean(name = "cmsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean cmsEntityManagerFactory() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(hikariDataSourceCms());
        emf.setJpaVendorAdapter(cmsJpaVendorAdapter());
        emf.setPackagesToScan(PACKAGES_TO_SCAN);
        emf.setJpaProperties(hibernateProperties());
        emf.afterPropertiesSet();
        return emf;
    }

    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("spring.jpa.hibernate.ddl-auto", "validate");
        return properties;
    }

    @Bean(name = "cmsEntityManager")
    public EntityManager cmsEntityManager(@Qualifier("cmsEntityManagerFactory") LocalContainerEntityManagerFactoryBean cmsEntityManagerFactory) {
        return cmsEntityManagerFactory.getObject().createEntityManager();
    }

    @Bean(name = "cmsJpaVendorAdapter")
    public JpaVendorAdapter cmsJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.ORACLE);
        adapter.setShowSql(false);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform(dialect);
        return adapter;
    }

    @Bean(name = "dataSourcePropertiesCms")
    @ConfigurationProperties(prefix = "spring.datasource.cms")
    public DataSourceProperties dataSourcePropertiesCms() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSourceCmsHikari")
    public HikariDataSource hikariDataSourceCms() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        DataSourceProperties props = dataSourcePropertiesCms();
        props.setPassword(LinkAppProperties.decodePassword(props.getPassword()));
        HikariDataSource hikariDataSource = props.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        hikariDataSource.setPoolName("CMS");
        return hikariDataSource;
    }

    //Transactions for "JPA" ("lv.bank.cards.dbsynchronizer.jpa.cms") !!!
    @Bean(name = "cmsTransactionManager")
    public PlatformTransactionManager cmsTransactionManager(@Qualifier("cmsEntityManagerFactory") LocalContainerEntityManagerFactoryBean cmsEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(cmsEntityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean(name = "cmsSessionFactory")
    public LocalSessionFactoryBean cmsSessionFactory(@Qualifier("dataSourceCmsHikari") HikariDataSource dataSourceCmsHikari) {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceCmsHikari);
        sessionFactory.setPackagesToScan(PACKAGES_TO_SCAN);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    //Transactions for "DAO" ("lv.bank.cards.dbsynchronizer.dao.CmsDao") !!!
    @Bean(name = "cmsHibernateTransactionManager")
    public PlatformTransactionManager hibernateTransactionManager(@Qualifier("cmsSessionFactory") LocalSessionFactoryBean cmsSessionFactory) {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(cmsSessionFactory.getObject());
        return transactionManager;
    }

}
