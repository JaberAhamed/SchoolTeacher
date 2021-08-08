package com.teacher.modern.modernteacher.service_models.chat_models;

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


public class ChatListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;

    ArrayList<ChatInfo> chatList;



    public ChatListAdapter(Activity activity, ArrayList<ChatInfo> chatList) {
        this.activity = activity;
        this.chatList = chatList;
    }

    public static class ChatHolder{

        public TextView message_from;
        public TextView from_msg_posted_on;
        public TextView my_message;
        public TextView my_msg_posted_on;

    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ChatHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            vi = inflater.inflate(R.layout.chat_list, null);
            holder=new ChatHolder();

            holder.message_from =   vi.findViewById(R.id.message_from);
            holder.from_msg_posted_on =  vi.findViewById(R.id.from_msg_posted_on);
            holder.my_message =  vi.findViewById(R.id.my_message);
            holder.my_msg_posted_on =  vi.findViewById(R.id.my_msg_posted_on);



            vi.setTag(holder);
        }
        else {
            holder=(ChatHolder) vi.getTag();
            vi = convertView;
        }

        if(chatList.get(position).getSenderId().trim().equals(UserSession.UserId))
        {
               vi.findViewById(R.id.from_message_layout).setVisibility(View.GONE);
               vi.findViewById(R.id.my_message_layout).setVisibility(View.VISIBLE);
            holder.my_message.setText(chatList.get(position).getMessageDetails());
            holder.my_msg_posted_on.setText(chatList.get(position).getMessageTime());
        }
        else{
               vi.findViewById(R.id.from_message_layout).setVisibility(View.VISIBLE);
              vi.findViewById(R.id.my_message_layout).setVisibility(View.GONE);
            holder.message_from.setText(chatList.get(position).getMessageDetails());
            holder.from_msg_posted_on.setText(chatList.get(position).getMessageTime());

        }
        return vi;
    }


}
