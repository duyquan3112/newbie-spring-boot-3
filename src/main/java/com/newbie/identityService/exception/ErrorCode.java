package com.newbie.identityService.exception;

public enum ErrorCode {
    USER_EXISTED(401, "User existed!"),
    USER_NOT_EXISTED(401, "User not existed!"),
    INVALID_KEY(-2, "Invalid message key."),
    UNKNOWN_EXCEPTION(-1, "unknown exception"),
    USERNAME_INVALID(401, "Username must be at least 3-characters"),
    PASSWORD_INVALID(401, "Password must be at least 8-characters")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
