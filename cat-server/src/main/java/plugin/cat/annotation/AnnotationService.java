package plugin.cat.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AnnotationService {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "annotation-service");
        SpringApplication.run(AnnotationService.class, args);
    }
}
