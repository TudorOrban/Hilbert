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
import java.util.*;

@Service
public class SynsetWordFinderServiceImpl implements SynsetWordFinderService {

    private final ResourceLoader resourceLoader;

    @Autowired
    public SynsetWordFinderServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public HashMap<String, List<String>> identifyTranslationsByILIs(HashMap<String, List<String>> wordSynsetILIs, Language language) {
        String filePath = OMWFileFinderUtil.getFilePathByLanguage(language);

        try {
            Resource resource = resourceLoader.getResource("classpath:" + filePath);
            InputStream inputStream = resource.getInputStream();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(inputStream);

            HashMap<String, List<String>> wordDestSynsetIds = findWordDestSynsetIds(wordSynsetILIs, reader);

            // Create a new reader for the second traversal
            inputStream = resource.getInputStream();
            reader = xmlInputFactory.createXMLEventReader(inputStream);

            return findTranslatedWords(wordDestSynsetIds, reader);
        } catch (XMLStreamException | IOException e) {
            throw new RuntimeException("Error processing XML file: ", e);
        }
    }

    private HashMap<String, List<String>> findWordDestSynsetIds(HashMap<String, List<String>> wordSynsetILIs, XMLEventReader reader) throws XMLStreamException {
        HashMap<String, List<String>> wordSynsetIds = new HashMap<>();

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (!nextEvent.isStartElement()) {
                continue;
            }
            StartElement startElement = nextEvent.asStartElement();

            String localPart = startElement.getName().getLocalPart();
            if (!Objects.equals(localPart, "Synset")) {
                continue;
            }

            String synsetId = startElement.getAttributeByName(new QName("id")).getValue();
            String synsetILI = startElement.getAttributeByName(new QName("ili")).getValue();

            for (Map.Entry<String, List<String>> entry : wordSynsetILIs.entrySet()) {
                if (entry.getValue().contains(synsetILI)) {
                    wordSynsetIds.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(synsetId);
                }
            }
        }

        return wordSynsetIds;
    }

    private HashMap<String, List<String>> findTranslatedWords(HashMap<String, List<String>> wordDestSynsetIds, XMLEventReader reader) throws XMLStreamException {
        HashMap<String, List<String>> wordTranslationsMap = new HashMap<>();
        String currentLexicalEntryId = null;
        String currentWord = null;
        System.out.println("Third before");

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();

            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("LexicalEntry")) {
                    currentLexicalEntryId = null;
                    currentWord = null;
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
                    currentWord = startElement.getAttributeByName(new QName("writtenForm")).getValue();
                    break;
                case "Sense":
                    if (currentLexicalEntryId == null || currentWord == null) {
                        break;
                    }

                    String senseSynsetId = startElement.getAttributeByName(new QName("synset")).getValue();

                    for (Map.Entry<String, List<String>> entry : wordDestSynsetIds.entrySet()) {
                        if (entry.getValue().contains(senseSynsetId)) {
                            wordTranslationsMap.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(currentWord);
                            break;
                        }
                    }
            }
        }

        return wordTranslationsMap;
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