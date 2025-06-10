package com.eagle.feature.user.web.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateUserRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
}
