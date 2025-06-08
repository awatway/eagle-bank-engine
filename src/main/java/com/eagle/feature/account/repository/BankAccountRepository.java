package com.eagle.feature.account.repository;

import com.eagle.feature.account.web.model.BankAccount;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;

@Repository
public class BankAccountRepository {
    private final JdbcTemplate jdbcTemplate;

    public BankAccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public BankAccount createAccount(UUID userId, BankAccount account) {
        String accountNumber = UUID.randomUUID().toString();
        String sql = "INSERT INTO bank_account(account_number, balance, user_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, accountNumber);
            ps.setObject(2, BigDecimal.ZERO);
            ps.setObject(3, userId);
            return ps;
        }, keyHolder);
        account.setAccountId(UUID.fromString(keyHolder.getKey().toString()));
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.ZERO);
        account.setUserId(userId);
        return account;
    }

    @Transactional
    public BankAccount getAccount(UUID accountId) {
        String sql = "SELECT * FROM bank_account WHERE account_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(BankAccount.class), accountId);
    }

    @Transactional
    public void updateAccount(UUID accountId, BankAccount account) {
        String sql = "UPDATE bank_account SET balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sql, account.getBalance(), accountId);
    }

    @Transactional
    public void deleteAccount(UUID accountId) {
        jdbcTemplate.update("DELETE FROM bank_account WHERE account_id = ?", accountId);
    }
}
