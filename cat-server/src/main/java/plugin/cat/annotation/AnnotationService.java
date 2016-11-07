package plugin.cat.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebSecurity
public class AnnotationService {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "annotation-service");
        SpringApplication.run(AnnotationService.class, args);
    }
}