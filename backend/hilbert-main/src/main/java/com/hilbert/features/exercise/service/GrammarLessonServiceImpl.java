package com.hilbert.features.exercise.service;

import com.hilbert.features.exercise.dto.*;
import com.hilbert.features.exercise.model.Exercise;
import com.hilbert.features.exercise.model.GrammarLesson;
import com.hilbert.features.exercise.model.LessonData;
import com.hilbert.features.exercise.repository.ExerciseRepository;
import com.hilbert.features.exercise.repository.GrammarLessonRepository;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import com.hilbert.shared.error.types.ValidationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<GrammarLessonDto> searchLessons(Boolean includeExercises) { // TODO: Add Search Params
        List<GrammarLesson> lessons = lessonRepository.findAll();

        List<Exercise> allExercises = new ArrayList<>();
        if (includeExercises) {
            allExercises = this.fetchAllExercises(lessons);
        }

        List<GrammarLessonDto> lessonDtos = new ArrayList<>();

        for (GrammarLesson lesson : lessons) {
            GrammarLessonDto lessonDto = GrammarLessonMapper.INSTANCE.lessonToLessonDto(lesson);

            List<Exercise> lessonExercises = allExercises.stream()
                    .filter(e -> lessonDto.getLessonData().getExerciseIds().contains(e.getId())).toList();

            List<ExerciseFullDto> exerciseDtos = lessonExercises.stream().map(ExerciseMapper.INSTANCE::exerciseToExerciseFullDto).toList();
            lessonDto.setExercises(exerciseDtos);

            lessonDtos.add(lessonDto);
        }

        return lessonDtos;
    }

    private List<Exercise> fetchAllExercises(List<GrammarLesson> lessons) {
        Set<Long> allUniqueExerciseIds = lessons.stream()
                .filter(lesson -> lesson.getLessonDataJson() != null && !lesson.getLessonDataJson().isEmpty())
                .flatMap(lesson -> {
                    List<Long> exerciseIds = lesson.getLessonData().getExerciseIds();
                    return exerciseIds != null ? exerciseIds.stream() : java.util.stream.Stream.empty();
                })
                .collect(Collectors.toSet());

        return exerciseRepository.findAllById(allUniqueExerciseIds);
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

    public void deleteLesson(Long id) {
        GrammarLesson existingLesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.CHAT, ResourceIdentifierType.ID));

        lessonRepository.delete(existingLesson);
    }
}
