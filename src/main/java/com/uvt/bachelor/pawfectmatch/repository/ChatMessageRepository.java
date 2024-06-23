package com.uvt.bachelor.pawfectmatch.repository;

import com.uvt.bachelor.pawfectmatch.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query(value = "SELECT DISTINCT ON (LEAST(c.sender_id, c.receiver_id), GREATEST(c.sender_id, c.receiver_id)) " +
            "c.id, c.sender_id, c.receiver_id, c.content, c.timestamp " +
            "FROM chat_messages c " +
            "WHERE c.sender_id = :userId OR c.receiver_id = :userId " +
            "ORDER BY LEAST(c.sender_id, c.receiver_id), GREATEST(c.sender_id, c.receiver_id), c.timestamp DESC",
            nativeQuery = true)
    List<ChatMessage> findByUserId(Long userId);

    @Query("SELECT cm FROM ChatMessage cm WHERE (cm.senderId = :senderId AND cm.receiverId = :receiverId) OR (cm.senderId = :receiverId AND cm.receiverId = :senderId) ORDER BY cm.timestamp ASC")
    Collection<ChatMessage> findMessagesFromChat(Long senderId, Long receiverId);
}
