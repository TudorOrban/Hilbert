package com.hilbert.features.article.service.translation;

import com.hilbert.shared.common.enums.Language;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface SynsetWordFinderService {

    HashMap<String, Set<String>> identifyTranslationsByILIs(HashMap<String, List<String>> wordSynsetILIs, Language language);
}
