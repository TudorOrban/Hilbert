package com.hilbert.features.vocabulary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadWords {

    private HashMap<String, List<LocalDateTime>> wordsReadDates;
}
