package com.eagle.feature.user.web;

import com.eagle.feature.auth.JwtProvider;
import com.eagle.feature.common.web.BaseController;
import com.eagle.feature.user.service.UserService;
import com.eagle.feature.user.web.model.CreateUserRequest;
import com.eagle.feature.user.web.model.UpdateUserRequest;
import com.eagle.feature.user.web.model.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "Users", description = "Apis related to users")
public class UserController extends BaseController {
    private final UserService userService;

    public UserController(JwtProvider jwtProvider,
                          UserService userService) {
        super(jwtProvider);
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Fetch user by user id", security = @SecurityRequirement(name = "bearerAuth"))
    public UserResponse getUser(@PathVariable UUID userId,
                                @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws AccessDeniedException {
        validateUserId(userId, authHeader);
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user", security = @SecurityRequirement(name = "bearerAuth"))
    public void updateUser(@PathVariable UUID userId,
                           @Valid @RequestBody UpdateUserRequest updateUserRequest,
                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws AccessDeniedException {
        validateUserId(userId, authHeader);
        userService.updateUser(userId, updateUserRequest);
    }
}
