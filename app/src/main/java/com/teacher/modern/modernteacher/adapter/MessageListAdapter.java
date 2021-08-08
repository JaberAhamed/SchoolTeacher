package com.teacher.modern.modernteacher.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.database.MyDatabase;
import com.teacher.modern.modernteacher.models.Message;
import com.teacher.modern.modernteacher.service_models.chat_models.ChatListAdapter;

import java.util.List;

public class MessageListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    int count =0;
    int count2 =0;
    private MyDatabase myDatabase;
    private long teacherUID;

    List<Message> chatList;



    public MessageListAdapter(Activity activity, List<Message> chatList) {
        this.activity = activity;
        this.chatList = chatList;
        myDatabase = new MyDatabase(activity);
        Cursor cursor =  myDatabase.getAllData();
        while (cursor.moveToNext()){
            teacherUID = cursor.getLong(2);
        }
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
        final ChatListAdapter.ChatHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            vi = inflater.inflate(R.layout.chat_list, null);
            holder=new ChatListAdapter.ChatHolder();

            holder.message_from =   vi.findViewById(R.id.message_from);
            holder.from_msg_posted_on =  vi.findViewById(R.id.from_msg_posted_on);
            holder.my_message =  vi.findViewById(R.id.my_message);
            holder.my_msg_posted_on =  vi.findViewById(R.id.my_msg_posted_on);



            vi.setTag(holder);
        }
        else {
            holder=(ChatListAdapter.ChatHolder) vi.getTag();
            vi = convertView;
        }



        if(chatList.get(position).SenderId== teacherUID)
        {
            vi.findViewById(R.id.from_message_layout).setVisibility(View.GONE);
            vi.findViewById(R.id.my_message_layout).setVisibility(View.VISIBLE);
            holder.my_message.setText(chatList.get(position).Message1);
            String[] parts =chatList.get(position).Date.split("T");
            String[] datess = parts[0].split("-");
            String[] time = parts[1].split(":");
            holder.my_msg_posted_on.setText(datess[2]+"-"+datess[1]+"-"+datess[0] + " AT " + time[0]+":"+time[1]);
        }
        else{
            vi.findViewById(R.id.from_message_layout).setVisibility(View.VISIBLE);
            vi.findViewById(R.id.my_message_layout).setVisibility(View.GONE);
            holder.message_from.setText(chatList.get(position).Message1);
            String[] parts =chatList.get(position).Date.split("T");
            String[] datess = parts[0].split("-");
            String[] time = parts[1].split(":");
            holder.from_msg_posted_on.setText(datess[2]+"-"+datess[1]+"-"+datess[0] + " AT " + time[0]+":"+time[1]);

        }
        vi.findViewById(R.id.my_message_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count==0){
                    holder.my_msg_posted_on.setVisibility(View.VISIBLE);
                    count=1;
                }
                else {
                    holder.my_msg_posted_on.setVisibility(View.GONE);
                    count =0;

                }

            }
        });

        vi.findViewById(R.id.from_message_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count2 ==0){
                    holder.from_msg_posted_on.setVisibility(View.VISIBLE);
                    count2 = 1;
                }
                else {
                    holder.from_msg_posted_on.setVisibility(View.GONE);
                    count2 = 0;
                }
            }
        });
        return vi;
    }


}
