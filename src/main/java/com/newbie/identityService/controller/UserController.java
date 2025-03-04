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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/create-user")
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));
        apiResponse.setCode(SuccessCode.CREATE_SUCCESS.getCode());

        return apiResponse;
    }

    @GetMapping("/get-users")
    ApiResponse<List<UserResponse>> getUsers() {
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.getUsers());
        apiResponse.setCode(SuccessCode.GET_SUCCESS.getCode());

        return apiResponse;
    }

    @GetMapping("/get-user/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.getUserById(userId));
        apiResponse.setCode(SuccessCode.GET_SUCCESS.getCode());

        return apiResponse;
    }

    @PutMapping("/update-user/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.updateUser(request, userId));
        apiResponse.setCode(SuccessCode.UPDATE_SUCCESS.getCode());

        return apiResponse;
    }

    @DeleteMapping("/delete-user/{userId}")
    ApiResponse<String> deleteUser(@PathVariable("userId") String userId) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        userService.deleteUser(userId);

        apiResponse.setCode(SuccessCode.DELETE_SUCCESS.getCode());
        apiResponse.setMessage("User has been deleted!");

        return apiResponse;
    }
}
