package ${basePackageName}.controller;

import ${basePackageName}.service.TestService${name}AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xiayx
 */
@Configuration
@ImportAutoConfiguration(classes = {
        TestService${name}AutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        Controller${name}AutoConfiguration.class,
})
@PropertySource("classpath:application-demo-controller-test.properties")
public class TestController${name}AutoConfiguration {
}
