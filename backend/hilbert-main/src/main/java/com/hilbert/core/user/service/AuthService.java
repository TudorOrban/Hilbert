package com.hilbert.core.user.service;

import com.hilbert.core.user.dto.LoginDto;
import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.jwt.JwtAuthenticationResponse;

public interface AuthService {

    JwtAuthenticationResponse login(LoginDto loginDto);
    String getUsernameFromJWTToken(String jwtToken);
    UserDataDto getUserFromJWTToken(String jwtToken);
}
