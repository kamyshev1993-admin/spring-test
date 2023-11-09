package com.test.currency.client;

import java.time.LocalDate;

public interface CurrencyClient {

    String getCurrencyData(LocalDate date);
}
