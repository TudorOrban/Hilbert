package com.hilbert.core.user.service;

import com.hilbert.core.user.dto.CreateUserDto;
import com.hilbert.core.user.dto.UpdateUserDto;
import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.dto.UserMapper;
import com.hilbert.core.user.model.User;
import com.hilbert.core.user.repository.UserRepository;
import com.hilbert.shared.error.types.ResourceAlreadyExistsException;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import com.hilbert.shared.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;

    @Autowired
    public UserServiceImpl(
        UserRepository userRepository,
        PasswordEncoderUtil passwordEncoderUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoderUtil = passwordEncoderUtil;
    }

    public UserDataDto getByUsername(String username) {
        User foundUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username, ResourceType.USER, ResourceIdentifierType.USERNAME));

        return this.mapUserToUserDataDto(foundUser);
    }

    public UserDataDto createUser(CreateUserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new ResourceAlreadyExistsException(userDto.getUsername(), ResourceType.USER, ResourceIdentifierType.USERNAME);
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResourceAlreadyExistsException(userDto.getUsername(), ResourceType.USER, ResourceIdentifierType.EMAIL);
        }

        User user = UserMapper.INSTANCE.createUserDtoToUser(userDto);
        user.setPasswordHash(passwordEncoderUtil.encode(userDto.getPassword()));

        User savedUser = this.userRepository.save(user);

        return this.mapUserToUserDataDto(savedUser);
    }

    public UserDataDto updateUser(UpdateUserDto userDto) {
        User existingUser = this.userRepository.findById(userDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(userDto.getId().toString(), ResourceType.USER, ResourceIdentifierType.ID));

        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());

        User updatedUser = this.userRepository.save(existingUser);

        return this.mapUserToUserDataDto(updatedUser);
    }

    private UserDataDto mapUserToUserDataDto(User user) {
        return UserMapper.INSTANCE.userToUserDataDto(user);
    }
}
