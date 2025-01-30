package com.hilbert.features.botchat.controller;

import com.hilbert.features.botchat.dto.BotChatFullDto;
import com.hilbert.features.botchat.dto.BotChatSearchDto;
import com.hilbert.features.botchat.dto.CreateBotChatDto;
import com.hilbert.features.botchat.service.BotChatService;
import com.hilbert.shared.common.enums.Language;
import com.hilbert.shared.search.models.BotChatSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bot-chats")
public class BotChatController {
    
    private final BotChatService chatService;
    
    @Autowired
    public BotChatController(BotChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BotChatFullDto> getChatFullDto(
            @PathVariable Long id,
            @RequestParam(value = "includeUsers", required = false, defaultValue = "false") Boolean includeUsers,
            @RequestParam(value = "includeMessages", required = false, defaultValue = "false") Boolean includeMessages
    ) {
        BotChatFullDto chatDto = chatService.getChatFullDto(id, includeUsers, includeMessages);
        return ResponseEntity.ok(chatDto);
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResults<BotChatSearchDto>> searchChats(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "language", required = false, defaultValue = "NONE") Language language,
            @RequestParam(value = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAscending", required = false, defaultValue = "true") Boolean isAscending,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "itemsPerPage", defaultValue = "10") Integer itemsPerPage,
            @RequestParam(value = "includeUsers", defaultValue = "false") Boolean includeUsers
    ) {
        BotChatSearchParams searchParams = new BotChatSearchParams(
                userId, language, searchQuery, sortBy, isAscending, page, itemsPerPage);

        PaginatedResults<BotChatSearchDto> results = chatService.searchChats(searchParams, includeUsers);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<BotChatFullDto> createChat(@RequestBody CreateBotChatDto chatDto) {
        BotChatFullDto createdChat = chatService.createChat(chatDto);
        return ResponseEntity.ok(createdChat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.ok().build();
    }
}
