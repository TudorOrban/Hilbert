package com.hilbert.features.article.service.translation;

import java.util.HashMap;
import java.util.List;

public interface SynsetWordFinderService {

    HashMap<String, List<String>> findTranslationsByILIs(HashMap<String, List<String>> wordSynsetILIs);
}
