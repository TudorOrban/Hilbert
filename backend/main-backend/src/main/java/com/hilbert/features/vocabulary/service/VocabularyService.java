package com.hilbert.features.vocabulary.service;

import com.hilbert.features.vocabulary.model.VocabularyData;
import com.hilbert.features.vocabulary.model.Vocabulary;
import com.hilbert.shared.common.enums.Language;

public interface VocabularyService {

    Vocabulary findOrCreateVocabulary(Long userId, Language language);
    Vocabulary updateVocabularyData(Long vocabularyId, VocabularyData vocabularyData);
}
