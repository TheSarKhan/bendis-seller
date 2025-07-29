package com.sarkhan.backend.bendisseller.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryProperties {
    private Cloud cloud;
    private Api api;

    @Data
    public static class Cloud {
        private String name;
    }

    @Data
    public static class Api {
        private String key;
        private String secret;
    }
}
