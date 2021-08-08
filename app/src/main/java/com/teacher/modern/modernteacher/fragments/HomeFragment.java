package com.teacher.modern.modernteacher.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.activities.LoginActivity;
import com.teacher.modern.modernteacher.activities.MainActivity;
import com.teacher.modern.modernteacher.activities.NewLoginActivity;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;
import com.teacher.modern.modernteacher.database.MyDatabase;
import com.teacher.modern.modernteacher.models.Subject;
import com.teacher.modern.modernteacher.models.TeacherSession;
import com.teacher.modern.modernteacher.models.viewmodels.ClassSectionIdsVM;
import com.teacher.modern.modernteacher.models.viewmodels.ClassSectionNamesVM;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;
    View rootView;
    private ApiInterface apiInterface;
    ImageView new_notification_notice;

    LinearLayout attendance_layout,members_layout,all_notice_layout,statistics_layout;
    MyDatabase myDatabase;
    private long teacherUID;
    ProgressDialog pd;
    String email;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.home);
        apiInterface = ServiceGenerator.createService(ApiInterface.class);
        pd = new ProgressDialog(getActivity());

        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        TextView textView = rootView.findViewById(R.id.banner_image_title);
        textView.setSelected(true);
        textView.setSingleLine(true);



        new_notification_notice = rootView.findViewById(R.id.all_notice_image1);

       /* if(!UserSession.UserLoggedIn.equals("Y"))
        {
            GoToLoginFragment();
        }*/
        myDatabase = new MyDatabase(getActivity());
        Cursor cursor =  myDatabase.getAllData();
        while (cursor.moveToNext()){
            teacherUID = cursor.getLong(2);
            email= cursor.getString(0);
        }


        attendance_layout= rootView.findViewById(R.id.attendance_layout);
        attendance_layout.setOnClickListener(this);
        members_layout= rootView.findViewById(R.id.members_layout);
        members_layout.setOnClickListener(this);
        all_notice_layout= rootView.findViewById(R.id.all_notice_layout);
        all_notice_layout.setOnClickListener(this);
        statistics_layout= rootView.findViewById(R.id.exams_layout);
        statistics_layout.setOnClickListener(this);

        /*Call<String> getLastUpdatedNoticeTim = apiInterface.getLastUpdatedNoticeTime("Notice/LastUpdatedNoticeTime?uid="+TeacherSession.UId);
        getLastUpdatedNoticeTim.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()!=null){
                    MyDatabase myDatabase = new MyDatabase(getActivity());
                    if(!myDatabase.getLastNoticeSeen().equals("Not found")){
                        if(!myDatabase.getLastNoticeSeen().equals(response.body())){
                            new_notification_notice.setVisibility(View.VISIBLE);
                            //Toast.makeText(getActivity(),"Not Same ..",Toast.LENGTH_LONG).show();
                            //Toast.makeText(getActivity(),myDatabase.getLastNoticeSeen()+" - "+response.body(),Toast.LENGTH_LONG).show();

                        }
                        else{
                            //Toast.makeText(getActivity(),"Same ..",Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });*/
        pd.setMessage(getString(R.string.please_wait));
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        Call<List<Subject>> getSubjectsByTeacherId = apiInterface.getSubjectsByTeacherId("Teacher/getSubjectsByTeacherId?uid="+teacherUID);
        getSubjectsByTeacherId.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                if(response.body()!=null && response.body().size()>0){
                    //int i = response.body().size();
                    TeacherSession.subjects = response.body();

                    List<Integer> classIds = new ArrayList<Integer>();
                    List<Integer> sectionIds= new ArrayList<Integer>();

                    for(int i=0; i<response.body().size(); i++){
                        classIds.add(response.body().get(i).ClassId);
                        sectionIds.add(response.body().get(i).SectionId);
                    }

                    TeacherSession.classIds = classIds;
                    TeacherSession.sectionIds = sectionIds;
                    ClassSectionIdsVM classSectionIdsVM = new ClassSectionIdsVM(classIds, sectionIds);

                    Call<ClassSectionNamesVM> getClassNamesByClassIds = apiInterface.getClassNamesByClassIds(classSectionIdsVM);
                    getClassNamesByClassIds.enqueue(new Callback<ClassSectionNamesVM>() {
                        @Override
                        public void onResponse(Call<ClassSectionNamesVM> call, Response<ClassSectionNamesVM> response) {
                            if(response.body()!=null){
                                TeacherSession.classNames = response.body().classNames;
                                TeacherSession.sectionNames = response.body().sectionNames;
                                TeacherSession.shifts = response.body().shifts;
                                pd.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ClassSectionNamesVM> call, Throwable t) {

                        }
                    });
                    //Toast.makeText(getActivity(),"---"+i,Toast.LENGTH_LONG).show();
                }
                else {
                    //pd.setMessage("Please Assign Teacher First Then Login Again");
                    pd.dismiss();
                    showDialogForAssignSubject();
                    //Toast.makeText(getActivity(),"Please Assign Teacher First",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                pd.dismiss();
            }
        });

        return rootView;
    }

    private void showDialogForAssignSubject() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_subject_assign, null);
        Button itsOk = mView.findViewById(R.id.itsOkBtnID);
        Button settings = mView.findViewById(R.id.exitBtnID);
        ImageView close = mView.findViewById(R.id.closeID);

        builder.setView(mView);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        itsOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),MainActivity.class));
                getActivity().finish();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabase.deleteGuardian(email);
                Intent intent = new Intent(getActivity(), NewLoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void GoToLoginFragment()
    {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
       if(id==R.id.attendance_layout)
            {
                /*manager = getFragmentManager();
                currentFragment = new AttendanceFragment();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment,currentFragment);
                transaction.addToBackStack("AttendanceFragment");
                transaction.commit();*/

                manager = getFragmentManager();
                currentFragment = new newAttendanceFragment();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment,currentFragment);
                transaction.addToBackStack("newAttendanceFragment");
                transaction.commit();
            }

        else if(id==R.id.all_notice_layout)
               {
                   manager = getFragmentManager();
                   currentFragment = new NoticeListFragment();
                   transaction = manager.beginTransaction();
                   transaction.replace(R.id.main_fragment,currentFragment);
                   transaction.addToBackStack("NoticeListFragment");
                   transaction.commit();
               }
       else if(id==R.id.members_layout)
       {
           manager = getFragmentManager();
           currentFragment = new GuardianFragment();
           transaction = manager.beginTransaction();
           transaction.replace(R.id.main_fragment,currentFragment);
           transaction.addToBackStack("GuardianFragment");
           transaction.commit();
       }
       else if(id==R.id.exams_layout)
       {
           manager = getFragmentManager();
           currentFragment = new newExamFragment();
           transaction = manager.beginTransaction();
           transaction.replace(R.id.main_fragment,currentFragment);
           transaction.addToBackStack("newExamFragment");
           transaction.commit();
       }
    }
}
