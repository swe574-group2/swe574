package plugin.cat.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import plugin.cat.annotation.AnnotationService;

/**
 * Created by tolgacaner on 05/11/16.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AccountsService {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "accounts-service");
        SpringApplication.run(AccountsService.class, args);
    }
}
