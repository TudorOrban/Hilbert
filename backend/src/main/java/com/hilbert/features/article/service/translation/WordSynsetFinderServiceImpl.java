package com.hilbert.features.article.service.translation;

import com.hilbert.shared.common.enums.Language;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class WordSynsetFinderServiceImpl implements WordSynsetFinderService {

    public HashMap<String, List<String>> identifySynsetILIs(List<String> contentWords, Language language) {
        HashMap<String, List<String>> wordSynsetIds = new HashMap<>();

        String filePath = OMWFileFinderUtil.getFilePathByLanguage(language);

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(filePath));

            String currentLexicalEntryId = null;
            String currentMatchedWord = null;

            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();

                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();

                    switch (startElement.getName().getLocalPart()) {
                        case "LexicalEntry":
                            Attribute id = startElement.getAttributeByName(new QName("id"));
                            currentLexicalEntryId = id.getValue();
                            break;
                        case "Lemma":
                            Attribute writtenForm = startElement.getAttributeByName(new QName("writtenForm"));
                            String word = writtenForm.getValue();

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

                            Attribute synset = startElement.getAttributeByName(new QName("synset"));
                            String synsetId = synset.getValue();

                            wordSynsetIds.computeIfAbsent(currentMatchedWord, k -> new ArrayList<>()).add(synsetId);
                            break;
                    }
                }
                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("LexicalEntry")) {
                        currentLexicalEntryId = null;
                        currentMatchedWord = null;
                    }
                }
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        HashMap<String, List<String>> wordSynsetILIs = new HashMap<>();

        return wordSynsetILIs;
    }

    private void handleLexicalEntry(StartElement startElement) {

    }

}

/*

    <LexicalEntry id="omw-fr-6_août-n">
      <Lemma writtenForm="6 août" partOfSpeech="n" />
      <Sense id="omw-fr-6_août-15299367-n" synset="omw-fr-15299367-n" />
      <Sense id="omw-fr-21-15299367-n" synset="omw-fr-12499365-n" />
    </LexicalEntry>
    <LexicalEntry id="omw-fr-11_septembre-n">
      <Lemma writtenForm="11 septembre" partOfSpeech="n" />
      <Sense id="omw-fr-11_septembre-15300051-n" synset="omw-fr-15300051-n" />
    </LexicalEntry>
    <Synset id="omw-fr-00001740-a" ili="i1" partOfSpeech="a" members="omw-fr-comptable-00001740-a" />
    <Synset id="omw-fr-00001740-n" ili="i35545" partOfSpeech="n" members="omw-fr-entité-00001740-n" />

 */