package com.uvt.bachelor.pawfectmatch.service;

import com.uvt.bachelor.pawfectmatch.Transformer;
import com.uvt.bachelor.pawfectmatch.entity.ChatMessage;
import com.uvt.bachelor.pawfectmatch.entity.Pet;
import com.uvt.bachelor.pawfectmatch.model.ChatMessageDto;
import com.uvt.bachelor.pawfectmatch.repository.ChatMessageRepository;
import com.uvt.bachelor.pawfectmatch.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final PetRepository petRepository;

    public ChatService(ChatMessageRepository chatMessageRepository, PetRepository petRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.petRepository = petRepository;
    }

    public List<ChatMessageDto> getChatsForUser(Long userId) {
        return chatMessageRepository.findByUserId(userId).stream()
                .map(this::mapToChatDto).collect(Collectors.toList());
    }


    public void sendMessage(ChatMessageDto message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderId(message.getSenderId());
        chatMessage.setReceiverId(message.getReceiverId());
        chatMessage.setContent(message.getContent());
        chatMessage.setTimestamp(Timestamp.from(Instant.now()));
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessageDto> getMessagesByChatId(Long senderId, Long receiverId) {
        return chatMessageRepository.findMessagesFromChat(senderId, receiverId).stream()
                .map(this::mapToChatDto).collect(Collectors.toList());
    }

    private ChatMessageDto mapToChatDto(ChatMessage chat) {
        Long senderId = chat.getSenderId();
        Long receiverId = chat.getReceiverId();
        Pet sender = petRepository.findById(senderId).orElseThrow();
        Pet receiver = petRepository.findById(receiverId).orElseThrow();
        return Transformer.toDto(chat, sender, receiver);
    }

}
