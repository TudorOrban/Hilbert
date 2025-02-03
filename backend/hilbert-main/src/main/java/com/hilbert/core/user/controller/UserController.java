package com.hilbert.core.user.controller;

import com.hilbert.core.user.dto.CreateUserDto;
import com.hilbert.core.user.dto.UpdateUserDto;
import com.hilbert.core.user.dto.UserDataDto;
import com.hilbert.core.user.dto.UserSearchDto;
import com.hilbert.core.user.service.UserService;
import com.hilbert.shared.common.enums.DifficultyLevel;
import com.hilbert.shared.common.enums.Language;
import com.hilbert.shared.search.models.PaginatedResults;
import com.hilbert.shared.search.models.UserSearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/search")
    public ResponseEntity<PaginatedResults<UserSearchDto>> searchChats(
            @RequestParam(value = "languages", required = false) String languagesParam,
            @RequestParam(value = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAscending", required = false, defaultValue = "true") Boolean isAscending,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "itemsPerPage", defaultValue = "10") Integer itemsPerPage
    ) {
        Map<Language, DifficultyLevel> languages = parseLanguagesParam(languagesParam);
        UserSearchParams searchParams = new UserSearchParams(
                languages, searchQuery, sortBy, isAscending, page, itemsPerPage
        );

        PaginatedResults<UserSearchDto> results = userService.searchUsers(searchParams);
        return ResponseEntity.ok(results);
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


    // Manual parsing of searchParams Map for language-difficulty
    private Map<Language, DifficultyLevel> parseLanguagesParam(String languagesParam) {
        Map<Language, DifficultyLevel> languages = new HashMap<>();
        if (languagesParam != null && !languagesParam.isEmpty()) {
            String[] pairs = languagesParam.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    Language language = Language.valueOf(keyValue[0].toUpperCase());
                    DifficultyLevel level = DifficultyLevel.valueOf(keyValue[1].toUpperCase());
                    languages.put(language, level);
                }
            }
        }
        return languages;
    }
}
