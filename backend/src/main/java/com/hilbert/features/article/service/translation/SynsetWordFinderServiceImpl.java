package com.hilbert.features.article.service.translation;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SynsetWordFinderServiceImpl implements SynsetWordFinderService {


    public HashMap<String, List<String>> findTranslationsByILIs(HashMap<String, List<String>> wordSynsetILIs) {
        HashMap<String, List<String>> translationMap = new HashMap<>();

        return translationMap;
    }
}
