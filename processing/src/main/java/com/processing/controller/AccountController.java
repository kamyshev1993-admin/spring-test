package com.processing.controller;

import com.processing.model.dto.AddMoneyDto;
import com.processing.model.dto.ExchangeDto;
import com.processing.model.dto.NewAccountDto;
import com.processing.model.entity.AccountEntity;
import com.processing.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/processing")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<AccountEntity> createNewAccount(@Valid @RequestBody NewAccountDto newAccountDto) {
        return ResponseEntity.ok(accountService.createNew(newAccountDto));
    }

    @PutMapping("/account")
    public ResponseEntity<AccountEntity> addMoney(@Valid @RequestBody AddMoneyDto data) {
        return ResponseEntity.ok(accountService.addMoneyToAccount(data.getAccountId(), data.getMoney()));
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountEntity> getAccount(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @GetMapping("/accounts/{user_id}")
    public ResponseEntity<List<AccountEntity>> getAllByClientId(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(accountService.getAllByUserId(userId));
    }

    @PostMapping("/exchange")
    public void exchange(@Valid @RequestBody ExchangeDto data) {
        accountService.exchange(data.getSourceId(), data.getTargetId(), data.getMoney());
    }
}
