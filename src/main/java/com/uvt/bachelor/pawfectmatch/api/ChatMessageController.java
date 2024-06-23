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
    public ChatMessageDto sendMessage(ChatMessageDto message) {
        chatService.sendMessage(message);
        return message;
    }

    @GetMapping("/chat/{senderId}/{receiverId}")
    public List<ChatMessageDto> getMessagesForUser(@PathVariable Long senderId, @PathVariable Long receiverId) {
        return chatService.getMessagesByChatId(senderId, receiverId);
    }

    @GetMapping("/chats/user/{userId}")
    public ResponseEntity<List<ChatMessageDto>> getUserChats(@PathVariable Long userId) {
        List<ChatMessageDto> chats = chatService.getChatsForUser(userId);
        return ResponseEntity.ok(chats);
    }

}
