package com.newbie.identityService.controller;

import com.newbie.identityService.dto.request.RoleRequest;
import com.newbie.identityService.dto.response.ApiResponse;
import com.newbie.identityService.dto.response.RoleResponse;
import com.newbie.identityService.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping("/create")
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping("/get-all")
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/delete/{roleName}")
    ApiResponse<String> delete(@PathVariable("roleName") String roleName) {
        roleService.delete(roleName);
        return ApiResponse.<String>builder()
                .result("Delete success!")
                .build();
    }
}
