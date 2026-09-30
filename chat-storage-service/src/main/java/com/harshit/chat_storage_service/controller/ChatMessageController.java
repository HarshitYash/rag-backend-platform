package com.harshit.chat_storage_service.controller;

import com.harshit.chat_storage_service.dto.ChatMessageRequest;
import com.harshit.chat_storage_service.entity.ChatMessage;
import com.harshit.chat_storage_service.service.ChatMessageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chats")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("/{sessionId}/messages")
    public ResponseEntity<ChatMessage> saveMessage(
            @PathVariable String sessionId,
            @Valid @RequestBody ChatMessageRequest request) {

        ChatMessage savedMessage = chatMessageService.saveMessage(sessionId, request.getMessage());

        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/{sessionId}/messages")
    public ResponseEntity<Page<ChatMessage>> getMessages(
            @PathVariable String sessionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                chatMessageService.getMessages(sessionId, pageable));
    }
}