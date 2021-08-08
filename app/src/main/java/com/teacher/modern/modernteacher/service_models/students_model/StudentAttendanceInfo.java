package com.teacher.modern.modernteacher.service_models.students_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentAttendanceInfo {
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
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("AttendanceId")
    @Expose
    private String attendanceId;


    @SerializedName("AttendanceDate")
    @Expose
    private String attendanceDate;
    @SerializedName("PresentYN")
    @Expose
    private String presentYN;
    @SerializedName("UpdateTime")
    @Expose
    private String updateTime;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }
    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getPresentYN() {
        return presentYN;
    }

    public void setPresentYN(String presentYN) {
        this.presentYN = presentYN;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


}
