package com.eagle.feature.account.web;

import com.eagle.feature.account.service.BankAccountService;
import com.eagle.feature.account.web.model.BankAccountResponse;
import com.eagle.feature.account.web.model.CreateBankAccountRequest;
import com.eagle.feature.account.web.model.UpdateBankAccountRequest;
import com.eagle.feature.auth.JwtProvider;
import com.eagle.feature.common.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
@Tag(name = "Bank Accounts", description = "Apis related to accounts")
@SecurityRequirement(name = "bearerAuth")
public class BankAccountController extends BaseController {
    private final BankAccountService bankAccountService;

    public BankAccountController(JwtProvider jwtProvider,
                                 BankAccountService bankAccountService) {
        super(jwtProvider);
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/user/{userId}")
    @Operation(summary = "Create a new bank account for the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Operation not allowed"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    public ResponseEntity<BankAccountResponse> createAccount(@PathVariable UUID userId,
                                                             @Valid @RequestBody CreateBankAccountRequest account,
                                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws AccessDeniedException {
        validateUserId(userId, authHeader);
        BankAccountResponse response = bankAccountService.createAccount(userId, account);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
