package lv.bank.cards.dbsynchronizer.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@Slf4j
public class FlywayConfiguration {

    @Qualifier("dataSourcePcdHikari")
    @Autowired
    private HikariDataSource hikariDataSourcePcd;

    @Value("${application.flyway.table.name}")
    protected String flywayTableName;

    @Value("${application.flyway.baseline.version}")
    protected String baselineVersion;

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() throws SQLException {

        String schema = hikariDataSourcePcd.getConnection().getSchema();
        log.info("Staring Flyway For Schema = {}, flywayTableName = {}, baselineVersion = {} ",  schema, flywayTableName, baselineVersion);

        return flyway1 -> Flyway.configure().dataSource(hikariDataSourcePcd)
                .baselineOnMigrate(true)
                .schemas(schema)
                .baselineVersion(baselineVersion)
                .table(flywayTableName)
                .load().migrate();
    }

}
