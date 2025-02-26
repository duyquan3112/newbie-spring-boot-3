package com.newbie.identityService.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) //giup loai bo nhung field null, khong can thiet
public class ApiResponse<T> {
    int code = 200;
    String message;
    T result;
}
