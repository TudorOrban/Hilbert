package com.hilbert.features.exercise.service;

import com.hilbert.features.exercise.dto.CreateLessonDto;
import com.hilbert.features.exercise.dto.GrammarLessonDto;

import java.util.List;

public interface GrammarLessonService {

    List<GrammarLessonDto> searchLessons();
    GrammarLessonDto createLesson(CreateLessonDto lessonDto);
}
