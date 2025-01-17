package com.hilbert.core.user.controller;

import com.hilbert.core.user.dto.LoginDto;
import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.jwt.JwtAuthenticationResponse;

import com.hilbert.core.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(
        AuthService authService
    ) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        JwtAuthenticationResponse response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-username-from-token")
    public ResponseEntity<String> getUsernameFromToken(@RequestBody String jwtToken) {
        String username = authService.getUsernameFromJWTToken(jwtToken);
        return ResponseEntity.ok(username);
    }

    @PostMapping("/get-user-from-token")
    public ResponseEntity<UserDataDto> getUserFromToken(@RequestBody String jwtToken) {
        UserDataDto userDataDto = authService.getUserFromJWTToken(jwtToken);
        return ResponseEntity.ok(userDataDto);
    }
}