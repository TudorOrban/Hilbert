package com.hilbert.features.article.service;

import com.hilbert.features.article.dto.TranslationRequestDto;
import com.hilbert.features.article.dto.TranslationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Primary
public class ArticleTranslatorServiceImpl implements ArticleTranslatorService {

    @Value("${translation.api.url}")
    private String translationApiUrl;

    @Value("${translation.api.key}")
    private String translationApiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public ArticleTranslatorServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TranslationResponseDto translateContent(TranslationRequestDto translationRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-Key", translationApiKey);
        HttpEntity<TranslationRequestDto> requestEntity = new HttpEntity<>(translationRequestDto, headers);

        ResponseEntity<TranslationResponseDto> response = restTemplate.exchange(translationApiUrl, HttpMethod.POST, requestEntity, TranslationResponseDto.class);
        return response.getBody();
    }
}
