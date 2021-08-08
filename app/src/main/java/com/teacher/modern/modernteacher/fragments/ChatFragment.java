package com.teacher.modern.modernteacher.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.adapter.MessageListAdapter;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;
import com.teacher.modern.modernteacher.connectivity.NetworkAvailability;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;
import com.teacher.modern.modernteacher.database.MyDatabase;
import com.teacher.modern.modernteacher.models.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {

    View rootView;
    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;
    private ApiInterface apiInterface;

    private ListView listView;
    private MessageListAdapter adapter;
    private String ReceiverId, ReceiverName;
    TextView reciever_name_tv;
    EditText message_text;
    ImageView send_message_btn;

    String lastMessageTime;

    private ProgressDialog progressDialog;
    MyDatabase myDatabase;
    private long teacherUID;
    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.chat);
        apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Bundle bundle = this.getArguments();
        myDatabase = new MyDatabase(getActivity());

        if(bundle!=null){
            ReceiverId= bundle.getString("member_id");
            ReceiverName = bundle.getString("gurdian_name");
        }
        else {
            manager = getFragmentManager();
            currentFragment = new LoginFragment();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment,currentFragment);
            transaction.addToBackStack("HomeFragment");
            transaction.commit();
        }
        message_text = rootView.findViewById(R.id.message_text);
        send_message_btn = rootView.findViewById(R.id.send_message_btn);
        send_message_btn.setOnClickListener(this);

        reciever_name_tv = rootView.findViewById(R.id.reciever_name_tv);
        reciever_name_tv.setText(ReceiverName);
        myDatabase = new MyDatabase(getActivity());
        Cursor cursor =  myDatabase.getAllData();
        while (cursor.moveToNext()){
            teacherUID = cursor.getLong(2);
        }

        ViewChatList();
        message_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (i2>0){
                    send_message_btn.setVisibility(View.VISIBLE);
                }
                else {
                    send_message_btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Thread t9 = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(2000);
                        if(getActivity()!=null && ReceiverId!=null){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Call<List<Message>> getMessages = apiInterface.getMessages("Message/getMessages?uId1="+teacherUID+" &uId2="+ReceiverId);

                                    getMessages.enqueue(new Callback<List<Message>>() {
                                        @Override
                                        public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                                            if(response.body()!=null){
                                                int i = response.body().size()-1;
                                                String newLastMessageTime = response.body().get(i).Date;
                                                if( lastMessageTime!= null && !lastMessageTime.equals(newLastMessageTime)){
                                                   // Toast.makeText(getActivity(),"Not equal",Toast.LENGTH_LONG).show();
                                                    MediaPlayer mediaPlayer;

                                                    mediaPlayer = MediaPlayer.create(getActivity(), R.raw.notification);

                                                    mediaPlayer.start();


                                                    ViewChatList();
                                                }
                                                else {
                                                    //Toast.makeText(getActivity()," equal",Toast.LENGTH_LONG).show();

                                                }

                                                //Toast.makeText(getActivity(),lastMessageTime + "  " + newLastMessageTime,Toast.LENGTH_LONG).show();

                                            }
                                            else {
                                               // Toast.makeText(getActivity(), " null " ,Toast.LENGTH_LONG).show();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<Message>> call, Throwable t) {
                                           // Toast.makeText(getActivity(), " null " ,Toast.LENGTH_LONG).show();

                                        }
                                    });



                                }
                            });
                        }

                    }
                } catch (InterruptedException e) {
                }
            }
        };

        if(getActivity()==null){
            t9.interrupt();
        }

        t9.start();

        return rootView;
    }

    private void updateChatList(){
        Toast.makeText(getActivity(),"Updated Called",Toast.LENGTH_LONG).show();
        if (NetworkAvailability.isNetworkAvailable(getActivity())){

            listView  = (ListView)rootView.findViewById(R.id.chat_list_view);


            Call<List<Message>> getMessages = apiInterface.getMessages("Message/getMessages?senderId="+teacherUID+" &receiverId="+ReceiverId);

            getMessages.enqueue(new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    if(response.body()!=null){

                        int i = response.body().size() -1;
                        lastMessageTime = response.body().get(i).Date;

                        adapter = new MessageListAdapter(getActivity(),response.body());
                        listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Message>> call, Throwable t) {

                }
            });

           /* ChatParameters chatParameters = new ChatParameters(UserSession.UserId, ReceiverId,"");

            Call<ChatResponse> commonResponseCall = apiInterface.getAllChat(chatParameters);

            commonResponseCall.enqueue(new Callback<ChatResponse>() {
                @Override
                public void onResponse(Call<ChatResponse> call, @NonNull Response<ChatResponse> response) {
                    try {

                        if (response.body()!=null){
                            if (response.body().getMessageCode().equals("Y")){

                                Log.d("TestN", "002");

                                //ArrayList<ChatInfo> chatInfos = response.body().getChatInfo();
                                //Collections.reverse(chatInfos);

                                int i = response.body().getChatInfo().size() -1;
                                lastMessageTime = response.body().getChatInfo().get(i).getMessageTime();

                                adapter = new ChatListAdapter(getActivity(), response.body().getChatInfo());
                                listView.setAdapter(adapter);
                                Log.d("TestN", "003");

                            } else {
                                Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Unable to fetch data, try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ChatResponse> call, Throwable t) {

                    Toast.makeText(getActivity(),"Failure, Unable to fetch data, try again.", Toast.LENGTH_SHORT).show();

                }
            });*/
        }
        else {
            Toast.makeText(getActivity(),"No internet connection.", Toast.LENGTH_SHORT).show();

        }
    }

    private void ViewChatList()
    {
        if (NetworkAvailability.isNetworkAvailable(getActivity())){

            listView  = (ListView)rootView.findViewById(R.id.chat_list_view);

            Call<List<Message>> getMessages = apiInterface.getMessages("Message/getMessages?uId1="+teacherUID+" &uId2="+ReceiverId);

            getMessages.enqueue(new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    if(response.body()!=null){
                        int i = response.body().size() -1;
                        lastMessageTime = response.body().get(i).Date;

                        adapter = new MessageListAdapter(getActivity(),response.body());
                        listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Message>> call, Throwable t) {

                }
            });
        }
        else {
            Toast.makeText(getActivity(),"No internet connection.", Toast.LENGTH_SHORT).show();

        }

    }

    private void PostMessage()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        message_text = rootView.findViewById(R.id.message_text);

        if (NetworkAvailability.isNetworkAvailable(getActivity())){
            progressDialog.show();

            Message message = new Message(teacherUID,Long.parseLong(ReceiverId),message_text.getText().toString(),"");

            Call<Boolean> postMessage = apiInterface.postMessage(message);

            postMessage.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.body()==true){
                        progressDialog.hide();
                        message_text.setText("");
                        ViewChatList();
                    }
                    else {
                        progressDialog.hide();
                        Toast.makeText(getActivity(),R.string.something_went,Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        }
        else {
            Toast.makeText(getActivity(),R.string.no_internet, Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.send_message_btn)
        {
            if (message_text.getText().toString().equals("")){
                Toast.makeText(getActivity(), "Please type a  message", Toast.LENGTH_SHORT).show();
            }
            else {
                PostMessage();
            }

        }

    }
}
