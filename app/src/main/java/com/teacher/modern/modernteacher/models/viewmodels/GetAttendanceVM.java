package com.teacher.modern.modernteacher.models.viewmodels;

public class GetAttendanceVM {

    public int maskingId ;

    public int classId ;
    public int sectionId ;

    public int day ;
    public int year ;
    public int month ;

    public GetAttendanceVM(int maskingId,  int classId, int sectionId, int day, int year, int month) {
        this.maskingId = maskingId;

        this.classId = classId;
        this.sectionId = sectionId;
        this.day = day;
        this.year = year;
        this.month = month;
    }
}
