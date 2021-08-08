package com.teacher.modern.modernteacher.service_models.exam_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamGroupBasic {
    public ExamGroupBasic(String groupId, String examGroupId, String userId, String terminalId) {
        GroupId = groupId;
        ExamGroupId = examGroupId;
        UserId = userId;
        TerminalId = terminalId;
    }

    @SerializedName("GroupId")
    @Expose
    private String GroupId;
    @SerializedName("ExamGroupId")
    @Expose
    private String ExamGroupId;
    @SerializedName("UserId")
    @Expose
    private String UserId;
    @SerializedName("TerminalId")
    @Expose
    private String TerminalId;

}
