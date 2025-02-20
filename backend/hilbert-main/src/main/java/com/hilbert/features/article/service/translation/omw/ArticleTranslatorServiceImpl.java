package com.hilbert.features.article.service.translation.omw;

import com.hilbert.features.article.dto.TranslationRequestDto;
import com.hilbert.features.article.dto.TranslationResponseDto;
import com.hilbert.shared.error.types.HilbertServiceType;
import com.hilbert.shared.error.types.UnauthorizedException;
import com.hilbert.shared.error.types.UnavailableServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;

@Service
@Primary
public class ArticleTranslatorServiceImpl implements ArticleTranslatorService {

    @Value("${hilbert.ml.api.url}")
    private String hilbertMLApiUrl;

    @Value("${hilbert.ml.api.key}")
    private String hilbertMLApiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public ArticleTranslatorServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TranslationResponseDto translateContent(TranslationRequestDto translationRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-Key", hilbertMLApiKey);
        HttpEntity<TranslationRequestDto> requestEntity = new HttpEntity<>(translationRequestDto, headers);

        try {
            ResponseEntity<TranslationResponseDto> response = restTemplate.exchange(hilbertMLApiUrl + "/translate", HttpMethod.POST, requestEntity, TranslationResponseDto.class);
            if (response.getStatusCode().value() == 401) {
                throw new UnauthorizedException("Hilbert ML Service rejected access. Ensure you have a valid API key.");
            }

            return response.getBody();
        } catch (RestClientException e) {
            if (e.getCause() instanceof ConnectException) {
                throw new UnavailableServiceException(HilbertServiceType.HILBERT_ML);
            }
            throw e;
        }
    }
}
