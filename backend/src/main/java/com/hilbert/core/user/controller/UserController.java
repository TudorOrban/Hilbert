package com.hilbert.core.user.controller;

import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDataDto> getUserDataByUsername(@PathVariable String username) {
        UserDataDto userDto = userService.getByUsername(username);
        return ResponseEntity.ok(userDto);
    }
}
