package com.harshit.chat_storage_service.controller;

import com.harshit.chat_storage_service.dto.ChatMessageRequest;
import com.harshit.chat_storage_service.entity.ChatMessage;
import com.harshit.chat_storage_service.service.ChatMessageService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<ChatMessage>> getMessages(
            @PathVariable String sessionId) {

        return ResponseEntity.ok(
                chatMessageService.getMessages(sessionId));
    }
}