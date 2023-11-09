package com.processing.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddMoneyDto {
    @NotNull
    @JsonAlias("account_id")
    private Long accountId;

    @NotNull
    @Positive
    private BigDecimal money;
}
