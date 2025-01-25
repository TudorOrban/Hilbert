package com.hilbert.features.article.service.translation;

import com.hilbert.shared.common.enums.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WordSynsetFinderServiceImpl implements WordSynsetFinderService {

    private final ResourceLoader resourceLoader;

    @Autowired
    public WordSynsetFinderServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public HashMap<String, List<String>> identifySynsetILIs(List<String> contentWords, Language language) {
        String filePath = OMWFileFinderUtil.getFilePathByLanguage(language);

        try {
            Resource resource = resourceLoader.getResource("classpath:" + filePath);
            InputStream inputStream = resource.getInputStream();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(inputStream);

            return findWordSynsetILIs(contentWords, reader);
        } catch (XMLStreamException | IOException e) {
            throw new RuntimeException("Error processing XML file: ", e);
        }
    }

    private HashMap<String, List<String>> findWordSynsetILIs(List<String> contentWords, XMLEventReader reader) throws XMLStreamException {
        HashMap<String, List<String>> wordSynsetIds = new HashMap<>();
        HashMap<String, List<String>> wordSynsetILIs = new HashMap<>();
        String currentLexicalEntryId = null;
        String currentMatchedWord = null;

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();

            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("LexicalEntry")) {
                    currentLexicalEntryId = null;
                    currentMatchedWord = null;
                }
            }

            if (!nextEvent.isStartElement()) {
                continue;
            }
            StartElement startElement = nextEvent.asStartElement();

            switch (startElement.getName().getLocalPart()) {
                case "LexicalEntry":
                    currentLexicalEntryId = startElement.getAttributeByName(new QName("id")).getValue();
                    break;
                case "Lemma":
                    String word = startElement.getAttributeByName(new QName("writtenForm")).getValue();

                    if (contentWords.contains(word)) {
                        currentMatchedWord = word;
                    } else {
                        currentMatchedWord = null;
                    }
                    break;
                case "Sense":
                    if (currentLexicalEntryId == null || currentMatchedWord == null) {
                        break;
                    }

                    String senseSynsetId = startElement.getAttributeByName(new QName("synset")).getValue();

                    wordSynsetIds.computeIfAbsent(currentMatchedWord, k -> new ArrayList<>()).add(senseSynsetId);
                    break;
                // Synset case can be handled in the same traversal as Synset elements are always after all the LexicalEntries
                case "Synset":
                    String synsetId = startElement.getAttributeByName(new QName("id")).getValue();
                    String synsetILI = startElement.getAttributeByName(new QName("ili")).getValue();

                    for (Map.Entry<String, List<String>> entry : wordSynsetIds.entrySet()) {
                        if (entry.getValue().contains(synsetId)) {
                            wordSynsetILIs.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(synsetILI);
                            break;
                        }
                    }
                    break;
            }
        }

        return wordSynsetILIs;
    }
}

/*
 *   Example from omw-fr:
 *
 *   <LexicalEntry id="omw-fr-6_août-n">
 *     <Lemma writtenForm="6 août" partOfSpeech="n" />
 *     <Sense id="omw-fr-6_août-15299367-n" synset="omw-fr-15299367-n" />
 *     <Sense id="omw-fr-21-15299367-n" synset="omw-fr-12499365-n" />
 *   </LexicalEntry>
 *   <LexicalEntry id="omw-fr-11_septembre-n">
 *     <Lemma writtenForm="11 septembre" partOfSpeech="n" />
 *     <Sense id="omw-fr-11_septembre-15300051-n" synset="omw-fr-15300051-n" />
 *   </LexicalEntry>
 *   <Synset id="omw-fr-00001740-a" ili="i1" partOfSpeech="a" members="omw-fr-comptable-00001740-a" />
 *   <Synset id="omw-fr-00001740-n" ili="i35545" partOfSpeech="n" members="omw-fr-entité-00001740-n" />
 *
 */