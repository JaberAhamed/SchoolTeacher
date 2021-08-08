package com.teacher.modern.modernteacher.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.adapter.NoticeAdapter;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;
import com.teacher.modern.modernteacher.connectivity.NetworkAvailability;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;
import com.teacher.modern.modernteacher.database.MyDatabase;
import com.teacher.modern.modernteacher.models.Notice;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeListFragment extends Fragment {

    View rootView;
    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private ListView listView;
    private NoticeAdapter adapter;
    private Button viewAllBtn;
    private FloatingActionButton postNotice;
    MyDatabase myDatabase;
    long teacherUID;

    private LinearLayout noDataFoundlayout;

    public NoticeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notice_list, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.notices_);
        apiInterface = ServiceGenerator.createService(ApiInterface.class);
        postNotice = rootView.findViewById(R.id.postNoticeId);
        listView  = (ListView)rootView.findViewById(R.id.notice_list_view);
        myDatabase = new MyDatabase(getActivity());
        Cursor cursor =  myDatabase.getAllData();
        while (cursor.moveToNext()){
            teacherUID = cursor.getLong(2);
        }

        ViewNoticeList(0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView notice_title = (TextView) view.findViewById(R.id.notice_title);
                TextView notice_description = (TextView) view.findViewById(R.id.notice_description);

                TextView notice_posted_by = (TextView)view.findViewById(R.id.notice_posted_by);
                TextView notice_posted_on = (TextView)view.findViewById(R.id.notice_posted_on);

                manager = getFragmentManager();
                currentFragment = new NoticeViewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("notice_title", notice_title.getText().toString());
                bundle.putString("notice_description", notice_description.getText().toString());
                bundle.putString("notice_posted_by", notice_posted_by.getText().toString());
                bundle.putString("notice_posted_on", notice_posted_on.getText().toString());

                currentFragment.setArguments(bundle);
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment,currentFragment);
                transaction.addToBackStack("NoticeViewFragment");
                transaction.commit();

            }
        });
        postNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    manager = getFragmentManager();
                    currentFragment = new PostNoticeFragment();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.main_fragment, currentFragment);
                    transaction.addToBackStack("PostNoticeFragment");
                    transaction.commit();



            }
        });


        return rootView;
    }

    private void ViewNoticeList(int all)
    {
        noDataFoundlayout = rootView.findViewById(R.id.noDataFoundLayoutID);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        if (NetworkAvailability.isNetworkAvailable(getActivity())){
            progressDialog.show();


            if(all==0){
                Call<List<Notice>> getNoticesByUserId = apiInterface.getNoticesByUserId("Notice/getNoticesByUserId?uid="+teacherUID);
                getNoticesByUserId.enqueue(new Callback<List<Notice>>() {
                    @Override
                    public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                        if(response.body()!= null && response.body().size()>0){
                            MyDatabase myDatabase = new MyDatabase(getActivity());
                            int i = response.body().size();
                            myDatabase.updateLastNoticeSeen(response.body().get(i-1).PostedDate);

                            progressDialog.dismiss();
                            adapter =new NoticeAdapter(getActivity(),response.body());
                            listView.setAdapter(adapter);
                            listView.setVisibility(View.VISIBLE);
                            noDataFoundlayout.setVisibility(View.GONE);
                        }
                        else{
                            noDataFoundlayout.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                            progressDialog.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Notice>> call, Throwable t) {
                        if(t instanceof SocketTimeoutException){
                            String message = "Socket Time out. Please try again.";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            else if(all==1){
                Call<List<Notice>> getNoticesByUserId = apiInterface.getNoticesByUserId("Notice/getAllNotices");
                getNoticesByUserId.enqueue(new Callback<List<Notice>>() {
                    @Override
                    public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                        if(response.body().size()>0){
                            progressDialog.dismiss();
                            adapter =new NoticeAdapter(getActivity(),response.body());
                            listView.setAdapter(adapter);
                            listView.setVisibility(View.VISIBLE);
                            noDataFoundlayout.setVisibility(View.GONE);
                        }
                        else{

                            noDataFoundlayout.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Notice>> call, Throwable t) {
                        if(t instanceof SocketTimeoutException){
                            String message = "Socket Time out. Please try again.";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }
        else {
            noDataFoundlayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            Toast.makeText(getActivity(),R.string.no_internet, Toast.LENGTH_SHORT).show();

        }

    }





}
