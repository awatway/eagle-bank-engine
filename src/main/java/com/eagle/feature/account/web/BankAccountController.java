package com.eagle.feature.account.web;

import com.eagle.feature.account.service.BankAccountService;
import com.eagle.feature.account.web.model.BankAccountResponse;
import com.eagle.feature.account.web.model.CreateBankAccountRequest;
import com.eagle.feature.account.web.model.UpdateBankAccountRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
@Tag(name = "Bank Accounts", description = "Apis related to accounts")
@SecurityRequirement(name = "bearerAuth")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/user/{userId}")
    @Operation(summary = "Create a new bank account for the user")
    public BankAccountResponse createAccount(@PathVariable UUID userId,
                                             @Valid @RequestBody CreateBankAccountRequest account) {
        return bankAccountService.createAccount(userId, account);
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Fetch bank account by account id")
    public BankAccountResponse getAccount(@PathVariable UUID accountId) {
        return bankAccountService.getAccount(accountId);
    }

    @PatchMapping("/{accountId}")
    @Operation(summary = "Update bank account")
    public void updateAccount(@PathVariable UUID accountId,
                              @Valid @RequestBody UpdateBankAccountRequest account) {
        bankAccountService.updateAccount(accountId, account);
    }

    @DeleteMapping("/{accountId}")
    @Operation(summary = "Delete bank account")
    public void deleteAccount(@PathVariable UUID accountId) {
        bankAccountService.deleteAccount(accountId);
    }
}
