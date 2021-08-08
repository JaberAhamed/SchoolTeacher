package com.teacher.modern.modernteacher.service_models.basic_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupBasic {
    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
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

    public GroupBasic(String groupId, String userId, String terminalId) {
        GroupId = groupId;
        UserId = userId;
        TerminalId = terminalId;
    }

    @SerializedName("GroupId")
    @Expose
    private String GroupId;
    @SerializedName("UserId")
    @Expose
    private String UserId;
    @SerializedName("TerminalId")
    @Expose
    private String TerminalId;
}
