package lv.bank.cards.dbsynchronizer.config;

import com.zaxxer.hikari.HikariDataSource;
import lv.bank.cards.core.utils.LinkAppProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        basePackages = "lv.bank.cards.dbsynchronizer.jpa.pcd",
        entityManagerFactoryRef = "pcdEntityManagerFactory",
        transactionManagerRef = "pcdTransactionManager"
)
@Configuration
@EnableTransactionManagement
public class PcdConfiguration {

    public static final String PACKAGES_TO_SCAN = "lv.bank.cards.core.entity.linkApp";

    @Value("${spring.datasource.pcd.dialect}")
    protected String dialect;

    @Primary
    @Bean(name = "pcdEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean pcdEntityManagerFactory(
            @Qualifier("dataSourcePcdHikari") HikariDataSource hikariDataSourcePcd,
            @Qualifier("pcdJpaVendorAdapter") JpaVendorAdapter pcdJpaVendorAdapter) {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(hikariDataSourcePcd);
        emf.setJpaVendorAdapter(pcdJpaVendorAdapter);
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

    @Primary
    @Bean(name = "pcdEntityManager")
    public EntityManager cmsEntityManager(@Qualifier("pcdEntityManagerFactory") LocalContainerEntityManagerFactoryBean pcdEntityManagerFactory) {
        return pcdEntityManagerFactory.getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "pcdJpaVendorAdapter")
    public JpaVendorAdapter pcdJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.ORACLE);
        adapter.setShowSql(false);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform(dialect);
        return adapter;
    }

    @Primary
    @Bean(name = "dataSourcePropertiesPcd")
    @ConfigurationProperties(prefix = "spring.datasource.pcd")
    public DataSourceProperties dataSourcePropertiesPcd() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSourcePcdHikari")
    public HikariDataSource hikariDataSourcePcd(@Qualifier("dataSourcePropertiesPcd") DataSourceProperties dataSourcePropertiesPcd) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        DataSourceProperties props = dataSourcePropertiesPcd;
        props.setPassword(LinkAppProperties.decodePassword(props.getPassword()));
        HikariDataSource hikariDataSource = props.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        hikariDataSource.setPoolName("PCD");
        return hikariDataSource;
    }

    //Transactions for "JPA" ("lv.bank.cards.dbsynchronizer.jpa.pcd") !!!
    @Primary
    @Bean(name = "pcdTransactionManager")
    public JpaTransactionManager pcdTransactionManager(@Qualifier("pcdEntityManagerFactory") LocalContainerEntityManagerFactoryBean pcdEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(pcdEntityManagerFactory.getObject());
        return transactionManager;
    }

    @Primary
    @Bean(name = "pcdSessionFactory")
    public LocalSessionFactoryBean pcdSessionFactory(@Qualifier("dataSourcePcdHikari") HikariDataSource dataSourcePcdHikari) {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSourcePcdHikari);
        sessionFactory.setPackagesToScan(PACKAGES_TO_SCAN);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Primary
    //Transactions for "DAO" ("lv.bank.cards.dbsynchronizer.dao.LinkAppDao") !!!
    @Bean(name = "pcdHibernateTransactionManager")
    public PlatformTransactionManager hibernateTransactionManager(@Qualifier("pcdSessionFactory") LocalSessionFactoryBean pcdSessionFactory) {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(pcdSessionFactory.getObject());
        return transactionManager;
    }


}
