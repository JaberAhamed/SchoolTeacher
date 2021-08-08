package com.teacher.modern.modernteacher.service_models.exam_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ExamInfoResponse {
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
    private ArrayList<ExamInfo> contentExamInfo = null;

    public ArrayList<ExamInfo> getContentExamInfo() {
        return contentExamInfo;
    }

    public void setContentExamInfo(ArrayList<ExamInfo> contentExamInfo) {
        this.contentExamInfo = contentExamInfo;
    }

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
