package com.newbie.identityService.dto.request;

public enum SuccessCode {
    CREATE_SUCCESS(200),
    GET_SUCCESS(201),
    UPDATE_SUCCESS(203),
    DELETE_SUCCESS(204)
    ;

    private final int code;

    SuccessCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
