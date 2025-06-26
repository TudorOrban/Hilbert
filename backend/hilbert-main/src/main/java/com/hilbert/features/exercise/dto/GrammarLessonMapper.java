package com.hilbert.features.exercise.dto;

import com.hilbert.features.exercise.model.GrammarLesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GrammarLessonMapper {
    GrammarLessonMapper INSTANCE = Mappers.getMapper(GrammarLessonMapper.class);

    @Mapping(source = "lesson.id", target = "id")
    @Mapping(source = "lesson.creatorId", target = "creatorId")
    @Mapping(source = "lesson.createdAt", target = "createdAt")
    @Mapping(source = "lesson.updatedAt", target = "updatedAt")
    @Mapping(source = "lesson.lessonData", target = "lessonData")
    GrammarLessonDto lessonToLessonDto(GrammarLesson lesson);

}
