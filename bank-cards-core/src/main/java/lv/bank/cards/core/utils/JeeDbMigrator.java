package lv.bank.cards.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class JeeDbMigrator {

    @Resource(lookup = "java:jboss/datasources/PCD-DS")
    protected DataSource dataSource;

    @PostConstruct
    protected void onStartup() throws SQLException {
        String schema = dataSource.getConnection().getSchema();
        log.info("Staring Flyway For Schema = {}",  schema);

        Flyway flyway = Flyway.configure().dataSource(dataSource)
                .baselineOnMigrate(true)
                .schemas(schema)
                .baselineVersion("0.0")
                .table("PCD_SCHEMA_HISTORY")
                .load();

        Location[] location = flyway.getConfiguration().getLocations();

        log.info("Flyway locations = {}", location);
        flyway.migrate();
    }

}
