package com.hilbert.features.learningprofile.dto;

import com.hilbert.features.learningprofile.model.LearningProfileData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningProfileFullDto {

    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private LearningProfileData learningData;
}
