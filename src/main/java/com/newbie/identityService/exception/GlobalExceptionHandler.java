package com.newbie.identityService.exception;

import com.newbie.identityService.dto.response.ApiResponse;
import com.newbie.identityService.enums.ErrorCode;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE_KEY = "min";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handlingRuntimeException(Exception exception) {
        return ResponseEntity.status(ErrorCode.UNKNOWN_EXCEPTION.getStatusCode())
                .body(ApiResponse.builder()
                        .code(ErrorCode.UNKNOWN_EXCEPTION.getCode())
                        .message(ErrorCode.UNKNOWN_EXCEPTION.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        Map<String, Objects> attributes = null;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
            var constraintViolation = !exception.getBindingResult().getAllErrors().isEmpty() ? exception.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class) : null;
            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            log.info(attributes.toString());

        } catch (IllegalArgumentException ignored) {

        }

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(Objects.nonNull(attributes)
                                ? mapAttribute(errorCode.getMessage(), attributes)
                                : errorCode.getMessage())
                        .build());
    }

    private String mapAttribute(String message, Map<String, Objects> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE_KEY));

        return message.replace("{" + MIN_ATTRIBUTE_KEY + "}", minValue);
    }
}
