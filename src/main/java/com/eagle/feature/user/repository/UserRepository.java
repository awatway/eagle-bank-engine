package com.eagle.feature.user.repository;

import com.eagle.feature.user.web.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public User createUser(User user) {
        String sql = "INSERT INTO users(name, email, phone) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            return ps;
        }, keyHolder);
        user.setUserId((UUID) keyHolder.getKeyList().getFirst().get("user_id"));
        return user;
    }

    @Transactional
    public User getUser(UUID userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userId);
    }

    @Transactional
    public void updateUser(UUID userId, User user) {
        String sql = "UPDATE users SET name = ?, email = ?, phone = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPhone(), userId);
    }
}
