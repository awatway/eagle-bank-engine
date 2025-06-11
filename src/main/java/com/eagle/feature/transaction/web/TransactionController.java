package com.eagle.feature.transaction.web;

import com.eagle.feature.transaction.web.model.Transaction;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/transactions")
@SecurityRequirement(name = "bearerAuth")
class TransactionController {

    @GetMapping("/account/{accountId}")
    public List<Transaction> getTransactions(@PathVariable Long accountId) {
        return Collections.emptyList();
    }
}
