package com.newbie.identityService.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE) //default neu khong dinh nghia access-modifier
public class PermissionRequest {
   String name;
   String description;
}
