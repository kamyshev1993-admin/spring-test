package com.test.currency.client;

import com.test.currency.config.CurrencyClientConfig;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CbrClient implements CurrencyClient {

    private final CurrencyClientConfig currencyClientConfig;

    public CbrClient(CurrencyClientConfig currencyClientConfig) {
        this.currencyClientConfig = currencyClientConfig;
    }

    @Override
    public String getCurrencyData(LocalDate date) {
        var client = HttpClient.newHttpClient();
        var url = buildUrl(date);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(500),
                    "Can not get info about currency from the client\n" +
                            "URL is : " + url);
        }
    }

    private String formatDate(LocalDate date) {
        var pattern = "dd/MM/yyyy";
        return DateTimeFormatter.ofPattern(pattern)
                .format(date);
    }

    private String buildUrl(LocalDate date) {
        return UriComponentsBuilder.fromHttpUrl(currencyClientConfig.getUrl())
                .queryParam("date_req", this.formatDate(date))
                .build().toUriString();
    }
}
