package com.eagle.feature.user.web;

import com.eagle.feature.user.service.UserService;
import com.eagle.feature.user.web.model.CreateUserRequest;
import com.eagle.feature.user.web.model.UpdateUserRequest;
import com.eagle.feature.user.web.model.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "Users", description = "Apis related to users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public UserResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Fetch user by user id")
    public UserResponse getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user")
    public void updateUser(@PathVariable UUID userId, @RequestBody UpdateUserRequest updateUserRequest) {
        userService.updateUser(userId, updateUserRequest);
    }
}
