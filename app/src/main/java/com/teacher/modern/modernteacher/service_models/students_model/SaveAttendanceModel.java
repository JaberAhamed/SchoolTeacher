package com.teacher.modern.modernteacher.service_models.students_model;

import com.teacher.modern.modernteacher.service_models.basic_models.GroupBasic;

import java.util.ArrayList;

public class SaveAttendanceModel {
    public SaveAttendanceModel(ArrayList<StudentAttendanceInfo> studInfoList, GroupBasic groupBasic) {
        this.studInfoList = studInfoList;
        this.groupBasic = groupBasic;
    }

    public ArrayList<StudentAttendanceInfo> studInfoList;
    public GroupBasic groupBasic;

}
