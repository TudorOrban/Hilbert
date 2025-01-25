package com.hilbert.features.article.service.translation;

import com.hilbert.shared.common.enums.Language;

import java.util.HashMap;
import java.util.List;

public interface WordSynsetFinderService {

    HashMap<String, List<String>> identifySynsetILIs(List<String> contentWords, Language language);
}
