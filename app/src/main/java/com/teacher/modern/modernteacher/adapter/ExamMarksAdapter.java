package com.teacher.modern.modernteacher.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.models.viewmodels.InputExamMarksVM;

import java.util.List;

public class ExamMarksAdapter  extends BaseAdapter {
    private Activity context;
    private LayoutInflater layoutInflater;

    List<InputExamMarksVM> inputExamMarksVMS;

    int viewOrEdit; // 1 = view , 0 = edit

    static int k;
    double x =0;

    public ExamMarksAdapter(Activity context,List<InputExamMarksVM> inputExamMarksVMS, int viewOrEdit){
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if(view==null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  layoutInflater.inflate(R.layout.new_exam_marks_input_details,viewGroup,false);
        }

        TextView student_name = view.findViewById(R.id.student_name);
        TextView student_roll = view.findViewById(R.id.student_roll);
        EditText obtained_marks = view.findViewById(R.id.obtained_marks);
        TextView mark_text = view.findViewById(R.id.mark_text);
        TextView roll_text = view.findViewById(R.id.roll_text);
        TextView name_text = view.findViewById(R.id.name_text);

        student_name.setText(inputExamMarksVMS.get(i).StudentName);
        student_roll.setText(inputExamMarksVMS.get(i).StudentRoll+"");
        obtained_marks.setText(String.valueOf(inputExamMarksVMS.get(i).obtainedMarks));

        if(k<inputExamMarksVMS.size()){
            x = inputExamMarksVMS.get(k).obtainedMarks;
        }

        if(inputExamMarksVMS.get(i).StudentRoll==0){
            student_name.setVisibility(View.GONE);
            student_roll.setVisibility(View.GONE);
            obtained_marks.setVisibility(View.GONE);
            mark_text.setVisibility(View.GONE);
            roll_text.setVisibility(View.GONE);
            name_text.setVisibility(View.GONE);
        }


        if(viewOrEdit==1){ // View mode

            obtained_marks.setText(x+"");


            obtained_marks.setKeyListener(null);
            obtained_marks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Please goto edit mode to edit marks..",Toast.LENGTH_LONG).show();
                }
            });
        }




        obtained_marks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int j, int i1, int i2) {
                inputExamMarksVMS.get(i).obtainedMarks = -99;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("")){
                    inputExamMarksVMS.get(i).obtainedMarks = Double.parseDouble(editable.toString());
                }

            }
        });

        k++;
        return view;
    }
}