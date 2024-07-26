package com.uvt.bachelor.pawfectmatch.api;

import com.uvt.bachelor.pawfectmatch.model.ChatMessageDto;
import com.uvt.bachelor.pawfectmatch.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class ChatMessageController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ResponseEntity<ChatMessageDto> sendMessage(ChatMessageDto message) {
        chatService.sendMessage(message);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/chat/{senderId}/{receiverId}")
    public List<ChatMessageDto> getMessagesForPet(@PathVariable Long senderId, @PathVariable Long receiverId) {
        return chatService.getMessagesByChatId(senderId, receiverId);
    }

    @GetMapping("/chats/pet/{petId}")
    public ResponseEntity<List<ChatMessageDto>> getUserChats(@PathVariable Long petId) {
        List<ChatMessageDto> chats = chatService.getChatsForUser(petId);
        return ResponseEntity.ok(chats);
    }

}
