package com.github.peacetrue.velocity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiayx
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.velocity")
public class VelocityProperties {
    private String endpoint;
    private String accessKeyId;
    private String secretAccessKey;
}
