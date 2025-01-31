package com.hilbert.features.botchat.controller;

import com.hilbert.features.botchat.dto.BotChatMessageSearchDto;
import com.hilbert.features.botchat.dto.BotChatStartStreamResponse;
import com.hilbert.features.botchat.dto.CreateBotChatMessageDto;
import com.hilbert.features.botchat.service.BotChatMessageService;
import com.hilbert.features.botchat.service.BotChatStreamService;
import com.hilbert.shared.search.models.BotChatMessageSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("api/v1/bot-chat-messages")
public class BotChatMessageController {

    private final BotChatMessageService chatMessageService;
    private final BotChatStreamService botChatStreamService;

    @Autowired
    public BotChatMessageController(
            BotChatMessageService chatMessageService,
            BotChatStreamService botChatStreamService
    ) {
        this.chatMessageService = chatMessageService;
        this.botChatStreamService = botChatStreamService;
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResults<BotChatMessageSearchDto>> searchChats(
            @RequestParam(value = "chatId", required = false) Long chatId,
            @RequestParam(value = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAscending", required = false, defaultValue = "true") Boolean isAscending,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "itemsPerPage", defaultValue = "10") Integer itemsPerPage
    ) {
        BotChatMessageSearchParams searchParams = new BotChatMessageSearchParams(
                chatId, searchQuery, sortBy, isAscending, page, itemsPerPage);

        PaginatedResults<BotChatMessageSearchDto> results = chatMessageService.searchMessages(searchParams);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/start-responding")
    public ResponseEntity<BotChatStartStreamResponse> createMessageAndResponse(@RequestBody CreateBotChatMessageDto messageDto) {
        String requestId = botChatStreamService.processMessageAndTriggerResponse(messageDto);
        return ResponseEntity.ok(new BotChatStartStreamResponse(requestId));
    }

    @GetMapping("/response-stream/{requestId}")
    public ResponseEntity<Flux<String>> responseStream(@PathVariable String requestId) {
        Flux<String> responseStream = botChatStreamService.getResponseStream(requestId);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseStream);
    }

    @PostMapping
    public ResponseEntity<BotChatMessageSearchDto> createMessage(@RequestBody CreateBotChatMessageDto messageDto) {
        BotChatMessageSearchDto message = chatMessageService.createMessage(messageDto);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        chatMessageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }

}
