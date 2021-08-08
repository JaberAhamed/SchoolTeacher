package com.teacher.modern.modernteacher.models;

public class Notice {

    public long Id ;
    public long PostedBy ;
    public int MaskingId ;
    public String Message ;
    public String Title ;
    public String PostedDate ;
    public String UpdatedDate ;
    public String PostedForTime ;

    public Notice(long id, long postedBy, int maskingId, String message, String title, String postedDate, String updatedDate, String postedForTime) {
        Id = id;
        PostedBy = postedBy;
        MaskingId = maskingId;
        Message = message;
        Title = title;
        PostedDate = postedDate;
        UpdatedDate = updatedDate;
        PostedForTime = postedForTime;
    }
}
