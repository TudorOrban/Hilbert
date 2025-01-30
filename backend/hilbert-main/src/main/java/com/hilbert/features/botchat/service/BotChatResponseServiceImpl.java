package com.hilbert.features.botchat.service;

import com.hilbert.features.botchat.dto.BotChatInputDto;
import com.hilbert.shared.error.types.HilbertServiceType;
import com.hilbert.shared.error.types.UnauthorizedException;
import com.hilbert.shared.error.types.UnavailableServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;


@Service
public class BotChatResponseServiceImpl implements BotChatResponseService {

    @Value("${hilbert.ml.api.url}")
    private String hilbertMLApiUrl;

    @Value("${hilbert.ml.api.key}")
    private String hilbertMLApiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public BotChatResponseServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String respondToUser(BotChatInputDto inputDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-Key", hilbertMLApiKey);
        HttpEntity<BotChatInputDto> requestEntity = new HttpEntity<>(inputDto, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(hilbertMLApiUrl + "/chatbot/stream", HttpMethod.POST, requestEntity, String.class);
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
