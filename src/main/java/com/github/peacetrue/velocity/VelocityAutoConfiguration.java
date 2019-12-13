package com.github.peacetrue.velocity;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author xiayx
 */
@EnableConfigurationProperties(VelocityProperties.class)
public class VelocityAutoConfiguration {

    private VelocityProperties properties;

    public VelocityAutoConfiguration(VelocityProperties properties) {
        this.properties = properties;
    }

    @Bean
    public VelocityEngine velocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.DEFAULT_RUNTIME_LOG_NAME, "model.generator");
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath,file");
        velocityEngine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        velocityEngine.setProperty("resource.loader.file.class", FileResourceLoader.class.getName());
        velocityEngine.init();
        return velocityEngine;
    }
}
