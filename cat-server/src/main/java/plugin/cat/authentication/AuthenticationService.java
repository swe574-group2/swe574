package plugin.cat.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by tolgacaner on 05/11/16.
 */
@SpringBootApplication
@EnableEurekaClient
public class AuthenticationService {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "authentication-service");
        SpringApplication.run(AuthenticationService.class, args);
    }
}
