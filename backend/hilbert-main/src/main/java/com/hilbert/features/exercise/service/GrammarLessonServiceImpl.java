package com.hilbert.features.exercise.service;

import com.hilbert.features.exercise.dto.*;
import com.hilbert.features.exercise.model.Exercise;
import com.hilbert.features.exercise.model.GrammarLesson;
import com.hilbert.features.exercise.model.LessonData;
import com.hilbert.features.exercise.repository.ExerciseRepository;
import com.hilbert.features.exercise.repository.GrammarLessonRepository;
import com.hilbert.shared.error.types.ValidationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Transactional
    public GrammarLessonDto createLesson(CreateLessonDto lessonDto) {
        if (lessonDto.getExercises().size() >= MAX_EXERCISES_PER_LESSON) {
            throw new ValidationException("Too many exercises. The maximum is " + MAX_EXERCISES_PER_LESSON);
        }

        List<Long> exerciseIds = new ArrayList<>(); // Important to preserve exercise order

        for (LessonExercise lessonExercise : lessonDto.getExercises()) {
            if (lessonExercise.getExerciseType().equals(LessonExerciseType.NEW)) {
                if (lessonExercise.getNewExerciseData() == null) {
                    throw new ValidationException("New Exercise Data is invalid");
                }

                Exercise exercise = new Exercise();
                exercise.setCreatorId(lessonDto.getCreatorId());
                exercise.setExerciseData(lessonExercise.getNewExerciseData());

                Exercise savedExercise = exerciseRepository.save(exercise);

                exerciseIds.add(savedExercise.getId());
            }
            if (lessonExercise.getExerciseType().equals(LessonExerciseType.EXISTING)) {
                exerciseIds.add(lessonExercise.getExistingExerciseId());
            }
        }

        GrammarLesson lesson = new GrammarLesson();
        lesson.setCreatorId(lessonDto.getCreatorId());
        LessonData lessonData = new LessonData(exerciseIds);
        lesson.setLessonData(lessonData);

        GrammarLesson savedLesson = lessonRepository.save(lesson);

        return GrammarLessonMapper.INSTANCE.lessonToLessonDto(savedLesson);
    }
}
