package com.hilbert.features.vocabulary.service;

import com.hilbert.features.vocabulary.model.VocabularyData;
import com.hilbert.features.vocabulary.model.Vocabulary;
import com.hilbert.features.vocabulary.repository.VocabularyRepository;
import com.hilbert.shared.common.enums.Language;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabularyServiceImpl implements VocabularyService {

    private final VocabularyRepository vocabularyRepository;

    @Autowired
    public VocabularyServiceImpl(VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;
    }

    public Vocabulary findOrCreateVocabulary(Long userId, Language language) {
        List<Vocabulary> vocabularies = vocabularyRepository.findByUserId(userId);
        List<Vocabulary> languageVocabularies = vocabularies.stream().filter(v -> v.getLanguage() == language).toList();

        Vocabulary foundVocabulary = null;
        if (languageVocabularies.isEmpty()) {
            foundVocabulary = this.createVocabulary(userId, language);
        } else {
            if (languageVocabularies.size() >= 2) {
                // TODO: Replace with structured logger
                System.out.println("User with ID: " + userId + " has multiple vocabularies for language: " + language.toString());
            }
            foundVocabulary = languageVocabularies.getFirst();
        }

        return foundVocabulary;
    }

    public Vocabulary updateVocabularyData(Long vocabularyId, VocabularyData vocabularyData) {
        Vocabulary vocabulary = vocabularyRepository.findById(vocabularyId)
                .orElseThrow(() -> new ResourceNotFoundException(vocabularyId.toString(), ResourceType.ARTICLE, ResourceIdentifierType.ID));

        vocabulary.setVocabularyData(vocabularyData);

        return vocabularyRepository.save(vocabulary);
    }

    private Vocabulary createVocabulary(Long userId, Language language) {
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setUserId(userId);
        vocabulary.setLanguage(language);
        vocabulary.setVocabularyData(new VocabularyData());

        return vocabularyRepository.save(vocabulary);
    }
}
