package com.hilbert.core.user.service;

import com.hilbert.core.user.dto.CreateUserDto;
import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.model.User;
import com.hilbert.core.user.repository.UserRepository;
import com.hilbert.shared.error.ResourceAlreadyExistsException;
import com.hilbert.shared.error.ResourceNotFoundException;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoderUtil passwordEncoderUtil;

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
        UserDataDto userDataDto = userService.getByUsername(username);

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
            userService.getByUsername(username);
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
    public void whenUsernameAlreadyExists_thenThrowUsernameAlreadyExistsException() {
        // Arrange
        String username = "testuser";
        CreateUserDto userDto = new CreateUserDto(username, "test@example.com", "password");
        when(userRepository.existsByUsername(username)).thenReturn(true);

        // Act & Assert
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            userService.createUser(userDto);
        });
    }
}
