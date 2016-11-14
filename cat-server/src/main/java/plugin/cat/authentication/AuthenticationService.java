package plugin.cat.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Created by tolgacaner on 05/11/16.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableWebSecurity
public class AuthenticationService {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "authentication-service");
        SpringApplication.run(AuthenticationService.class, args);
    }
}
