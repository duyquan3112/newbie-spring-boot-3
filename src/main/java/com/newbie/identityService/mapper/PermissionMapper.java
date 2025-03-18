package com.newbie.identityService.mapper;

import com.newbie.identityService.dto.request.PermissionRequest;
import com.newbie.identityService.dto.request.UserCreationRequest;
import com.newbie.identityService.dto.request.UserUpdateRequest;
import com.newbie.identityService.dto.response.PermissionResponse;
import com.newbie.identityService.dto.response.UserResponse;
import com.newbie.identityService.entity.Permission;
import com.newbie.identityService.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    // @Mapping(source = "fieldA", target = "fieldB", ignore = true)
    // su dung mapping de map 2 field khac ten nhau; dung ignore de khong map field target
    PermissionResponse toPermissionResponse(Permission permission);
}
