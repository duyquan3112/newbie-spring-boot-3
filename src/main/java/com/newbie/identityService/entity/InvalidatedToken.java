package com.newbie.identityService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class InvalidatedToken {
    @Id
    String id;
    Date expiryTime;
}
