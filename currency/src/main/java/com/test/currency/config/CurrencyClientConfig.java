package com.test.currency.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "currency.client")
@Data
public class CurrencyClientConfig {
    private String url;
}
