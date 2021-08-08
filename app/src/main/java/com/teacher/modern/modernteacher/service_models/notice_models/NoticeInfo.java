package com.teacher.modern.modernteacher.service_models.notice_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeInfo {



    @SerializedName("NoticeId")
    @Expose
    private String noticeId;

    public NoticeInfo(String noticeId, String postedBy,String groupId, String noticeTitle, String noticeDetails) {
        this.noticeId = noticeId;
        this.postedBy = postedBy;
        this.groupId = groupId;
        this.noticeTitle = noticeTitle;
        this.noticeDetails = noticeDetails;
    }

    @SerializedName("PostedBy")
    @Expose
    private String postedBy;
    @SerializedName("GroupId")
    @Expose
    private String groupId;
    @SerializedName("NoticeTitle")
    @Expose
    private String noticeTitle;
    @SerializedName("NoticeDetails")
    @Expose
    private String noticeDetails;

    @SerializedName("PostedOn")
    @Expose
    private String postedOn;
    @SerializedName("UpdatedOn")
    @Expose
    private String updatedOn;
    @SerializedName("NoticeStatus")
    @Expose
    private String noticeStatus;
    @SerializedName("Remarks")
    @Expose
    private String remarks;

    public NoticeInfo(String noticeId, String postedBy, String groupId, String noticeTitle, String noticeDetails, String postedOn, String updatedOn, String noticeStatus, String remarks) {
        this.noticeId = noticeId;
        this.postedBy = postedBy;
        this.groupId = groupId;
        this.noticeTitle = noticeTitle;
        this.noticeDetails = noticeDetails;
        this.postedOn = postedOn;
        this.updatedOn = updatedOn;
        this.noticeStatus = noticeStatus;
        this.remarks = remarks;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeDetails() {
        return noticeDetails;
    }

    public void setNoticeDetails(String noticeDetails) {
        this.noticeDetails = noticeDetails;
    }

    public String getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(String postedOn) {
        this.postedOn = postedOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}