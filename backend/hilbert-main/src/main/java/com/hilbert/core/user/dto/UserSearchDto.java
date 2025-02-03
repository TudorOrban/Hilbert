package com.hilbert.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchDto {

    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
}
