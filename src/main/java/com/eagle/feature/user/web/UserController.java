package com.eagle.feature.user.web;

import com.eagle.feature.user.service.UserService;
import com.eagle.feature.user.web.model.User;
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
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable UUID userId, @RequestBody User user) {
        userService.updateUser(userId, user);
    }
}
