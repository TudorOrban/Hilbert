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
        System.out.println("Api url: " + hilbertMLApiUrl);
        this.webClient = webClientBuilder.baseUrl(hilbertMLApiUrl).build();
    }

    public Flux<String> respondToUser(BotChatInputDto inputDto) {
        return webClient.post()
                .uri("/chatbot/stream")
                .header("API-Key", hilbertMLApiKey)
                .bodyValue(inputDto)
                .retrieve()
                .bodyToFlux(String.class);
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("API-Key", hilbertMLApiKey);
//        HttpEntity<BotChatInputDto> requestEntity = new HttpEntity<>(inputDto, headers);
//
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(hilbertMLApiUrl + "/chatbot/stream", HttpMethod.POST, requestEntity, String.class);
//            if (response.getStatusCode().value() == 401) {
//                throw new UnauthorizedException("Hilbert ML Service rejected access. Ensure you have a valid API key.");
//            }
//
//            return response.getBody();
//        } catch (RestClientException e) {
//            if (e.getCause() instanceof ConnectException) {
//                throw new UnavailableServiceException(HilbertServiceType.HILBERT_ML);
//            }
//            throw e;
//        }
    }
}
