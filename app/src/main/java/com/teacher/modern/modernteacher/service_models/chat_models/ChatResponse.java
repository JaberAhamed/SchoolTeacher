package com.teacher.modern.modernteacher.service_models.chat_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatResponse {
    @SerializedName("MessageCode")
    @Expose
    private String messageCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("SystemMessage")
    @Expose
    private String systemMessage;
    @SerializedName("Content")
    @Expose
    private ArrayList<ChatInfo> chatInfo = null;

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public ArrayList<ChatInfo> getChatInfo() {
        return chatInfo;
    }

    public void setChatInfo(ArrayList<ChatInfo> chatInfo) {
        this.chatInfo = chatInfo;
    }

}