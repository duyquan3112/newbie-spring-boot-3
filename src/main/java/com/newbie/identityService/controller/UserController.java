package com.newbie.identityService.controller;

import com.newbie.identityService.dto.response.ApiResponse;
import com.newbie.identityService.dto.request.SuccessCode;
import com.newbie.identityService.dto.request.UserCreationRequest;
import com.newbie.identityService.dto.request.UserUpdateRequest;
import com.newbie.identityService.dto.response.UserResponse;
import com.newbie.identityService.entity.User;
import com.newbie.identityService.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/create-user")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(SuccessCode.CREATE_SUCCESS.getCode())
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/get-users")
    ApiResponse<List<UserResponse>> getUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // log authentication info
        log.info("Username requesting: {}", authentication.getName());
        authentication.getAuthorities()
                .forEach((e) -> log.info(e.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .code(SuccessCode.GET_SUCCESS.getCode())
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/get-user/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .code(SuccessCode.GET_SUCCESS.getCode())
                .result(userService.getUserById(userId))
                .build();
    }

    @GetMapping("/get-my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .code(SuccessCode.GET_SUCCESS.getCode())
                .build();
    }

    @PatchMapping("/update-user/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") String userId,
                                         @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(SuccessCode.UPDATE_SUCCESS.getCode())
                .result(userService.updateUser(request, userId))
                .build();
    }

    @DeleteMapping("/delete-user/{userId}")
    ApiResponse<String> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .code(SuccessCode.DELETE_SUCCESS.getCode())
                .result("User has been deleted!")
                .build();
    }
}
