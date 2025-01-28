package com.hilbert.features.vocabulary.repository;

import com.hilbert.features.vocabulary.model.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {

    List<Vocabulary> findByUserId(Long userId);
}
