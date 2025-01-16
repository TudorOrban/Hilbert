package com.hilbert.core.user.service;

import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.model.User;
import com.hilbert.core.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

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
}
