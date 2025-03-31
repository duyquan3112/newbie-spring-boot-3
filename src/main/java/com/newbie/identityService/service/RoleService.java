package com.newbie.identityService.service;

import com.newbie.identityService.dto.request.RoleRequest;
import com.newbie.identityService.dto.response.RoleResponse;
import com.newbie.identityService.entity.Permission;
import com.newbie.identityService.entity.Role;
import com.newbie.identityService.exception.AppException;
import com.newbie.identityService.enums.ErrorCode;
import com.newbie.identityService.mapper.RoleMapper;
import com.newbie.identityService.repository.PermissionRepository;
import com.newbie.identityService.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        Role role = roleMapper.toRole(request);

        // Fetch and sort permissions in one operation, then store in LinkedHashSet to preserve order
        Set<Permission> sortedPermissions = permissionRepository.findAllById(request.getPermissionsName())
                .stream()
                .sorted(Comparator.comparing(Permission::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        role.setPermissions(sortedPermissions);

        Role savedRole = roleRepository.save(role);
        return roleMapper.toRoleResponse(savedRole);
    }

    public List<RoleResponse> getAll() {
        // Use Spring Data JPA's built-in sorting capability
        List<Role> roles = roleRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        return roles.stream().map(role -> {
            // Sort permissions and set them back using a single operation
            role.setPermissions(role.getPermissions().stream()
                    .sorted(Comparator.comparing(Permission::getName))
                    .collect(Collectors.toCollection(LinkedHashSet::new)));

            return roleMapper.toRoleResponse(role);
        }).toList();
    }

    public void delete(String roleName) {
        roleRepository.deleteById(roleName);
    }
}
