package com.newbie.identityService.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE) //default neu khong dinh nghia access-modifier
public class RoleRequest {
   String name;
   String description;
   Set<String> permissionsName;
}
