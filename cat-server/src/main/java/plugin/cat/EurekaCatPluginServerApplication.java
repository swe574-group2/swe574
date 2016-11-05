package plugin.cat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by tolgacaner on 05/11/16.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaCatPluginServerApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "eureka-server");
        SpringApplication.run(EurekaCatPluginServerApplication.class, args);
    }
}
