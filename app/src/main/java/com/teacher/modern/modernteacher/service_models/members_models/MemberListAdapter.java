package com.teacher.modern.modernteacher.service_models.members_models;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.service_models.security_models.UserSession;

import java.util.ArrayList;


public class MemberListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;

    ArrayList<MemberInfo> memberList;



    public MemberListAdapter(Activity activity, ArrayList<MemberInfo> memberList) {
        this.activity = activity;
        this.memberList = memberList;
    }

    public static class NoticeHolder{

        public TextView member_name;
        public TextView gurdian_name;
        public TextView member_relation;
        public TextView member_phone;
        public TextView member_id;

    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public Object getItem(int position) {
        return memberList.get(position);
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
            vi = inflater.inflate(R.layout.member_list, null);
            holder=new NoticeHolder();

            holder.member_name = (TextView) vi.findViewById(R.id.member_name);
            holder.gurdian_name = (TextView) vi.findViewById(R.id.gurdian_name);

           // holder.member_relation = (TextView)vi.findViewById(R.id.member_relation);
            holder.member_phone = (TextView)vi.findViewById(R.id.member_phone);
            holder.member_id = (TextView)vi.findViewById(R.id.member_id);


            vi.setTag(holder);
        }
        else {
            holder=(NoticeHolder) vi.getTag();
            vi = convertView;
        }

        if(memberList.get(position).getUserId().trim().equals(UserSession.UserId))
        {
            vi.findViewById(R.id.memberlist_layout).setVisibility(View.GONE);

        }
        else{
            vi.findViewById(R.id.memberlist_layout).setVisibility(View.VISIBLE);

            holder.member_name.setText(memberList.get(position).getFullName());
            holder.gurdian_name.setText(memberList.get(position).getFirstName() +" "+memberList.get(position).getLastName() +" "+memberList.get(position).getNickName());
            holder.member_relation.setText(memberList.get(position).getUserRelation());
            holder.member_phone.setText(memberList.get(position).getPhone());
            holder.member_id.setText(memberList.get(position).getUserId());
        }




        return vi;
    }


}
