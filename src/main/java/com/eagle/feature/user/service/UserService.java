package com.eagle.feature.user.service;

import com.eagle.feature.user.repository.UserRepository;
import com.eagle.feature.user.repository.domain.User;
import com.eagle.feature.user.web.model.CreateUserRequest;
import com.eagle.feature.user.web.model.Identity;
import com.eagle.feature.user.web.model.UserResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final IdentityService identityService;

    public UserService(UserRepository userRepository, IdentityService identityService) {
        this.userRepository = userRepository;
        this.identityService = identityService;
    }

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        //TODO: add validations
        User user = user(createUserRequest);
        UUID userId = userRepository.createUser(user);
        identityService.createIdentity(userId, identity(createUserRequest));
        return userResponse(userId, user);
    }

    public UserResponse getUser(UUID userId) {
        User user = userRepository.getUser(userId);
        return userResponse(userId, user);
    }

    public void updateUser(UUID userId, CreateUserRequest createUserRequest) {
        //TODO: add validations
        userRepository.updateUser(userId, user(createUserRequest));
    }

    private User user(CreateUserRequest createUserRequest) {
        return User.builder()
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .phone(createUserRequest.getPhone())
                .build();
    }

    private static Identity identity(CreateUserRequest createUserRequest) {
        return Identity.builder()
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .build();
    }

    private static UserResponse userResponse(UUID userId, User user) {
        return UserResponse.builder()
                .userId(userId)
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .createdTimestamp(user.getCreatedTimestamp())
                .updatedTimestamp(user.getUpdatedTimestamp())
                .build();
    }

}
