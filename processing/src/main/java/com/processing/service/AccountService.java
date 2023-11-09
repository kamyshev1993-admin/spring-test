package com.processing.service;

import com.processing.model.dto.NewAccountDto;
import com.processing.model.entity.AccountEntity;
import com.processing.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {

    public final AccountRepository accountRepository;
    public final CurrencyService currencyService;
    private final static String CURRENCY_RUB = "RUB";

    public AccountEntity getById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with id " + id + " was not found"));
    }

    public List<AccountEntity> getAllByUserId(Long userId) {
        return accountRepository.getByUserId(userId);
    }

    @Transactional
    public AccountEntity createNew(NewAccountDto newAccountDto) {
        var accountEntity = new AccountEntity();
        accountEntity.setCurrencyCode(newAccountDto.getCurrencyCode());
        accountEntity.setUserId(newAccountDto.getPersonId());
        accountEntity.setBalance(BigDecimal.ZERO);
        return accountRepository.save(accountEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public AccountEntity addMoneyToAccount(Long accountId, BigDecimal money) {
        var accountEntity = accountRepository.findById(accountId);
        return accountEntity.map(acc -> this.addMoneyToAccount(acc, money))
                .orElseThrow(() -> new EntityNotFoundException("Could not find account with id " + accountId));
    }

    // todo read more about Transactional
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void exchange(Long from, Long to, BigDecimal amount) {
        var source = this.getById(from);
        var target = this.getById(to);
        if (source.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("You have not enough money" +
                    "\n Balance is " + source.getBalance() + " " + source.getCurrencyCode());
        }
        BigDecimal targetAmount;
        if (Objects.equals(CURRENCY_RUB, source.getCurrencyCode()) && Objects.equals(CURRENCY_RUB, target.getCurrencyCode())) {
            targetAmount = amount;
        } else if (Objects.equals(CURRENCY_RUB, source.getCurrencyCode()) && !Objects.equals(CURRENCY_RUB, target.getCurrencyCode())) {
            var rate = currencyService.getRate(target.getCurrencyCode());
            targetAmount = amount.divide(rate, 4, RoundingMode.HALF_DOWN);
        } else if (!Objects.equals(CURRENCY_RUB, source.getCurrencyCode()) && Objects.equals(CURRENCY_RUB, target.getCurrencyCode())) {
            var rate = currencyService.getRate(source.getCurrencyCode());
            targetAmount = amount.multiply(rate);
        } else if (!Objects.equals(CURRENCY_RUB, source.getCurrencyCode()) && !Objects.equals(CURRENCY_RUB, target.getCurrencyCode())) {
            var courseRate = currencyService.getRate(source.getCurrencyCode());
            var targetRate = currencyService.getRate(target.getCurrencyCode());
            targetAmount = amount.multiply(courseRate).divide(targetRate, 4, RoundingMode.HALF_DOWN);
        } else {
            throw new IllegalArgumentException("Incorrect currency code");
        }
        addMoneyToAccount(source, amount.negate());
        addMoneyToAccount(target, targetAmount);
    }

    private AccountEntity addMoneyToAccount(AccountEntity account, BigDecimal money) {
        var new_balance = account.getBalance().add(money);
        account.setBalance(new_balance);
        return accountRepository.save(account);
    }
}
