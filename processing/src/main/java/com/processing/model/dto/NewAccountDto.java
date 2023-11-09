package com.processing.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class NewAccountDto {

    @NotNull
    @JsonAlias(value = "currency_code")
    @Max(3)
    @Min(3)
    private String currencyCode;

    @NotNull
    @Positive
    @JsonAlias(value = {"person_id", "client_id"})
    private Long personId;
}
