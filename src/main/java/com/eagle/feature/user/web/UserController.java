package com.eagle.feature.user.web;

import com.eagle.feature.user.service.UserService;
import com.eagle.feature.user.web.model.CreateUserRequest;
import com.eagle.feature.user.web.model.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable UUID userId, @RequestBody CreateUserRequest createUserRequest) {
        userService.updateUser(userId, createUserRequest);
    }
}
