package com.newbie.identityService.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SecurityConfig {

    String[] PUBLIC_ENDPOINTS = {
            "/users/create-user",
            "/auth/token",
            "/auth/introspect",
            "/auth/logout"
    };

    private CustomJwtDecoder customJwtDecoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(requestMatcherRegistry ->
                requestMatcherRegistry
                        //phan quyen tren endpoint
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
//                        .requestMatchers(HttpMethod.GET, "/users/get-users")
////                            .hasAnyAuthority(Role.ADMIN.name())
//                        .hasRole(Role.ADMIN.name())
                        .anyRequest()
                        .authenticated());

        httpSecurity.oauth2ResourceServer(oAuth2Config ->
                oAuth2Config.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(customJwtConverter()))
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    // custom jwt converter
    @Bean
    JwtAuthenticationConverter customJwtConverter() {
        JwtGrantedAuthoritiesConverter jwtConverter = new JwtGrantedAuthoritiesConverter();
        JwtAuthenticationConverter customConverter = new JwtAuthenticationConverter();
        jwtConverter.setAuthorityPrefix("");// bo di prefix mac dinh la SCOPE, co the doi thanh prefix khac neu muon
        customConverter.setJwtGrantedAuthoritiesConverter(jwtConverter);
        return customConverter;
    }

    // xoa prefix ROLE_ cua func hasRole
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
