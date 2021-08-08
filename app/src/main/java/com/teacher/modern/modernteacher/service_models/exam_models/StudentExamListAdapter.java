package com.teacher.modern.modernteacher.service_models.exam_models;

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

import com.teacher.modern.modernteacher.R;

import java.util.ArrayList;


public class StudentExamListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;

    ArrayList<ExamInfo> examInfoList;


    public StudentExamListAdapter(Activity activity, ArrayList<ExamInfo> studentInfoList) {
        this.activity = activity;
        this.examInfoList = studentInfoList;
    }

    public static class ExamHolder{

        public TextView student_name_tv;
        public TextView student_roll_tv;
        public EditText studentObtainedMarks;
        public EditText studentRemarks;
        public TextView studentId_tv;

    }

    @Override
    public int getCount() {
        return examInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return examInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        final ExamHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            vi = inflater.inflate(R.layout.student_exam_list, null);
            holder=new ExamHolder();

            holder.student_name_tv = vi.findViewById(R.id.student_name_extv);
            holder.student_roll_tv =  vi.findViewById(R.id.student_roll_extv);

            holder.studentObtainedMarks = vi.findViewById(R.id.studentObtainedMarks);
            holder.studentRemarks = vi.findViewById(R.id.studentRemarks);
            holder.studentId_tv = vi.findViewById(R.id.studentId_extv);



            vi.setTag(holder);
        }
        else {
            holder=(ExamHolder) vi.getTag();
            vi = convertView;
        }

         holder.student_name_tv.setText(examInfoList.get(position).getFullName());
         holder.student_roll_tv.setText(examInfoList.get(position).getIdentityNo());

         holder.studentId_tv.setText(examInfoList.get(position).getSubUserId());
         holder.studentObtainedMarks.setText(examInfoList.get(position).getStudentMarks());
         holder.studentRemarks.setText(examInfoList.get(position).getRemarks());
         holder.studentId_tv.setText(examInfoList.get(position).getSubUserId());

        holder.studentObtainedMarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                examInfoList.get(position).setStudentMarks(holder.studentObtainedMarks.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.studentRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                examInfoList.get(position).setRemarks(holder.studentRemarks.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        return vi;
    }


}
