package com.hilbert.core.user.service;

import com.hilbert.core.user.dto.CreateUserDto;
import com.hilbert.core.user.dto.UserDataDto;

public interface UserService {

    UserDataDto getByUsername(String username);
    UserDataDto createUser(CreateUserDto userDto);
}
