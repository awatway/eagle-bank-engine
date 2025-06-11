package com.eagle.feature.account.service;

import com.eagle.feature.account.repository.BankAccountRepository;
import com.eagle.feature.account.repository.domain.BankAccount;
import com.eagle.feature.account.web.model.AccountType;
import com.eagle.feature.account.web.model.BankAccountResponse;
import com.eagle.feature.account.web.model.CreateBankAccountRequest;
import com.eagle.feature.account.web.model.UpdateBankAccountRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static com.eagle.feature.account.service.BankAccountUtils.generateAccountNumber;
import static com.eagle.feature.account.service.BankAccountUtils.generateSortCode;

@Service
public class BankAccountService {
    //TODO: Add support for user to update currency
    public static final String CURRENCY = "GBP";
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccountResponse createAccount(UUID userId, CreateBankAccountRequest createBankAccountRequest) {
        BankAccount bankAccount = bankAccount(userId, createBankAccountRequest.getAccountType(), createBankAccountRequest.getName(),
                generateAccountNumber(), generateSortCode());
        UUID accountId = bankAccountRepository.createAccount(userId, bankAccount);
        return bankAccountResponse(userId, accountId, bankAccount);
    }

    public BankAccountResponse getAccount(UUID accountId) {
        BankAccount bankAccount = bankAccountRepository.getAccount(accountId);
        return bankAccountResponse(bankAccount.getUserId(), accountId, bankAccount);
    }

    public void updateAccount(UUID accountId, UpdateBankAccountRequest updateBankAccountRequest) {
        BankAccount bankAccount = bankAccountRepository.getAccount(accountId);
        if (bankAccount != null) {
            bankAccount.setName(updateBankAccountRequest.getName());
            bankAccount.setAccountType(updateBankAccountRequest.getAccountType());
            bankAccountRepository.updateAccount(accountId, bankAccount);
        }
    }

    public void deleteAccount(UUID accountId) {
        //TODO: validations -- is balance zero?
        bankAccountRepository.deleteAccount(accountId);
    }

    private BankAccount bankAccount(UUID userId, AccountType accountType, String name, String accountNumber, String sortcode) {
        return BankAccount.builder()
                .userId(userId)
                .accountNumber(accountNumber)
                .accountType(accountType)
                .sortCode(sortcode)
                .balance(BigDecimal.ZERO)
                .currency(CURRENCY)
                .name(name)
                .build();
    }

    private BankAccountResponse bankAccountResponse(UUID userId, UUID accountId, BankAccount bankAccount) {
        return BankAccountResponse.builder()
                .accountId(accountId)
                .userId(userId)
                .accountNumber(bankAccount.getAccountNumber())
                .sortCode(bankAccount.getSortCode())
                .accountType(bankAccount.getAccountType())
                .name(bankAccount.getName())
                .balance(bankAccount.getBalance())
                .currency(bankAccount.getCurrency())
                .build();
    }
}
