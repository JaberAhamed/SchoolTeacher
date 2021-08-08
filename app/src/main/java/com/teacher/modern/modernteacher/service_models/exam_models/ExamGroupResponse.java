package com.teacher.modern.modernteacher.service_models.exam_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ExamGroupResponse {

    @SerializedName("MessageCode")
    @Expose
    private String messageCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("SystemMessage")
    @Expose
    private String systemMessage;

    public ArrayList<ExamGroupInfo> getContentExamGroups() {
        return contentExamGroups;
    }

    public void setContentExamGroups(ArrayList<ExamGroupInfo> contentExamGroups) {
        this.contentExamGroups = contentExamGroups;
    }

    @SerializedName("Content")
    @Expose
    private ArrayList<ExamGroupInfo> contentExamGroups = null;

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