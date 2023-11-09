package com.processing.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeDto {
    @NotNull
    @JsonAlias(value = "source_id")
    private Long sourceId;

    @NotNull
    @JsonAlias(value = "target_id")
    private Long targetId;

    @NotNull
    @Positive
    private BigDecimal money;
}
