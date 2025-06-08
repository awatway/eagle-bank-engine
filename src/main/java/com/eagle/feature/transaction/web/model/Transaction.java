package com.eagle.feature.transaction.web.model;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class Transaction {
    private UUID transactionId;
    private UUID accountId;
    private Double amount;
    private String type;
    private ZonedDateTime timestamp;
}
