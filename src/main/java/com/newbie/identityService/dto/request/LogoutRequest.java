package com.newbie.identityService.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogoutRequest {
    String accessToken;
}
