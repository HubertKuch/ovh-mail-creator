package com.hubert.ovhmailcreator.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ovh")
public class OvhConfiguration {
    private String endpoint;
    private String consumerKey;
    private String appKey;
    private String appSecret;
}
