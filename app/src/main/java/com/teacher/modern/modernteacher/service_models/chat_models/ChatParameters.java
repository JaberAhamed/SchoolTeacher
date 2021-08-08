package com.teacher.modern.modernteacher.service_models.chat_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatParameters {

    public ChatParameters(String senderId, String receiverId, String terminalId) {
        SenderId = senderId;
        ReceiverId = receiverId;
        TerminalId = terminalId;
    }

    @SerializedName("SenderId")
    @Expose
    private String SenderId;
    @SerializedName("ReceiverId")
    @Expose
    private String ReceiverId;
    @SerializedName("TerminalId")
    @Expose
    private String TerminalId;
}
