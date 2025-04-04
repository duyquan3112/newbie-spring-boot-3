package com.newbie.identityService.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1, "User existed!", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(2, "User not existed!", HttpStatus.NOT_FOUND),
    INVALID_KEY(-2, "Invalid message key.", HttpStatus.BAD_REQUEST),
    UNKNOWN_EXCEPTION(-1, "Unknown exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_INVALID(3, "Username must be at least {min}-characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(4, "Password must be at least {min}-characters", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(5, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(6, "Unauthorized!", HttpStatus.FORBIDDEN),
    PERMISSION_EXISTED(7, "Permission existed!", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(8, "Role existed!", HttpStatus.BAD_REQUEST),
    INVALID_DOB(9, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code; // custom code
    private final String message;
    private final HttpStatusCode statusCode;
}
