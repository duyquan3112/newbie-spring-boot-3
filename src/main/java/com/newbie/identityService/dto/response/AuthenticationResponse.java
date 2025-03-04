package com.newbie.identityService.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    @JsonProperty("isAuthenticated") //de giu nguyen tien to "is"
    boolean isAuthenticated;
}
