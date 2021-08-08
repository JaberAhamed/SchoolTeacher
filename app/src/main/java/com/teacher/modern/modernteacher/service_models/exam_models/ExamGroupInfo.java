package com.teacher.modern.modernteacher.service_models.exam_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamGroupInfo {
    @SerializedName("ExamGroupId")
    @Expose
    private String examGroupId;
    @SerializedName("GroupId")
    @Expose
    private String groupId;
    @SerializedName("ExamTitle")
    @Expose
    private String examTitle;
    @SerializedName("ExamMarks")
    @Expose
    private String examMarks;
    @SerializedName("ExamDetails")
    @Expose
    private String examDetails;
    @SerializedName("ExamDate")
    @Expose
    private String examDate;
    @SerializedName("ExamStatus")
    @Expose
    private String examStatus;

    public String getExamGroupId() {
        return examGroupId;
    }

    public void setExamGroupId(String examGroupId) {
        this.examGroupId = examGroupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getExamMarks() {
        return examMarks;
    }

    public void setExamMarks(String examMarks) {
        this.examMarks = examMarks;
    }

    public String getExamDetails() {
        return examDetails;
    }

    public void setExamDetails(String examDetails) {
        this.examDetails = examDetails;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }

}
