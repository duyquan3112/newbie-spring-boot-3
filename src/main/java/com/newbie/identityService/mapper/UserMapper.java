package com.newbie.identityService.mapper;

import com.newbie.identityService.dto.request.UserCreationRequest;
import com.newbie.identityService.dto.request.UserUpdateRequest;
import com.newbie.identityService.dto.response.UserResponse;
import com.newbie.identityService.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);

    // @Mapping(source = "fieldA", target = "fieldB", ignore = true)
    // su dung mapping de map 2 field khac ten nhau; dung ignore de khong map field target
    UserResponse toUserResponse(User user);

    List<UserResponse> toListUserResponse(List<User> users);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
