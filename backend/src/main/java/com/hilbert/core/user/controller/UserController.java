package com.hilbert.core.user.controller;

import com.hilbert.core.user.dto.CreateUserDto;
import com.hilbert.core.user.dto.UpdateUserDto;
import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<UserDataDto> createUser(@RequestBody CreateUserDto userDto) {
        UserDataDto userDataDto = userService.createUser(userDto);
        return ResponseEntity.ok(userDataDto);
    }

    @PutMapping
    public ResponseEntity<UserDataDto> updateUser(@RequestBody UpdateUserDto userDto) {
        UserDataDto userDataDto = userService.updateUser(userDto);
        return ResponseEntity.ok(userDataDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
