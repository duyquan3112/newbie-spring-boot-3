package com.newbie.identityService.service;

import com.newbie.identityService.dto.request.AuthenticationRequest;
import com.newbie.identityService.entity.User;
import com.newbie.identityService.exception.AppException;
import com.newbie.identityService.exception.ErrorCode;
import com.newbie.identityService.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AuthenticationService {
    UserRepository userRepository;

    public boolean authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}
