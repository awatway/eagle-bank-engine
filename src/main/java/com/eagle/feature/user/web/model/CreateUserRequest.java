package com.eagle.feature.user.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema
public class CreateUserRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
}
