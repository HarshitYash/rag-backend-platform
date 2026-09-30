package com.harshit.chat_storage_service;

import com.harshit.chat_storage_service.controller.ChatMessageController;
import com.harshit.chat_storage_service.entity.ChatMessage;
import com.harshit.chat_storage_service.exception.GlobalExceptionHandler;
import com.harshit.chat_storage_service.repository.ChatMessageRepository;
import com.harshit.chat_storage_service.service.ChatMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatStorageServiceApplicationTests {

	@Test
	void getMessages_shouldReturnPaginatedMessages() {
		ChatMessageRepository repository = mock(ChatMessageRepository.class);
		ChatMessageService service = new ChatMessageService(repository);

		ChatMessage message1 = new ChatMessage("test-session", "Hello");
		ChatMessage message2 = new ChatMessage("test-session", "World");

		Pageable pageable = PageRequest.of(0, 2);

		Page<ChatMessage> expectedPage = new PageImpl<>(List.of(message1, message2), pageable, 2);

		when(repository.findBySessionIdOrderByIdAsc(
				"test-session",
				pageable)).thenReturn(expectedPage);

		Page<ChatMessage> result = service.getMessages("test-session", pageable);

		assertEquals(2, result.getContent().size());
		assertEquals("Hello", result.getContent().get(0).getMessage());
		assertEquals("World", result.getContent().get(1).getMessage());

		verify(repository).findBySessionIdOrderByIdAsc(
				"test-session",
				pageable);
	}

	@Test
	void saveMessage_shouldRejectBlankMessage() throws Exception {
		ChatMessageService service = mock(ChatMessageService.class);

		ChatMessageController controller = new ChatMessageController(service);

		MockMvc mockMvc = standaloneSetup(controller)
				.setControllerAdvice(new GlobalExceptionHandler())
				.build();

		mockMvc.perform(
				post("/api/chats/test-session/messages")
						.contentType("application/json")
						.content("{\"message\":\"\"}"))
				.andExpect(status().isBadRequest());

		verifyNoInteractions(service);
	}
}