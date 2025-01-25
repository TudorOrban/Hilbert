package com.hilbert.features.article.service.translation;

import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import java.util.HashMap;
import java.util.List;

@Service
public class WordSynsetFinderServiceImpl implements WordSynsetFinderService {


    public HashMap<String, List<String>> identifySynsetILIs(List<String> contentWords) {
        HashMap<String, List<String>> wordSynsetIds = new HashMap<>();

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
//        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(""));

        HashMap<String, List<String>> wordSynsetILIs = new HashMap<>();

        return wordSynsetILIs;
    }
}
