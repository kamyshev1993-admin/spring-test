package com.test.currency;

import com.test.currency.config.CurrencyClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CurrencyClientConfig.class)
public class CurrencyApplication {
	public static void main(String[] args) {
		SpringApplication.run(CurrencyApplication.class, args);
	}
}
