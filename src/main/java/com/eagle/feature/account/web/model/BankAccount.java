package com.eagle.feature.account.web.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class BankAccount {
    private UUID accountId;
    private UUID userId;
    private String accountNumber;
    private BigDecimal balance;
}
