package com.eagle.feature.user.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Schema
public class UpdateUserRequest {
    private String name;
    private String email;
    private String phone;
}
