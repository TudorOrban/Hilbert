package com.hilbert.features.article.service.translation.omw;

import com.hilbert.features.article.dto.TranslationRequestDto;
import com.hilbert.features.article.dto.TranslationResponseDto;
import com.hilbert.features.article.service.read.TextWordsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/*
 * Service that transforms a text into a map (word) -> wordTranslationsArray using the OMW database.
 *
 * *NOTE*: This implementation is currently being replaced by ArticleTranslatorServiceImpl,
 * which calls a separate Python server that uses an AI model for translation
 *
 * The algorithm for a single word is:
 *  1. For a given word, go through srcLanguage XML file to find LexicalEntry with Lemma.writtenForm = word
 *  2. Use that LexicalEntry's Sense.synsets to find the corresponding Synset's ILIs
 *  3. Go through destLanguage XML file to find Synsets with that ILI
 *  4. For each found Synset, find corresponding LexicalEntries and extract their Lemma.writtenForm
 *
 * The actual algorithm below only traverses the XML file once and checks against all the given words.
 */
@Service
public class ArticleTranslatorServiceOldImpl implements ArticleTranslatorService {

    private final WordSynsetFinderService wordSynsetFinderService;
    private final SynsetWordFinderService synsetWordFinderService;
    private final TextWordsManager textWordsManager;

    @Autowired
    public ArticleTranslatorServiceOldImpl(
            WordSynsetFinderServiceImpl wordSynsetFinderService,
            SynsetWordFinderServiceImpl synsetWordFinderService,
            TextWordsManager textWordsManager
    ) {
        this.wordSynsetFinderService = wordSynsetFinderService;
        this.synsetWordFinderService = synsetWordFinderService;
        this.textWordsManager = textWordsManager;
    }

    public TranslationResponseDto translateContent(TranslationRequestDto translationRequestDto) {
        List<String> contentWords = textWordsManager.getTextWords(translationRequestDto.getContent());

        HashMap<String, List<String>> wordSynsetILIs = this.wordSynsetFinderService
                .identifySynsetILIs(contentWords, translationRequestDto.getSrcLanguage());

        HashMap<String, Set<String>> translationMap = this.synsetWordFinderService
                .identifyTranslationsByILIs(wordSynsetILIs, translationRequestDto.getDestLanguage());

        return new TranslationResponseDto(translationMap, translationRequestDto.getSrcLanguage(), translationRequestDto.getDestLanguage());
    }
}
