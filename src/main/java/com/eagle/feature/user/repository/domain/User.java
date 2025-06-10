package com.eagle.feature.user.repository.domain;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class User {
    private UUID userId;
    private String name;
    private String email;
    private String phone;
    private ZonedDateTime createdTimestamp;
    private ZonedDateTime updatedTimestamp;
}
