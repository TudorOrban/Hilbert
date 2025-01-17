package com.hilbert.core.user.dto;

import com.hilbert.core.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

//    @Mapping(source = "user.username", target = "username")
//    @Mapping(source = "user.email", target = "email")
    UserDataDto userToUserDataDto(User user);

//    @Mapping(source = "userDto.username", target = "username")
//    @Mapping(source = "userDto.email", target = "email")
    User createUserDtoToUser(CreateUserDto userDto);
}
