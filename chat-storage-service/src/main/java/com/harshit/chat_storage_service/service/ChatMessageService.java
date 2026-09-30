package com.harshit.chat_storage_service.service;

import com.harshit.chat_storage_service.entity.ChatMessage;
import com.harshit.chat_storage_service.repository.ChatMessageRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @CacheEvict(value = "chatMessages", allEntries = true)
    public ChatMessage saveMessage(String sessionId, String message) {
        ChatMessage chatMessage = new ChatMessage(sessionId, message);
        return chatMessageRepository.save(chatMessage);
    }

    @Cacheable(value = "chatMessages", key = "#sessionId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<ChatMessage> getMessages(String sessionId, Pageable pageable) {
        return chatMessageRepository.findBySessionIdOrderByIdAsc(
                sessionId,
                pageable);
    }
}