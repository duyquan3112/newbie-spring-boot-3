package com.newbie.identityService.controller;

import com.newbie.identityService.dto.response.ApiResponse;
import com.newbie.identityService.dto.request.AuthenticationRequest;
import com.newbie.identityService.dto.request.IntrospectRequest;
import com.newbie.identityService.dto.response.AuthenticationResponse;
import com.newbie.identityService.dto.response.IntrospectResponse;
import com.newbie.identityService.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationResponse)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        IntrospectResponse introspectResponse = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(introspectResponse)
                .build();
    }
}
