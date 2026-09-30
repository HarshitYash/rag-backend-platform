package com.harshit.chat_storage_service.repository;

import com.harshit.chat_storage_service.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    Page<ChatMessage> findBySessionIdOrderByIdAsc(
            String sessionId,
            Pageable pageable);
}