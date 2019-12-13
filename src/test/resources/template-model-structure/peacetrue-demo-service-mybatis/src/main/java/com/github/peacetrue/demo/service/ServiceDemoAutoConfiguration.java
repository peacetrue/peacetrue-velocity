package ${basePackageName}.service;

import com.github.peacetrue.associate.AssociatedSourceBuilder;
import com.github.peacetrue.associate.AssociatedSourceBuilderImpl;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Objects;

/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(Service${name}Properties.class)
@MapperScan(basePackageClasses = Service${name}AutoConfiguration.class, annotationClass = Mapper.class)
@PropertySource("classpath:/application-demo-service.properties")
public class Service${name}AutoConfiguration {

    private Service${name}Properties properties;

    public Service${name}AutoConfiguration(Service${name}Properties properties) {
        this.properties = Objects.requireNonNull(properties);
    }

    @Bean
    public AssociatedSourceBuilder associatedSourceBuilder() {
        return new AssociatedSourceBuilderImpl();
    }

    @Bean
    public ${name}Service demoService() {
        return new ${name}ServiceImpl();
    }

}
