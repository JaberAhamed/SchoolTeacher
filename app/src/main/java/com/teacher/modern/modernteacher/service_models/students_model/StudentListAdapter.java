package com.teacher.modern.modernteacher.service_models.students_model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teacher.modern.modernteacher.R;

import java.util.ArrayList;


public class StudentListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    ArrayList<StudentInfo> studentInfoList;

    public StudentListAdapter(Activity activity, ArrayList<StudentInfo> studentInfoList) {
        this.activity = activity;
        this.studentInfoList = studentInfoList;
    }

    public static class AttendanceHolder{

        public TextView student_name_tv;
        public TextView student_roll_tv;
        public ImageView attendance_image;
        public TextView studentId_tv;

    }

    @Override
    public int getCount() {
        return studentInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        AttendanceHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            vi = inflater.inflate(R.layout.student_list, null);
            holder=new AttendanceHolder();

            holder.student_name_tv = vi.findViewById(R.id.student_name_tv);
            holder.student_roll_tv =  vi.findViewById(R.id.student_roll_tv);

            holder.attendance_image = vi.findViewById(R.id.attendance_image);
            holder.studentId_tv = vi.findViewById(R.id.studentId_tv);


            vi.setTag(holder);
        }
        else {
            holder=(AttendanceHolder) vi.getTag();
            vi = convertView;
        }

         holder.student_name_tv.setText(studentInfoList.get(position).getFullName());
         holder.studentId_tv.setText(studentInfoList.get(position).getSubUserId());


        return vi;
    }


}
