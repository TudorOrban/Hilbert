package com.hilbert.core.user.service;

import com.hilbert.core.user.dto.*;
import com.hilbert.shared.search.models.PaginatedResults;
import com.hilbert.shared.search.models.SearchParams;

import java.util.List;

public interface UserService {

    UserDataDto getByUsername(String username);
    List<UserSmallDto> getByIds(List<Long> ids);
    PaginatedResults<UserSearchDto> searchUsers(SearchParams searchParams);
    UserDataDto createUser(CreateUserDto userDto);
    UserDataDto updateUser(UpdateUserDto userDto);
    void deleteUser(Long id);
}
