package com.hilbert.core.user.service;

import com.hilbert.core.user.dto.CreateUserDto;
import com.hilbert.core.user.dto.UpdateUserDto;
import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.dto.UserSmallDto;

import java.util.List;

public interface UserService {

    UserDataDto getByUsername(String username);
    List<UserSmallDto> getByIds(List<Long> ids);
    UserDataDto createUser(CreateUserDto userDto);
    UserDataDto updateUser(UpdateUserDto userDto);
    void deleteUser(Long id);
}
