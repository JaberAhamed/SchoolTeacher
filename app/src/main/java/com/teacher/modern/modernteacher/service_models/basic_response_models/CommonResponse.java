package com.teacher.modern.modernteacher.service_models.basic_response_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonResponse {
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
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
