package com.newbie.identityService.controller;

import com.newbie.identityService.dto.request.PermissionRequest;
import com.newbie.identityService.dto.response.ApiResponse;
import com.newbie.identityService.dto.response.PermissionResponse;
import com.newbie.identityService.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("/create")
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping("/get-all")
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/delete/{permissionName}")
    ApiResponse<String> delete(@PathVariable("permissionName") String permissionName) {
        permissionService.delete(permissionName);
        return ApiResponse.<String>builder()
                .result("Delete success!")
                .build();
    }
}
