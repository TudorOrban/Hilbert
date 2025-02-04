package com.hilbert.features.learningprofile.dto;


import com.hilbert.features.learningprofile.model.LearningProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LearningProfileMapper {
    LearningProfileMapper INSTANCE = Mappers.getMapper(LearningProfileMapper.class);

    @Mapping(source = "learningProfile.id", target = "id")
    @Mapping(source = "learningProfile.userId", target = "userId")
    @Mapping(source = "learningProfile.createdAt", target = "createdAt")
    @Mapping(source = "learningProfile.learningData", target = "learningData")
    LearningProfileFullDto profileToProfileFullDto(LearningProfile learningProfile);

}
