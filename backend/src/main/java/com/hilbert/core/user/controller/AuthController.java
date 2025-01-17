package com.hilbert.core.user.controller;

import com.hilbert.core.user.dto.LoginDto;
import com.hilbert.core.user.jwt.JwtAuthenticationResponse;
import com.hilbert.core.user.jwt.JwtTokenProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        String jwt = tokenProvider.generateToken(authentication);
        logger.info(jwt);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<String> validateJWTToken(@RequestBody String jwtToken) {
        logger.info(jwtToken);
        boolean isValid = tokenProvider.validateToken(jwtToken);
        if (isValid) {
            return ResponseEntity.ok().body("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
    }

    @PostMapping("/get-username-from-token")
    public ResponseEntity<String> getUsernameFromToken(@RequestBody String jwtToken) {
        logger.info(jwtToken);
        String username = tokenProvider.getUsernameFromJWT(jwtToken);
        if (username != null) {
            return ResponseEntity.ok().body(username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        }
    }
}