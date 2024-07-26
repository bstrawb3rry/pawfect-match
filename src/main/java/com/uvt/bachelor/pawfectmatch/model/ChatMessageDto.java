package com.uvt.bachelor.pawfectmatch.model;

public class ChatMessageDto {

    private Long id;
    private Long senderId;
    private String senderName;
    private String senderOwner;
    private Long receiverId;
    private String receiverName;
    private String receiverOwner;
    private String content;
    private String timestamp;

    public ChatMessageDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderOwner() {
        return senderOwner;
    }

    public void setSenderOwner(String senderOwner) {
        this.senderOwner = senderOwner;
    }

    public String getReceiverOwner() {
        return receiverOwner;
    }

    public void setReceiverOwner(String receiverOwner) {
        this.receiverOwner = receiverOwner;
    }
}
