package com.hilbert.features.exercise.controller;

import com.hilbert.features.exercise.dto.CreateLessonDto;
import com.hilbert.features.exercise.dto.GrammarLessonDto;
import com.hilbert.features.exercise.service.GrammarLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/lessons")
public class GrammarLessonController {

    private final GrammarLessonService lessonService;

    @Autowired
    public GrammarLessonController(GrammarLessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<GrammarLessonDto>> searchLessons(
            @RequestParam(name = "includeExercises", required = false, defaultValue = "false") Boolean includeExercises
    ) {
        List<GrammarLessonDto> lessons = lessonService.searchLessons(includeExercises);
        return ResponseEntity.ok(lessons);
    }

    @PostMapping
    public ResponseEntity<GrammarLessonDto> createLesson(@RequestBody CreateLessonDto lessonDto) {
        GrammarLessonDto createdLesson = lessonService.createLesson(lessonDto);
        return ResponseEntity.ok(createdLesson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.ok().build();
    }
}
