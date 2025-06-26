package com.hilbert.features.exercise.service;

import com.hilbert.features.exercise.dto.*;
import com.hilbert.features.exercise.model.Exercise;
import com.hilbert.features.exercise.repository.ExerciseRepository;
import com.hilbert.features.exercise.repository.GrammarLessonRepository;
import com.hilbert.shared.error.types.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrammarLessonServiceImpl implements GrammarLessonService {

    private final GrammarLessonRepository lessonRepository;
    private final ExerciseRepository exerciseRepository;

    private static final Integer MAX_EXERCISES_PER_LESSON = 50;

    @Autowired
    public GrammarLessonServiceImpl(
            GrammarLessonRepository lessonRepository,
            ExerciseRepository exerciseRepository
    ) {
        this.lessonRepository = lessonRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public List<GrammarLessonDto> searchLessons() {
        return lessonRepository.findAll().stream()
                .map(GrammarLessonMapper.INSTANCE::lessonToLessonDto).toList();
    }

    public GrammarLessonDto createLesson(CreateLessonDto lessonDto) {
        List<LessonExercise> newLessonExercises = lessonDto.getExercises().values().stream()
                .filter(lessonExercise -> lessonExercise.getExerciseType().equals(LessonExerciseType.NEW)).toList();

        List<Exercise> newExercises = newLessonExercises.stream()
                .map(newExercise -> {
                    if (newExercise.getNewExerciseData() == null) {
                        throw new ValidationException("New Exercise Data is invalid");
                    }

                    Exercise exercise = new Exercise();
                    exercise.setCreatorId(lessonDto.getCreatorId());
                    exercise.setExerciseData(newExercise.getNewExerciseData());
                    return exercise;
                }).toList();

        List<Exercise> savedExercises = exerciseRepository.saveAll(newExercises);

        

        return new GrammarLessonDto();
    }
}
