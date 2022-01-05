package lv.bank.cards.dbsynchronizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@PropertySource(value = "${app.config.location.server}", ignoreResourceNotFound = true)
@PropertySource(value = "${app.config.location.develop}", ignoreResourceNotFound = true)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
