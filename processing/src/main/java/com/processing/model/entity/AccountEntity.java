package com.processing.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity(name = "account")
public class AccountEntity {

    @SequenceGenerator(name = "acc_seq", sequenceName = "account_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_seq")
    @Id
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    private BigDecimal balance;
}
