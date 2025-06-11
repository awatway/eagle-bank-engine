package com.eagle.feature.account.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class UpdateBankAccountRequest {
    @NotBlank(message = "Name is required.")
    private String name;
    private AccountType accountType;
}
