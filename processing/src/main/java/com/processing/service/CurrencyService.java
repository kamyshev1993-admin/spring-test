package com.processing.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CurrencyService {

    private final HttpClient client;

    @Value("${service.currency.url}")
    private String url;

    public CurrencyService() {
        client = HttpClient.newHttpClient();
    }

    public BigDecimal getRate(String code) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url + "/" + code))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return new BigDecimal(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
