package com.newbie.identityService.service;

import com.newbie.identityService.dto.request.UserCreationRequest;
import com.newbie.identityService.dto.request.UserUpdateRequest;
import com.newbie.identityService.dto.response.UserResponse;
import com.newbie.identityService.entity.Role;
import com.newbie.identityService.entity.User;
import com.newbie.identityService.exception.AppException;
import com.newbie.identityService.exception.ErrorCode;
import com.newbie.identityService.mapper.UserMapper;
import com.newbie.identityService.repository.RoleRepository;
import com.newbie.identityService.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;

// dung RequiredArgsConstructor de thay the cho @AutoWired
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // use lombok
//        UserCreationRequest userCreationRequest = UserCreationRequest.builder()
//                .username("")
//                .firstName("")
//                .password("")
//                .build();

//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());

        // use Mapper for create user
        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRoles()));
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserResponse> getUsers() {

        return userMapper.toListUserResponse(userRepository.findAll());

        // cach khac
//         return userRepository.findAll().stream()
//                 .map(userMapper::toUserResponse).toList();
    }

    public UserResponse getMyInfo() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        // dung username
//        String username = securityContext.getAuthentication().getName();
//
//        return userMapper.toUserResponse(userRepository.findByUsername(username)
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));

        // dung userId
        Authentication authentication = securityContext.getAuthentication();

        Jwt jwt = (Jwt) authentication.getPrincipal();

        String userId = jwt.getClaim("userId");

        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));

    }

    // chi lay thong tin cua user dang login
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    public UserResponse updateUser(UserUpdateRequest request, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        if (!request.getRoles().isEmpty()) {
            HashSet<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRoles()));
            user.setRoles(roles);
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasAuthority('DELETE')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
