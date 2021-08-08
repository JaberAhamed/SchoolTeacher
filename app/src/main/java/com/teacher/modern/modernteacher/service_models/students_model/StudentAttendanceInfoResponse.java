package com.teacher.modern.modernteacher.service_models.students_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StudentAttendanceInfoResponse {
    @SerializedName("MessageCode")
    @Expose
    private String messageCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("SystemMessage")
    @Expose
    private String systemMessage;

    public ArrayList<StudentAttendanceInfo> getStudentInfoContent() {
        return studentInfoContent;
    }

    public void setStudentInfoContent(ArrayList<StudentAttendanceInfo> studentInfoContent) {
        this.studentInfoContent = studentInfoContent;
    }

    @SerializedName("Content")
    @Expose
    private ArrayList<StudentAttendanceInfo> studentInfoContent = null;

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


}
