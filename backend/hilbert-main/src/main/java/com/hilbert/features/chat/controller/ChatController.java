package com.hilbert.features.chat.controller;

import com.hilbert.features.chat.dto.ChatFullDto;
import com.hilbert.features.chat.dto.ChatSearchDto;
import com.hilbert.features.chat.dto.CreateChatDto;
import com.hilbert.features.chat.service.ChatService;
import com.hilbert.shared.search.models.ChatSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/chats")
public class ChatController {
    
    private final ChatService chatService;
    
    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatFullDto> getChatFullDto(
            @PathVariable Long id,
            @RequestParam(value = "includeMessages", required = false, defaultValue = "false") Boolean includeMessages
    ) {
        ChatFullDto chatDto = chatService.getChatFullDto(id, includeMessages);
        return ResponseEntity.ok(chatDto);
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResults<ChatSearchDto>> searchChats(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAscending", required = false, defaultValue = "true") Boolean isAscending,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "itemsPerPage", defaultValue = "10") Integer itemsPerPage,
            @RequestParam(value = "includeUsers", defaultValue = "false") Boolean includeUsers
    ) {
        ChatSearchParams searchParams = new ChatSearchParams(
                userId, searchQuery, sortBy, isAscending, page, itemsPerPage
        );

        PaginatedResults<ChatSearchDto> results = chatService.searchChats(searchParams, includeUsers);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<ChatFullDto> createChat(@RequestBody CreateChatDto chatDto) {
        ChatFullDto createdChat = chatService.createChat(chatDto);
        return ResponseEntity.ok(createdChat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.ok().build();
    }
}
