package com.teacher.modern.modernteacher.service_models.students_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceGroup {
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

    public String getAttendanceDate() {
        return AttendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        AttendanceDate = attendanceDate;
    }

    public AttendanceGroup(String groupId, String userId, String terminalId, String attendanceDate) {
        GroupId = groupId;
        UserId = userId;
        TerminalId = terminalId;
        AttendanceDate = attendanceDate;
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
    @SerializedName("AttendanceDate")
    @Expose
    private String AttendanceDate;
}
