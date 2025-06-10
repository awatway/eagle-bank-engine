package com.eagle.feature.user.web.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class UserResponse {
    private UUID userId;
    private String name;
    private String email;
    private String phone;
    private ZonedDateTime createdTimestamp;
    private ZonedDateTime updatedTimestamp;
}
