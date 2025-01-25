package com.hilbert.features.article.service.translation;

import com.hilbert.shared.common.enums.Language;

import java.util.HashMap;
import java.util.List;

public interface SynsetWordFinderService {

    HashMap<String, List<String>> identifyTranslationsByILIs(HashMap<String, List<String>> wordSynsetILIs, Language language);
}
