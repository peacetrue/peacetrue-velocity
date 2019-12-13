package ${basePackageName}.controller;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(Controller${name}Properties.class)
@PropertySource("classpath:/application-demo-controller.properties")
public class Controller${name}AutoConfiguration {

    @Bean
    public ${name}Controller demoController() {
        return new ${name}Controller();
    }
}
