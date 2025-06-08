package com.eagle.feature.user.service;

import com.eagle.feature.user.repository.UserRepository;
import com.eagle.feature.user.web.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        //TODO: add validations
        return userRepository.createUser(user);
    }

    public User getUser(UUID userId) {
        return userRepository.getUser(userId);
    }

    public void updateUser(UUID userId, User user) {
        //TODO: add validations
        userRepository.updateUser(userId, user);
    }
}
