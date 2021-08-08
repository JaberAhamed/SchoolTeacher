package com.teacher.modern.modernteacher.service_models.students_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentInfo {
    @SerializedName("SubUserId")
    @Expose
    private String subUserId;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("BirthDate")
    @Expose
    private String birthDate;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("SubUserStatus")
    @Expose
    private String subUserStatus;

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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSubUserStatus() {
        return subUserStatus;
    }

    public void setSubUserStatus(String subUserStatus) {
        this.subUserStatus = subUserStatus;
    }

}
