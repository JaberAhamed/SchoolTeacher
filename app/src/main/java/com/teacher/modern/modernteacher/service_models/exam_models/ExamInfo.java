package com.teacher.modern.modernteacher.service_models.exam_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamInfo {
    public ExamInfo(String subUserId, String fullName, String identityNo, String gender, String examId, String examGroupId, String studentGroupId, String teacherId, String examDate, String examMarks, String studentMarks, String remarks, String examStatus) {
        this.subUserId = subUserId;
        this.fullName = fullName;
        this.identityNo = identityNo;
        this.gender = gender;
        this.examId = examId;
        this.examGroupId = examGroupId;
        this.studentGroupId = studentGroupId;
        this.teacherId = teacherId;
        this.examDate = examDate;
        this.examMarks = examMarks;
        this.studentMarks = studentMarks;
        this.remarks = remarks;
        this.examStatus = examStatus;
    }

    @SerializedName("SubUserId")
    @Expose
    private String subUserId;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("IdentityNo")
    @Expose
    private String identityNo;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("ExamId")
    @Expose
    private String examId;
    @SerializedName("ExamGroupId")
    @Expose
    private String examGroupId;
    @SerializedName("StudentGroupId")
    @Expose
    private String studentGroupId;
    @SerializedName("TeacherId")
    @Expose
    private String teacherId;
    @SerializedName("ExamDate")
    @Expose
    private String examDate;
    @SerializedName("ExamMarks")
    @Expose
    private String examMarks;
    @SerializedName("StudentMarks")
    @Expose
    private String studentMarks;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("ExamStatus")
    @Expose
    private String examStatus;

    public String getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(String subUserId) {
        this.subUserId = subUserId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamGroupId() {
        return examGroupId;
    }

    public void setExamGroupId(String examGroupId) {
        this.examGroupId = examGroupId;
    }

    public String getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(String studentGroupId) {
        this.studentGroupId = studentGroupId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamMarks() {
        return examMarks;
    }

    public void setExamMarks(String examMarks) {
        this.examMarks = examMarks;
    }

    public String getStudentMarks() {
        return studentMarks;
    }

    public void setStudentMarks(String studentMarks) {
        this.studentMarks = studentMarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }

}
