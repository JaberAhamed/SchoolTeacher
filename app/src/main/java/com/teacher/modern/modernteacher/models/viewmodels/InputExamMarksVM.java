package com.teacher.modern.modernteacher.models.viewmodels;

public class InputExamMarksVM {

    public String StudentName ;
    public int StudentRoll ;
    public double obtainedMarks ;
    public long StudentId ;
    public double totalMarks ;
    public String ExamDate ;
    public int maskingId;
    public int ExamTypeId;

    public InputExamMarksVM(int studentRoll) {
        StudentRoll = studentRoll;
    }
}
