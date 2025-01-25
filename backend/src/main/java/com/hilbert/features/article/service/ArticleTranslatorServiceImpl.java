package com.hilbert.features.article.service;

import com.hilbert.features.article.model.TranslatedContent;
import com.hilbert.features.article.service.translation.SynsetWordFinderService;
import com.hilbert.features.article.service.translation.SynsetWordFinderServiceImpl;
import com.hilbert.features.article.service.translation.WordSynsetFinderService;
import com.hilbert.features.article.service.translation.WordSynsetFinderServiceImpl;
import com.hilbert.shared.common.enums.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Service that transforms a text into a map (word) -> wordTranslationsArray using the OMW database.
 * The algorithm follows these steps:
 *  1. For a given word, go through srcLanguage XML file to find LexicalEntry with Lemma.writtenForm = word
 *  2. Use that LexicalEntry's Sense.synsets to find the corresponding Synset's ILIs
 *  3. Go through destLanguage XML file to find Synsets with that ILI
 *  4. For each found Synset, find corresponding LexicalEntries and extract their Lemma.writtenForm
 */
@Service
public class ArticleTranslatorServiceImpl implements ArticleTranslatorService {

    private final WordSynsetFinderService wordSynsetFinderService;
    private final SynsetWordFinderService synsetWordFinderService;

    @Autowired
    public ArticleTranslatorServiceImpl(
            WordSynsetFinderServiceImpl wordSynsetFinderService,
            SynsetWordFinderServiceImpl synsetWordFinderService
    ) {
        this.wordSynsetFinderService = wordSynsetFinderService;
        this.synsetWordFinderService = synsetWordFinderService;
    }

    public TranslatedContent translateContent(String content, Language srcLanguage, Language destLanguage) {
        List<String> contentWords = getContentWords(content);

        HashMap<String, List<String>> wordSynsetILIs = this.wordSynsetFinderService.identifySynsetILIs(contentWords);

        HashMap<String, List<String>> translationMap = this.synsetWordFinderService.findTranslationsByILIs(wordSynsetILIs);

        return new TranslatedContent(translationMap, srcLanguage, destLanguage);
    }

    private List<String> getContentWords(String content) {
        String[] preWords = content.split(" ");

        List<String> words = new ArrayList<>();

        for (String preWord : preWords) {
            String word = preWord.replaceAll("[.,?!;:'\"]*", "");
            words.add(word);
        }

        return words;
    }
}

/*

    <LexicalEntry id="omw-fr-6_août-n">
      <Lemma writtenForm="6 août" partOfSpeech="n" />
      <Sense id="omw-fr-6_août-15299367-n" synset="omw-fr-15299367-n" />
    </LexicalEntry>
    <LexicalEntry id="omw-fr-11_septembre-n">
      <Lemma writtenForm="11 septembre" partOfSpeech="n" />
      <Sense id="omw-fr-11_septembre-15300051-n" synset="omw-fr-15300051-n" />
    </LexicalEntry>
    <Synset id="omw-fr-00001740-a" ili="i1" partOfSpeech="a" members="omw-fr-comptable-00001740-a" />
    <Synset id="omw-fr-00001740-n" ili="i35545" partOfSpeech="n" members="omw-fr-entité-00001740-n" />

 */