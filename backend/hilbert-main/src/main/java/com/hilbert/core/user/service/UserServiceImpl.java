package com.hilbert.core.user.service;

import com.hilbert.core.user.dto.*;
import com.hilbert.core.user.model.User;
import com.hilbert.core.user.repository.UserRepository;
import com.hilbert.shared.error.types.ResourceAlreadyExistsException;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import com.hilbert.shared.sanitization.service.EntitySanitizerService;
import com.hilbert.shared.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public UserServiceImpl(
        UserRepository userRepository,
        PasswordEncoderUtil passwordEncoderUtil,
        EntitySanitizerService entitySanitizerService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoderUtil = passwordEncoderUtil;
        this.entitySanitizerService = entitySanitizerService;
    }

    public UserDataDto getByUsername(String username) {
        User foundUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username, ResourceType.USER, ResourceIdentifierType.USERNAME));

        return this.mapUserToUserDataDto(foundUser);
    }

    public List<UserSmallDto> getByIds(List<Long> ids) {
        List<User> users = userRepository.findByIds(ids);

        return users.stream().map(this::mapUserToUserSmallDto).toList();
    }

    public UserDataDto createUser(CreateUserDto userDto) {
        CreateUserDto sanitizedUserDto = entitySanitizerService.sanitizeCreateUserDto(userDto);

        if (userRepository.existsByUsername(sanitizedUserDto.getUsername())) {
            throw new ResourceAlreadyExistsException(sanitizedUserDto.getUsername(), ResourceType.USER, ResourceIdentifierType.USERNAME);
        }
        if (userRepository.existsByEmail(sanitizedUserDto.getEmail())) {
            throw new ResourceAlreadyExistsException(sanitizedUserDto.getUsername(), ResourceType.USER, ResourceIdentifierType.EMAIL);
        }

        User user = UserMapper.INSTANCE.createUserDtoToUser(sanitizedUserDto);
        user.setPasswordHash(passwordEncoderUtil.encode(sanitizedUserDto.getPassword()));

        User savedUser = this.userRepository.save(user);

        return this.mapUserToUserDataDto(savedUser);
    }

    public UserDataDto updateUser(UpdateUserDto userDto) {
        UpdateUserDto sanitizedUserDto = entitySanitizerService.sanitizeUpdateUserDto(userDto);

        User existingUser = this.userRepository.findById(sanitizedUserDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedUserDto.getId().toString(), ResourceType.USER, ResourceIdentifierType.ID));

        existingUser.setUsername(sanitizedUserDto.getUsername());
        existingUser.setEmail(sanitizedUserDto.getEmail());

        User updatedUser = this.userRepository.save(existingUser);

        return this.mapUserToUserDataDto(updatedUser);
    }

    public void deleteUser(Long id) {
        User foundUser = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.USER, ResourceIdentifierType.ID));

        this.userRepository.delete(foundUser);
    }

    private UserDataDto mapUserToUserDataDto(User user) {
        return UserMapper.INSTANCE.userToUserDataDto(user);
    }

    private UserSmallDto mapUserToUserSmallDto(User user) {
        return UserMapper.INSTANCE.userToUserSmallDto(user);
    }
}
