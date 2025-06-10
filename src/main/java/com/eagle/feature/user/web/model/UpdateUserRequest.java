package com.eagle.feature.user.web.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UpdateUserRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
}
