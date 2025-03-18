package com.newbie.identityService.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.SortedSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
  String name;
  String description;

  Set<PermissionResponse> permissions;
}
