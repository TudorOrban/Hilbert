package com.hilbert.core.user.dto;

import com.hilbert.features.learningprofile.dto.LearningProfileFullDto;
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

    private LearningProfileFullDto profileDto;
}
