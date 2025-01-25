package com.hilbert.features.article.service.translation;

import java.util.HashMap;
import java.util.List;

public interface WordSynsetFinderService {

    HashMap<String, List<String>> identifySynsetILIs(List<String> contentWords);
}
