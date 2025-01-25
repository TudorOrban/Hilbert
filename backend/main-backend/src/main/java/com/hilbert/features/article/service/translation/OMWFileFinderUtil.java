package com.hilbert.features.article.service.translation;

import com.hilbert.shared.common.enums.Language;
import com.hilbert.shared.error.types.ValidationException;

import java.util.EnumMap;
import java.util.Map;

public class OMWFileFinderUtil {

    private static final EnumMap<Language, String> fileCodeMap = new EnumMap<>(Map.of(
            Language.ENGLISH, "en",
            Language.FRENCH, "fr",
            Language.SPANISH, "es"
    ));

    public static String getFilePathByLanguage(Language language) {
        String fileCode = fileCodeMap.get(language);
        if (fileCode == null) {
            throw new ValidationException("Language not supported yet.");
        }

        return "static/dictionary/omw-1.4/omw-" + fileCode + "/omw-" + fileCode + ".xml";
    }
}
