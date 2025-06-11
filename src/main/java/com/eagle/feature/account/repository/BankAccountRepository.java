package com.eagle.feature.account.repository;

import com.eagle.feature.account.repository.domain.BankAccount;
import com.eagle.feature.account.web.model.CreateBankAccountRequest;
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
    public UUID createAccount(UUID userId, BankAccount bankAccount) {
        String sql = "INSERT INTO bank_account (account_id, name, account_type, account_number, sort_code, balance, currency, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, UUID.randomUUID());
            ps.setString(2, bankAccount.getName());
            ps.setString(3, bankAccount.getAccountType().name());
            ps.setString(4, bankAccount.getAccountNumber());
            ps.setString(5, bankAccount.getSortCode());
            ps.setObject(6, bankAccount.getBalance());
            ps.setString(7, bankAccount.getCurrency());
            ps.setObject(8, userId);
            return ps;
        }, keyHolder);
        return (UUID) keyHolder.getKeyList().getFirst().get("account_id");
    }

    @Transactional
    public BankAccount getAccount(UUID accountId) {
        String sql = "SELECT * FROM bank_account WHERE account_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(BankAccount.class), accountId);
    }

    @Transactional
    public void updateAccount(UUID accountId, BankAccount account) {
        String sql = "UPDATE bank_account SET name = ?, account_type = ?, balance = ?, " +
                "updated_timestamp = CURRENT_TIMESTAMP WHERE account_id = ?";
        jdbcTemplate.update(sql,
                account.getName(),
                account.getAccountType().name(),
                account.getBalance(),
                accountId
        );
    }

    @Transactional
    public void deleteAccount(UUID accountId) {
        jdbcTemplate.update("DELETE FROM bank_account WHERE account_id = ?", accountId);
    }
}
