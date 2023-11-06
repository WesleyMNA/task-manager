package com.manager.taskapi.domain.auth;


import com.manager.taskapi.domain.auth.dtos.AuthResponse;
import com.manager.taskapi.domain.auth.dtos.LoginRequest;
import com.manager.taskapi.domain.auth.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthResponse response = service.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(HttpServletRequest request) {
        AuthResponse response = service.refresh(request);
        return ResponseEntity.ok(response);
    }
}
