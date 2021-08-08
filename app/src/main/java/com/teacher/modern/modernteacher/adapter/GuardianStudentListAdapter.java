package com.teacher.modern.modernteacher.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.fragments.ChatFragment;
import com.teacher.modern.modernteacher.models.viewmodels.StudentGuardianVM;

import java.util.List;

public class GuardianStudentListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;

    List<StudentGuardianVM> memberList;



    public GuardianStudentListAdapter(Activity activity, List<StudentGuardianVM> memberList) {
        this.activity = activity;
        this.memberList = memberList;
    }

    public static class GuardianHolder{

        public TextView member_name;
        public TextView gurdian_name;

        public TextView member_phone;
        public TextView member_id;

        public TextView member_roll;
        public TextView member_email;

        public ImageView callImageView;
        public ImageView messageImageView;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        GuardianStudentListAdapter.GuardianHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            vi = inflater.inflate(R.layout.member_list, null);
            holder=new GuardianStudentListAdapter.GuardianHolder();

            holder.member_name = (TextView) vi.findViewById(R.id.member_name);
            holder.gurdian_name = (TextView) vi.findViewById(R.id.gurdian_name);


            holder.member_phone = (TextView)vi.findViewById(R.id.member_phone);
            holder.member_id = (TextView)vi.findViewById(R.id.member_id);
            holder.member_roll = vi.findViewById(R.id.member_roll);
            holder.member_email = vi.findViewById(R.id.member_email);
            holder.callImageView = vi.findViewById(R.id.callImageView);
            holder.messageImageView = vi.findViewById(R.id.messageImageView);


            vi.setTag(holder);
        }
        else {
            holder=(GuardianStudentListAdapter.GuardianHolder) vi.getTag();
            vi = convertView;
        }


            vi.findViewById(R.id.memberlist_layout).setVisibility(View.VISIBLE);

            holder.member_name.setText(memberList.get(position).studentName);
            holder.gurdian_name.setText(memberList.get(position).guardianName);
            //holder.member_relation.setText(memberList.get(position));
            holder.member_phone.setText(memberList.get(position).guardianPhone);
            holder.member_id.setText(memberList.get(position).guardianPhone);
            holder.member_roll.setText(memberList.get(position).studentRoll +" ");
            if (memberList.get(position).guardianEmail.isEmpty()){
                holder.member_email.setText("No e-mail");
            }else {
                holder.member_email.setText(memberList.get(position).guardianEmail);
            }

            holder.callImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "0"+memberList.get(position).guardianPhone, null)));
                }
            });

            holder.messageImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    manager = activity.getFragmentManager();
                    currentFragment = new ChatFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("member_id", memberList.get(position).guardianPhone);
                    bundle.putString("member_name", memberList.get(position).studentName);
                    bundle.putString("gurdian_name", memberList.get(position).guardianName);


                    currentFragment.setArguments(bundle);
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.main_fragment,currentFragment);
                    transaction.addToBackStack("ChatFragment");
                    transaction.commit();
                }
            });

        return vi;
    }

}
