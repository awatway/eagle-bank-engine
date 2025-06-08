package com.eagle.feature.account.web;

import com.eagle.feature.account.service.BankAccountService;
import com.eagle.feature.account.web.model.BankAccount;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/user/{userId}")
    public BankAccount createAccount(@PathVariable UUID userId, @RequestBody BankAccount account) {
        return bankAccountService.createAccount(userId, account);
    }

    @GetMapping("/{accountId}")
    public BankAccount getAccount(@PathVariable UUID accountId) {
        return bankAccountService.getAccount(accountId);
    }

    @PutMapping("/{accountId}")
    public void updateAccount(@PathVariable UUID accountId, @RequestBody BankAccount account) {
        bankAccountService.updateAccount(accountId, account);
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable UUID accountId) {
        bankAccountService.deleteAccount(accountId);
    }
}
