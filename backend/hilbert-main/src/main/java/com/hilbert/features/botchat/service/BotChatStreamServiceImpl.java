package com.hilbert.features.botchat.service;

import com.hilbert.features.botchat.dto.BotChatInputDto;
import com.hilbert.features.botchat.dto.BotChatMessageSearchDto;
import com.hilbert.features.botchat.dto.CreateBotChatMessageDto;
import com.hilbert.shared.common.enums.Language;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
        BotChatMessageSearchDto savedMessageDto = this.botChatMessageService.createMessage(messageDto);

        BotChatInputDto inputDto = new BotChatInputDto(
                savedMessageDto.getContent(), new ArrayList<>(), messageDto.getLanguage(), Language.ENGLISH, false
        );

        String requestId = UUID.randomUUID().toString();

        Flux<String> responseFlux = botChatResponseService.respondToUser(inputDto);

        Sinks.Many<String> sink = Sinks.many().multicast().directAllOrNothing();
        sinks.put(requestId, sink);

        responseFlux.subscribe(new org.reactivestreams.Subscriber<>() {
            @Override
            public void onSubscribe(org.reactivestreams.Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                sink.tryEmitNext(s);
            }

            @Override
            public void onError(Throwable t) {
                sink.tryEmitError(t);
            }

            @Override
            public void onComplete() {
                sink.tryEmitComplete();
                sinks.remove(requestId);
            }
        });

        return requestId;
    }

    public Flux<String> getResponseStream(String requestId) {
        Sinks.Many<String> sink = sinks.get(requestId);
        if (sink == null) {
            return Flux.just("error");
        }
        return sink.asFlux();
    }
}
