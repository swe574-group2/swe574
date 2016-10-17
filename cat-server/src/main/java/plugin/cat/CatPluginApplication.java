package plugin.cat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CatPluginApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatPluginApplication.class, args);
    }
}
