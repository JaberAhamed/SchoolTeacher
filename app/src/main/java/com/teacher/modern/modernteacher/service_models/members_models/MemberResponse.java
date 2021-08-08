package com.teacher.modern.modernteacher.service_models.members_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MemberResponse {

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
    private ArrayList<MemberInfo> contentMemberInfo = null;

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


    public ArrayList<MemberInfo> getContentMemberInfo() {
        return contentMemberInfo;
    }

    public void setContentMemberInfo(ArrayList<MemberInfo> contentMemberInfo) {
        this.contentMemberInfo = contentMemberInfo;
    }

}