package com.hilbert.core.user.service;

import com.hilbert.core.user.dto.LoginDto;
import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.jwt.JwtAuthenticationResponse;
import com.hilbert.core.user.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthServiceImpl(
        UserService userService,
        AuthenticationManager authenticationManager,
        JwtTokenProvider tokenProvider
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;;
        this.tokenProvider = tokenProvider;
    }

    public JwtAuthenticationResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }

    public String getUsernameFromJWTToken(String jwtToken) {
        return tokenProvider.getUsernameFromJWT(jwtToken);
    }

    public UserDataDto getUserFromJWTToken(String jwtToken) {
        String username = tokenProvider.getUsernameFromJWT(jwtToken);

        return userService.getByUsername(username, false);
    }
}
