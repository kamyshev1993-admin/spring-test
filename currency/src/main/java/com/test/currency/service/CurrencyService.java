package com.test.currency.service;

import com.test.currency.client.CurrencyClient;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class CurrencyService {

    private final ConcurrentMap<LocalDate, Document> docCache;
    private final ConcurrentMap<Map<LocalDate, String>, BigDecimal> currencyValueCache;
    private final CurrencyClient currencyClient;

    public CurrencyService(CurrencyClient currencyClient) {
        docCache = new ConcurrentHashMap<>();
        currencyValueCache = new ConcurrentHashMap<>();
        this.currencyClient = currencyClient;
    }

    public BigDecimal getCurrencyValueForNow(String code) {
        return this.getCurrencyValue(LocalDate.now(), code);
    }

    public BigDecimal getCurrencyValue(String date, String code) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return this.getCurrencyValue(localDate, code);
    }

    public BigDecimal getCurrencyValue(LocalDate date, String code) {
        return currencyValueCache.computeIfAbsent(Collections.singletonMap(date, code), (map) -> {
            var document = docCache.computeIfAbsent(date, (doc) ->
                    parseXmlString(currencyClient.getCurrencyData(date)));
            return this.getDocumentValue(document, code);
        });
    }

    private BigDecimal getDocumentValue(Document document, String code) {
        var voluteElements = document.getDocumentElement().getElementsByTagName("Valute");
        for (int i = 0; i < voluteElements.getLength(); i++) {
            var element = (Element) voluteElements.item(i);
            if (element.getElementsByTagName("CharCode").item(0).getTextContent().equals(code)) {
                var result = element.getElementsByTagName("Value").item(0).getTextContent();
                return new BigDecimal(result.replace(",", "."));
            }
        }
        throw new IllegalArgumentException("Illegal code " + code);
    }

    private Document parseXmlString(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
