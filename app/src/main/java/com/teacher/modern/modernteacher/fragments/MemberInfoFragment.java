package com.teacher.modern.modernteacher.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teacher.modern.modernteacher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberInfoFragment extends Fragment implements View.OnClickListener {

    View rootView;
    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;

    public MemberInfoFragment() {
        // Required empty public constructor
    }

    ImageView callButton;
    ImageView start_message_btn;
    TextView member_id,member_name;
    String phoneNumberToCall="01554035483";


    public TextView gurdian_name;
    public TextView member_relation;
    public TextView member_phone;


    public TextView member_roll;
    public TextView member_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_member_info, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Guardian Details Info");
        Bundle bundle = this.getArguments();

        member_id =  rootView.findViewById(R.id.member_id);
        member_name =  rootView.findViewById(R.id.member_name);
        gurdian_name =  rootView.findViewById(R.id.gurdian_name);
        member_phone = rootView.findViewById(R.id.member_phone);
        member_roll = rootView.findViewById(R.id.member_roll);
        member_email = rootView.findViewById(R.id.member_email);


        if(bundle!=null){

            member_id.setText(bundle.getString("member_id"));
            gurdian_name.setText(bundle.getString("gurdian_name"));
            member_name.setText(bundle.getString("member_name"));
            member_email.setText(bundle.getString("member_email"));
            member_phone.setText("0"+bundle.getString("member_phone"));
            member_roll.setText(bundle.getString("member_roll"));

            phoneNumberToCall ="0"+ bundle.getString("member_phone");
        }
        else {
            manager = getFragmentManager();
            currentFragment = new LoginFragment();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment,currentFragment);
            transaction.addToBackStack("HomeFragment");
            transaction.commit();
        }


        callButton = rootView.findViewById(R.id.CallButton);
        callButton.setOnClickListener(this);

        start_message_btn = rootView.findViewById(R.id.start_message_btn);
        start_message_btn.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.start_message_btn){

            manager = getFragmentManager();
            currentFragment = new ChatFragment();
            Bundle bundle = new Bundle();
            bundle.putString("member_id", member_id.getText().toString());
            bundle.putString("member_name", member_name.getText().toString());
            bundle.putString("gurdian_name", gurdian_name.getText().toString());


            currentFragment.setArguments(bundle);
            transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment,currentFragment);
            transaction.addToBackStack("ChatFragment");
            transaction.commit();

        }
        else if(id==R.id.CallButton){
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumberToCall, null)));
        }
    }






}
