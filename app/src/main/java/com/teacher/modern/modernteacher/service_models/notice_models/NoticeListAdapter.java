package com.teacher.modern.modernteacher.service_models.notice_models;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.teacher.modern.modernteacher.R;

import java.util.ArrayList;


public class NoticeListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;

    ArrayList<NoticeInfo> noticeList;



    public NoticeListAdapter(Activity activity, ArrayList<NoticeInfo> noticeList) {
        this.activity = activity;
        this.noticeList = noticeList;
    }

    public static class NoticeHolder{

        public TextView notice_title;
        public TextView notice_description;
        public TextView notice_posted_by;
        public TextView notice_posted_on;
        public TextView notice_posted_for;

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
        NoticeHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            vi = inflater.inflate(R.layout.notice_list, null);
            holder=new NoticeHolder();

            holder.notice_title = (TextView) vi.findViewById(R.id.notice_title);
            holder.notice_description = (TextView) vi.findViewById(R.id.notice_description);

            holder.notice_posted_by = (TextView)vi.findViewById(R.id.notice_posted_by);
            holder.notice_posted_on = (TextView)vi.findViewById(R.id.notice_posted_on);
            holder.notice_subject = (TextView)vi.findViewById(R.id.notice_subject);

            vi.setTag(holder);
        }
        else {
            holder=(NoticeHolder) vi.getTag();
            vi = convertView;
        }


         holder.notice_title.setText(""+noticeList.get(position).getNoticeTitle());
         holder.notice_description.setText(""+noticeList.get(position).getNoticeDetails());
         holder.notice_posted_by.setText(noticeList.get(position).getPostedBy());
         holder.notice_posted_on.setText(noticeList.get(position).getPostedOn());
        // holder.notice_subject.setText(noticeList.get(position).);


        return vi;
    }


}
