package com.teacher.modern.modernteacher.service_models.chat_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatInfo {

    @SerializedName("MessageId")
    @Expose
    private String messageId;
    @SerializedName("SenderId")
    @Expose
    private String senderId;
    @SerializedName("ReceiverId")
    @Expose
    private String receiverId;
    @SerializedName("MessageDetails")
    @Expose
    private String messageDetails;
    @SerializedName("MessageTime")
    @Expose
    private String messageTime;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessageDetails() {
        return messageDetails;
    }

    public void setMessageDetails(String messageDetails) {
        this.messageDetails = messageDetails;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
