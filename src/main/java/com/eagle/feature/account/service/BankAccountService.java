package com.eagle.feature.account.service;

import com.eagle.feature.account.repository.BankAccountRepository;
import com.eagle.feature.account.web.model.BankAccount;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount createAccount(UUID userId, BankAccount account) {
        //TODO: validations
        return bankAccountRepository.createAccount(userId, account);
    }

    public BankAccount getAccount(UUID accountId) {
        return bankAccountRepository.getAccount(accountId);
    }

    public void updateAccount(UUID accountId, BankAccount account) {
        //TODO: validations
        bankAccountRepository.updateAccount(accountId, account);
    }

    public void deleteAccount(UUID accountId) {
        //TODO: validations -- is balance zero?
        bankAccountRepository.deleteAccount(accountId);
    }
}
