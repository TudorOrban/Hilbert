package com.hilbert.core.user.service;

import com.hilbert.core.user.dto.*;
import com.hilbert.shared.search.models.PaginatedResults;
import com.hilbert.shared.search.models.SearchParams;
import com.hilbert.shared.search.models.UserSearchParams;

import java.util.List;

public interface UserService {

    UserDataDto getByUsername(String username);
    List<UserSmallDto> getByIds(List<Long> ids);
    PaginatedResults<UserSearchDto> searchUsers(UserSearchParams searchParams);
    UserDataDto createUser(CreateUserDto userDto);
    UserDataDto updateUser(UpdateUserDto userDto);
    void deleteUser(Long id);
}
