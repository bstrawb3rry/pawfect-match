package com.uvt.bachelor.pawfectmatch.service;

import com.uvt.bachelor.pawfectmatch.Transformer;
import com.uvt.bachelor.pawfectmatch.entity.ChatMessage;
import com.uvt.bachelor.pawfectmatch.entity.PetOwner;
import com.uvt.bachelor.pawfectmatch.model.ChatMessageDto;
import com.uvt.bachelor.pawfectmatch.repository.ChatMessageRepository;
import com.uvt.bachelor.pawfectmatch.repository.PetOwnerRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final PetOwnerRepository petOwnerRepository;

    public ChatService(ChatMessageRepository chatMessageRepository, PetOwnerRepository petOwnerRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.petOwnerRepository = petOwnerRepository;
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
        PetOwner senderOwner = petOwnerRepository.findById(senderId).orElseThrow();
        PetOwner receiverOwner = petOwnerRepository.findById(receiverId).orElseThrow();
        return Transformer.toDto(chat, senderOwner, receiverOwner);
    }

}
