package com.hilbert.features.botchat.service;

import com.hilbert.features.botchat.dto.BotChatInputDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class BotChatResponseServiceImpl implements BotChatResponseService {

    @Value("${hilbert.ml.api.url}")
    private String hilbertMLApiUrl;

    @Value("${hilbert.ml.api.key}")
    private String hilbertMLApiKey;

    private WebClient webClient;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public BotChatResponseServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl(hilbertMLApiUrl).build();
    }

    public Flux<String> respondToUser(BotChatInputDto inputDto) {
        return webClient.post()
                .uri("/chatbot/stream")
                .header("API-Key", hilbertMLApiKey)
                .bodyValue(inputDto)
                .retrieve()
                .bodyToFlux(String.class);
    }
}
