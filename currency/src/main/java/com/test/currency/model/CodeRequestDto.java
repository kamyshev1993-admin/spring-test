package com.test.currency.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CodeRequestDto {
    @NotNull
    @Pattern(regexp = "^\\d{2}\\.\\d{2}\\.\\d{4}$", message = "date should look like 12.12.1999")
    private String date;
    @NotNull
    @Size(min = 3, max = 3, message = "code size should be 3")
    private String code;
}
