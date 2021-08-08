package com.teacher.modern.modernteacher.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.models.viewmodels.InputExamMarksVM;

import java.util.List;

public class ExamMarksAdapterView extends BaseAdapter {
    private Activity context;
    private LayoutInflater layoutInflater;

    List<InputExamMarksVM> inputExamMarksVMS;

    int viewOrEdit; // 1 = view , 0 = edit

    static int k;
    double x =0;

    public ExamMarksAdapterView(Activity context, List<InputExamMarksVM> inputExamMarksVMS, int viewOrEdit){
        this.inputExamMarksVMS =inputExamMarksVMS;
        this.context = context;
        this.viewOrEdit =viewOrEdit;
        k=0;
    }

    @Override
    public int getCount() {
        if(inputExamMarksVMS!=null){
            return  inputExamMarksVMS.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return inputExamMarksVMS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView( int i, View view, ViewGroup viewGroup) {

        if(view==null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  layoutInflater.inflate(R.layout.model_view_exam_marks,viewGroup,false);
        }

        TextView student_name = view.findViewById(R.id.student_name);
        TextView student_roll = view.findViewById(R.id.student_roll);
        TextView obtainMarks= view.findViewById(R.id.obtainMarksTV);

        student_name.setText(inputExamMarksVMS.get(i).StudentName);
        student_roll.setText(inputExamMarksVMS.get(i).StudentRoll+"");
        obtainMarks.setText(inputExamMarksVMS.get(i).obtainedMarks+ " out of "+ inputExamMarksVMS.get(i).totalMarks);

        if(k<inputExamMarksVMS.size()){
            x = inputExamMarksVMS.get(k).obtainedMarks;
        }


        if(viewOrEdit==1){ // View mode


        }


        k++;
        return view;
    }
}