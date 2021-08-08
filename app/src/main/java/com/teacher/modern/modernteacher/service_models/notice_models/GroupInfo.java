package com.teacher.modern.modernteacher.service_models.notice_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupInfo {

    @SerializedName("GroupId")
    @Expose
    private String groupId;
    @SerializedName("GroupTitle")
    @Expose
    private String groupTitle;
    @SerializedName("GroupDetails")
    @Expose
    private String groupDetails;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getGroupDetails() {
        return groupDetails;
    }

    public void setGroupDetails(String groupDetails) {
        this.groupDetails = groupDetails;
    }

}