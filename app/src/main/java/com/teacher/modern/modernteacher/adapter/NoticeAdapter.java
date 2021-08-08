package com.teacher.modern.modernteacher.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.models.Notice;
import com.teacher.modern.modernteacher.models.TeacherSession;
import com.teacher.modern.modernteacher.service_models.notice_models.NoticeListAdapter;

import java.util.List;

public class NoticeAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;

    List<Notice> noticeList;



    public NoticeAdapter(Activity activity, List<Notice> noticeList) {
        this.activity = activity;
        this.noticeList = noticeList;
    }

    public static class NoticeHolder{

        public TextView notice_title;
        public TextView notice_description;
        public TextView notice_posted_by;
        public TextView notice_posted_on;
        public TextView notice_subject;

    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        NoticeListAdapter.NoticeHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            vi = inflater.inflate(R.layout.notice_list, null);
            holder=new NoticeListAdapter.NoticeHolder();

            holder.notice_title = (TextView) vi.findViewById(R.id.notice_title);
            holder.notice_description = (TextView) vi.findViewById(R.id.notice_description);

            holder.notice_posted_by = (TextView)vi.findViewById(R.id.notice_posted_by);
            holder.notice_posted_on = (TextView)vi.findViewById(R.id.notice_posted_on);
            holder.notice_subject = (TextView)vi.findViewById(R.id.notice_subject);
            holder.notice_posted_for = vi.findViewById(R.id.notice_posted_for);



            vi.setTag(holder);
        }
        else {
            holder=(NoticeListAdapter.NoticeHolder) vi.getTag();
            vi = convertView;
        }


        holder.notice_title.setText(""+noticeList.get(position).Title);
        //holder.notice_description.setText(""+noticeList.get(position).Message);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.notice_description.setText(noticeList.get(position).Message);
        } else {
            holder.notice_description.setText(noticeList.get(position).Message);
        }

        holder.notice_posted_by.setText(noticeList.get(position).PostedBy +"");

        String[] parts = noticeList.get(position).PostedDate.split("T");
        String[] datess = parts[0].split("-");


        holder.notice_posted_on.setText(datess[2]+"-"+datess[1]+"-"+datess[0]);

        String[] part = noticeList.get(position).PostedForTime.split("T");
        String[] dates = part[0].split("-");


        holder.notice_posted_for.setText(dates[2]+"-"+dates[1]+"-"+dates[0]);
        if(noticeList.get(position).MaskingId!=0){
            String subjectName = "";
            if(TeacherSession.subjects!=null){
                for(int i=0; i<TeacherSession.subjects.size(); i++){
                    if(TeacherSession.subjects.get(i).MaskingSubjectId == noticeList.get(position).MaskingId){
                        subjectName = TeacherSession.subjects.get(i).SubjectName;
                    }
                }
            }

            holder.notice_subject.setText(subjectName);
        }
        else {
            holder.notice_subject.setText("ALL");
        }




        return vi;
    }
}
