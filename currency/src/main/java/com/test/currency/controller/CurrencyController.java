package com.test.currency.controller;

import com.test.currency.model.CodeRequestDto;
import com.test.currency.service.CurrencyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/now/{code}")
    public ResponseEntity<BigDecimal> getNow(@PathVariable("code") String code) {
        return ResponseEntity.ok(currencyService.getCurrencyValueForNow(code));
    }

    @PostMapping
    public ResponseEntity<BigDecimal> getByDateAndCode(@Valid @RequestBody CodeRequestDto requestDto) {
        return ResponseEntity.ok(currencyService.getCurrencyValue(requestDto.getDate(), requestDto.getCode()));
    }
}
