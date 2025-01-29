package com.hilbert.features.chat.controller;

import com.hilbert.features.chat.dto.CreateMessageDto;
import com.hilbert.features.chat.dto.MessageSearchDto;
import com.hilbert.features.chat.service.ChatMessageService;
import com.hilbert.shared.search.models.ChatMessageSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/chat-messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResults<MessageSearchDto>> searchChats(
            @RequestParam(value = "chatId", required = false) Long chatId,
            @RequestParam(value = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAscending", required = false, defaultValue = "true") Boolean isAscending,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "itemsPerPage", defaultValue = "10") Integer itemsPerPage
    ) {
        ChatMessageSearchParams searchParams = new ChatMessageSearchParams(
                chatId, searchQuery, sortBy, isAscending, page, itemsPerPage
        );

        PaginatedResults<MessageSearchDto> results = chatMessageService.searchMessages(searchParams);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<MessageSearchDto> createChat(@RequestBody CreateMessageDto messageDto) {
        MessageSearchDto message = chatMessageService.createMessage(messageDto);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        chatMessageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }

}
