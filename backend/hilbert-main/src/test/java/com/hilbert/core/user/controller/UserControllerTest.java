package com.hilbert.core.user.controller;

import com.hilbert.core.user.dto.CreateUserDto;
import com.hilbert.core.user.dto.UpdateUserDto;
import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void whenValidUsername_thenUserDataDtoShouldBeReturned() {
        // Arrange
        String username = "testuser";
        UserDataDto userDto = new UserDataDto();
        userDto.setUsername(username);
        userDto.setEmail("testuser@example.com");
        when(userService.getByUsername(username, false)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDataDto> response = userController.getUserDataByUsername(username, false);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getUsername()).isEqualTo(username);
        assertThat(response.getBody().getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    public void whenValidCreateUserDto_thenUserShouldBeCreated() {
        // Arrange
        String username = "testuser";
        String email = "test@example.com";
        CreateUserDto userDto = new CreateUserDto(username, email, "password");
        UserDataDto userDataDto = new UserDataDto();
        userDataDto.setUsername(username);
        userDataDto.setEmail(email);

        when(userService.createUser(userDto)).thenReturn(userDataDto);

        // Act
        ResponseEntity<UserDataDto> response = userController.createUser(userDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getUsername()).isEqualTo(username);
        assertThat(response.getBody().getEmail()).isEqualTo(email);
    }

    @Test
    public void whenValidUpdateUserDto_thenUserShouldBeUpdated() {
        // Arrange
        Long id = 1L;
        String username = "testuser";
        String email = "test@example.com";
        UpdateUserDto userDto = new UpdateUserDto(id, username, email);
        UserDataDto userDataDto = new UserDataDto();
        userDataDto.setUsername(username);
        userDataDto.setEmail(email);

        when(userService.updateUser(userDto)).thenReturn(userDataDto);

        // Act
        ResponseEntity<UserDataDto> response = userController.updateUser(userDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getUsername()).isEqualTo(username);
        assertThat(response.getBody().getEmail()).isEqualTo(email);
    }
}
