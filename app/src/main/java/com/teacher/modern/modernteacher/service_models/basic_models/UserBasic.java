package com.teacher.modern.modernteacher.service_models.basic_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBasic {
    public UserBasic(String email, String userId, String terminalId) {
        Email = email;
        UserId = userId;
        TerminalId = terminalId;
    }

    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("UserId")
    @Expose
    private String UserId;
    @SerializedName("TerminalId")
    @Expose
    private String TerminalId;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTerminalId() {
        return TerminalId;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }
}
