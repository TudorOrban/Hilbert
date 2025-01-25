package com.hilbert.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDto {
    private Long id;
    private String username;
    private String email;
    private String passwordHash;
}
