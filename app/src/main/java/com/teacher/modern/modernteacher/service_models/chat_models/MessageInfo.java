package com.teacher.modern.modernteacher.service_models.chat_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageInfo {

    public MessageInfo(String senderId, String receiverId, String messageDetails, String terminalId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageDetails = messageDetails;
        this.terminalId = terminalId;
    }

    @SerializedName("SenderId")
    @Expose
    private String senderId;
    @SerializedName("ReceiverId")
    @Expose
    private String receiverId;
    @SerializedName("MessageDetails")
    @Expose
    private String messageDetails;
    @SerializedName("TerminalId")
    @Expose
    private String terminalId;
}
