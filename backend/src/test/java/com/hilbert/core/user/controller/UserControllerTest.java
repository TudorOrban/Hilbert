package com.hilbert.core.user.controller;

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
        when(userService.getByUsername(username)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDataDto> response = userController.getUserDataByUsername(username);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getUsername()).isEqualTo(username);
        assertThat(response.getBody().getEmail()).isEqualTo(userDto.getEmail());
    }
}
