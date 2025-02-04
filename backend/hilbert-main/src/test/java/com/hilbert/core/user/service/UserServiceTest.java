package com.hilbert.core.user.service;

import com.hilbert.core.user.dto.CreateUserDto;
import com.hilbert.core.user.dto.UpdateUserDto;
import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.model.User;
import com.hilbert.core.user.repository.UserRepository;
import com.hilbert.shared.error.types.ResourceAlreadyExistsException;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.sanitization.service.EntitySanitizerService;
import com.hilbert.shared.util.PasswordEncoderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoderUtil passwordEncoderUtil;
    @Mock
    private EntitySanitizerService entitySanitizerService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void whenValidUsername_thenUserDataDtoShouldBeReturned() {
        // Arrange
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        user.setEmail("testuser@example.com");
        user.setPasswordHash("passwordhash");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        UserDataDto userDataDto = userService.getByUsername(username, false);

        // Assert
        assertThat(userDataDto).isNotNull();
        assertThat(userDataDto.getUsername()).isEqualTo(username);
        assertThat(userDataDto.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void whenInvalidUsername_thenResourceNotFoundExceptionShouldBeThrown() {
        // Arrange
        String username = "testuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getByUsername(username, false);
        });
    }

    @Test
    public void whenValidCreateUserDto_thenUserShouldBeCreated() {
        // Arrange
        String username = "testuser";
        String email = "test@example.com";
        String rawPassword = "password";
        String hashedPassword = "hashedPassword";
        CreateUserDto userDto = new CreateUserDto(username, email, rawPassword);
        User savedUser = new User();
        savedUser.setUsername(username);
        savedUser.setEmail(email);
        savedUser.setPasswordHash(hashedPassword);

        when(entitySanitizerService.sanitizeCreateUserDto(any(CreateUserDto.class))).thenReturn(userDto);
        when(passwordEncoderUtil.encode(rawPassword)).thenReturn(hashedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserDataDto userDataDto = userService.createUser(userDto);

        // Assert
        assertThat(userDataDto).isNotNull();
        assertThat(userDataDto.getUsername()).isEqualTo(username);
        assertThat(userDataDto.getEmail()).isEqualTo(email);
    }

    @Test
    public void whenUsernameAlreadyExists_thenUsernameAlreadyExistsExceptionShouldBeThrown() {
        // Arrange
        String username = "testuser";
        CreateUserDto userDto = new CreateUserDto(username, "test@example.com", "password");
        when(entitySanitizerService.sanitizeCreateUserDto(any(CreateUserDto.class))).thenReturn(userDto);
        when(userRepository.existsByUsername(username)).thenReturn(true);

        // Act & Assert
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            userService.createUser(userDto);
        });
    }

    @Test
    public void whenValidUpdateUserDto_thenUserShouldBeUpdated() {
        // Arrange
        Long id = 1L;
        String username = "testuser";
        String email = "test@example.com";
        UpdateUserDto userDto = new UpdateUserDto(id, username, email);
        User updatedUser = new User();
        updatedUser.setUsername(username);
        updatedUser.setEmail(email);
        User existingUser = new User();
        existingUser.setUsername("beforeupdate");
        existingUser.setEmail("beforeupdate@example.com");

        when(entitySanitizerService.sanitizeUpdateUserDto(any(UpdateUserDto.class))).thenReturn(userDto);
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Act
        UserDataDto userDataDto = userService.updateUser(userDto);

        // Assert
        assertThat(userDataDto).isNotNull();
        assertThat(userDataDto.getUsername()).isEqualTo(username);
        assertThat(userDataDto.getEmail()).isEqualTo(email);
    }

    @Test
    public void whenInvalidUserId_thenResourceNotFoundExceptionShouldBeThrown() {
        // Arrange
        Long id = 1L;
        String username = "testuser";
        String email = "test@example.com";
        UpdateUserDto userDto = new UpdateUserDto(id, username, email);

        when(entitySanitizerService.sanitizeUpdateUserDto(any(UpdateUserDto.class))).thenReturn(userDto);
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.updateUser(userDto);
        });
    }

    @Test
    public void whenValidUserId_thenUserShouldBeDeleted() {
        // Arrange
        Long id = 1L;
        User existingUser = new User();
        existingUser.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        // Act
        userService.deleteUser(id);

        // Assert
        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    public void whenInvalidUserId_thenUSerShouldNotBeDeleted() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            this.userService.deleteUser(id);
        });
    }
}
