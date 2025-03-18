package com.newbie.identityService.mapper;

import com.newbie.identityService.dto.request.PermissionRequest;
import com.newbie.identityService.dto.request.RoleRequest;
import com.newbie.identityService.dto.response.PermissionResponse;
import com.newbie.identityService.dto.response.RoleResponse;
import com.newbie.identityService.entity.Permission;
import com.newbie.identityService.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    // @Mapping(source = "fieldA", target = "fieldB", ignore = true)
    // su dung mapping de map 2 field khac ten nhau; dung ignore de khong map field target

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
