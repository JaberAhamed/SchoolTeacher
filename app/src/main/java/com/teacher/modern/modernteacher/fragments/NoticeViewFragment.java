package com.teacher.modern.modernteacher.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teacher.modern.modernteacher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeViewFragment extends Fragment {

    View rootView;
    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;



    public NoticeViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notice_view, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.notice_details);
        Bundle bundle = this.getArguments();

        TextView notice_title =  rootView.findViewById(R.id.notice_title);
        TextView notice_description = rootView.findViewById(R.id.notice_description);

        TextView notice_posted_by = rootView.findViewById(R.id.notice_posted_by);
        TextView notice_posted_on = rootView.findViewById(R.id.notice_posted_on);


        if(bundle!=null){

            notice_title.setText(bundle.getString("notice_title"));
            notice_description.setText(bundle.getString("notice_description"));
            notice_posted_by.setText(bundle.getString("notice_posted_by"));
            notice_posted_on.setText(bundle.getString("notice_posted_on"));
        }
        else {
            manager = getFragmentManager();
            currentFragment = new LoginFragment();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment,currentFragment);
            transaction.addToBackStack("HomeFragment");
            transaction.commit();
        }





        return rootView;
    }


}
