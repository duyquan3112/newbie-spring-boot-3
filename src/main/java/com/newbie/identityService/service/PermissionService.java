package com.newbie.identityService.service;

import com.newbie.identityService.dto.request.PermissionRequest;
import com.newbie.identityService.dto.response.PermissionResponse;
import com.newbie.identityService.entity.Permission;
import com.newbie.identityService.exception.AppException;
import com.newbie.identityService.exception.ErrorCode;
import com.newbie.identityService.mapper.PermissionMapper;
import com.newbie.identityService.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permission permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String name) {
        permissionRepository.deleteById(name);
    }
}
