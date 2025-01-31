package com.hilbert.features.botchat.service;

import com.hilbert.features.botchat.dto.BotChatInputDto;
import com.hilbert.features.botchat.dto.BotChatMessageSearchDto;
import com.hilbert.features.botchat.dto.CreateBotChatMessageDto;
import com.hilbert.shared.common.enums.Language;
import com.hilbert.shared.search.models.BotChatMessageSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*
 * The BotChatStreamServiceImpl class is responsible for managing the lifecycle of bot chat responses.
 * Workflow in BotChatMessageController usage:
 * 1. User sends message in an initial POST request, which calls this.processMessageAndTriggerResponse
 * -> Save to database, subscribe to the Flux of the bot response from Python server and store in a sink. Return a requestId identifier to frontend.
 * 2. User sends a GET request with the requestId, which calls this.getResponseStream
 * -> Retrieve the sink from the requestId and stream Flux back to client
 * -> Save message to database
 *
 * NOTE: This 2-step workflow is necessary as browser EventSource API doesn't allow POST requests.
 *
 */
@Service
public class BotChatStreamServiceImpl implements BotChatStreamService {

    private final Map<String, Sinks.Many<String>> sinks = new ConcurrentHashMap<>();

    private final BotChatMessageService botChatMessageService;
    private final BotChatResponseService botChatResponseService;

    public BotChatStreamServiceImpl(
            BotChatMessageService botChatMessageService,
            BotChatResponseService botChatResponseService
    ) {
        this.botChatMessageService = botChatMessageService;
        this.botChatResponseService = botChatResponseService;
    }

    public String processMessageAndTriggerResponse(CreateBotChatMessageDto messageDto) {
        // Save message
        botChatMessageService.createMessage(messageDto);

        // Get flux for bot response
        BotChatInputDto inputDto = this.prepareBotResponseInput(messageDto);
        Flux<String> responseFlux = botChatResponseService.respondToUser(inputDto);

        // Store flux in a sync
        String requestId = UUID.randomUUID().toString();
        Sinks.Many<String> sink = Sinks.many().multicast().directAllOrNothing();
        sinks.put(requestId, sink);

        StringBuilder messageBuilder = new StringBuilder();

        responseFlux.subscribe(new org.reactivestreams.Subscriber<>() {
            @Override
            public void onSubscribe(org.reactivestreams.Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                sink.tryEmitNext(s);
                messageBuilder.append(s);
            }

            @Override
            public void onError(Throwable t) {
                sink.tryEmitError(t);
            }

            @Override
            public void onComplete() {
                sink.tryEmitComplete();
                sinks.remove(requestId);

                String completeMessage = messageBuilder.toString();
                saveBotMessage(messageDto.getBotChatId(), completeMessage, messageDto.getLanguage());
            }
        });

        return requestId;
    }

    public Flux<String> getResponseStream(String requestId) {
        Sinks.Many<String> sink = sinks.get(requestId);
        if (sink == null) {
            return Flux.just("error");
        }
        return sink
                .asFlux()
                .map(data -> {
                    byte[] utf8Bytes = data.getBytes(StandardCharsets.UTF_8);
                    String base64Encoded = Base64.getEncoder().encodeToString(utf8Bytes);
                    return "data: " + base64Encoded + "\n\n";
                })
                .concatWithValues("data: " + Base64.getEncoder().encodeToString("[DONE]".getBytes(StandardCharsets.UTF_8)) + "\n\n") // End of stream marker
                .log();
    }

    private BotChatInputDto prepareBotResponseInput(CreateBotChatMessageDto messageDto) {
        BotChatMessageSearchParams searchParams = new BotChatMessageSearchParams(
                messageDto.getBotChatId(), "", "createdAt", false, 1, 20
        );
        PaginatedResults<BotChatMessageSearchDto> recentMessages = botChatMessageService.searchMessages(searchParams);
        return new BotChatInputDto(
                messageDto.getContent(), recentMessages.getResults(), messageDto.getLanguage(), Language.ENGLISH, false
        );
    }

    private void saveBotMessage(Long chatId, String message, Language language) {
        CreateBotChatMessageDto messageDto = new CreateBotChatMessageDto(
            -1L, chatId, false, message, language
        );

        botChatMessageService.createMessage(messageDto);
    }
}
