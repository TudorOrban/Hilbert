package com.hilbert.features.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadArticleSummaryDto {

    private HashMap<String, Integer> newWords;
}
