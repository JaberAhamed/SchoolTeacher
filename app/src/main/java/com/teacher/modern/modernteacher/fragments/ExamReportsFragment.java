package com.teacher.modern.modernteacher.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamReportsFragment extends Fragment {
    View rootView;
    android.app.Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;
    private ApiInterface apiInterface;

    private ListView listView;
    private ProgressDialog progressDialog;

    public ExamReportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_exam_reports, container, false);



        return rootView;
    }

}
