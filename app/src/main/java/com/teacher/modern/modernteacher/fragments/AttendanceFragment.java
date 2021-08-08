package com.teacher.modern.modernteacher.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;
import com.teacher.modern.modernteacher.models.Subject;
import com.teacher.modern.modernteacher.models.TeacherSession;
import com.teacher.modern.modernteacher.service_models.notice_models.GroupInfo;
import com.teacher.modern.modernteacher.service_models.students_model.StudentAttendanceInfo;
import com.teacher.modern.modernteacher.service_models.students_model.StudentAttendanceListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    View rootView;
    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;
    private ApiInterface apiInterface;

    private ListView listView;
    private StudentAttendanceListAdapter adapter;

    Button submitAttendanceButton;
    ImageView attendanceDateImageIcon;
    Dialog dialog;
    Calendar today;
    Calendar attendanceDay;
    long days=0;
    TextView attendanceDateTextView;

    private ProgressDialog progressDialog;

    String groupId="",attendanceDate="";
    ArrayList<StudentAttendanceInfo> studInfoList;

    ArrayList<GroupInfo> groupInfo = new ArrayList<GroupInfo>();
    List<Subject> subjectLists = new ArrayList<Subject>();


    //List<String> groupInfoNames;
    List<String> subjectNames;

    List<String> groupInfoNames;
    private HashMap<Integer, String> groupInfoHashMap;
    private ArrayAdapter<String> groupInfoAdapter;
    Spinner sp;

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_attendance, container, false);
        getActivity().setTitle(R.string.attendance);
        apiInterface = ServiceGenerator.createService(ApiInterface.class);
        attendanceDateTextView = rootView.findViewById(R.id.attendanceDateTextView);
        today = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.yyy));
        attendanceDate = dateFormat.format(today.getTime());
        attendanceDateTextView.setText(attendanceDate);
        groupInfo = new ArrayList<GroupInfo>();
        listView  = rootView.findViewById(R.id.student_list_view);
        subjectLists = TeacherSession.subjects;
        subjectNames = new ArrayList<String>();

        for(int i=0; i<subjectLists.size(); i++){
            subjectNames.add(subjectLists.get(i).SubjectName + getString(R.string._class) + TeacherSession.classNames.get(i) + getString(R.string._sec) + TeacherSession.sectionNames.get(i)+", "+TeacherSession.shifts.get(i)+getString(R.string._shift));

        }


        sp=  rootView.findViewById(R.id.groupInfoAttendenceSpinner);
        //GetGroupInfo();
        subjectLists = TeacherSession.subjects;
        subjectNames = new ArrayList<String>();

        for(int i=0; i<subjectLists.size(); i++){
            subjectNames.add(subjectLists.get(i).SubjectName + getString(R.string._class) + TeacherSession.classNames.get(i) + getString(R.string._sec) + TeacherSession.sectionNames.get(i)+", "+TeacherSession.shifts.get(i)+getString(R.string._shift));

        }
        groupInfoAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subjectNames);
        groupInfoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(groupInfoAdapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        submitAttendanceButton = rootView.findViewById(R.id.submitAttendanceButton);
        submitAttendanceButton.setOnClickListener(this);

        attendanceDateImageIcon = rootView.findViewById(R.id.attendanceDateImageIcon);
        attendanceDateImageIcon.setOnClickListener(this);

        attendanceDateTextView.setOnClickListener(this);

/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              // String beforeName = parent.getAdapter().getItem(position).().toString();
                ImageView attendance_image = view.findViewById(R.id.attendance_image);
                TextView student_attend_status = (TextView) view.findViewById(R.id.student_attend_status);
                if(days<=0&&days>=-2)
                {
                    if(student_attend_status.getText().toString().trim().equals("Y"))
                    {
                        studInfoList.get(position).setAttendanceDate(attendanceDate);
                        studInfoList.get(position).setPresentYN("N");
                        student_attend_status.setText("N");
                        attendance_image.setImageResource(R.drawable.ic_absent_icon);
                    }
                    else{
                        studInfoList.get(position).setAttendanceDate(attendanceDate);
                        studInfoList.get(position).setPresentYN("Y");
                        student_attend_status.setText("Y");
                        attendance_image.setImageResource(R.drawable.ic_present_icon);
                    }
                }



            }
        });*/


        return rootView;
    }


   /* private void ViewStudentList()
    {

        if (NetworkAvailability.isNetworkAvailable(getActivity())){

            listView  = rootView.findViewById(R.id.student_list_view);

            AttendanceGroup attendanceGroup = new AttendanceGroup(groupId, UserSession.UserId,"",attendanceDate);

            Call<StudentAttendanceInfoResponse> commonResponseCall = apiInterface.getStudentAttendanceInfoByGroup(attendanceGroup);

            commonResponseCall.enqueue(new Callback<StudentAttendanceInfoResponse>() {
                @Override
                public void onResponse(Call<StudentAttendanceInfoResponse> call, @NonNull Response<StudentAttendanceInfoResponse> response) {
                    try {

                        if (response.body()!=null){
                            if (response.body().getMessageCode().equals("Y")){


                                studInfoList = response.body().getStudentInfoContent();

                                for (int i = 0; i < studInfoList.size(); i++)
                                {
                                    studInfoList.get(i).setAttendanceDate(attendanceDate);
                                }

                                if(listView.getVisibility()== View.VISIBLE)
                                {
                                    adapter = new StudentAttendanceListAdapter(getActivity(), response.body().getStudentInfoContent());
                                    listView.setAdapter(adapter);
                                }
                                else{
                                    listView.setVisibility(View.VISIBLE);
                                    adapter = new StudentAttendanceListAdapter(getActivity(), response.body().getStudentInfoContent());
                                    listView.setAdapter(adapter);
                                }

                                if(submitAttendanceButton.getVisibility()== View.VISIBLE)
                                {
                                   // ----
                                }
                                else{
                                    submitAttendanceButton.setVisibility(View.VISIBLE);
                                }


                            } else {
                                if(listView.getVisibility()== View.VISIBLE)
                                {
                                    listView.setVisibility(View.GONE);
                                }
                                if(submitAttendanceButton.getVisibility()== View.VISIBLE)
                                {
                                    submitAttendanceButton.setVisibility(View.GONE);
                                }

                                Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if(listView.getVisibility()== View.VISIBLE)
                            {
                                listView.setVisibility(View.GONE);
                            }
                            if(submitAttendanceButton.getVisibility()== View.VISIBLE)
                            {
                                submitAttendanceButton.setVisibility(View.GONE);
                            }

                            Toast.makeText(getActivity(),"Unable to fetch data, try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        if(listView.getVisibility()== View.VISIBLE)
                        {
                            listView.setVisibility(View.GONE);
                        }
                        if(submitAttendanceButton.getVisibility()== View.VISIBLE)
                        {
                            submitAttendanceButton.setVisibility(View.GONE);
                        }

                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<StudentAttendanceInfoResponse> call, Throwable t) {

                    if(listView.getVisibility()== View.VISIBLE)
                    {
                        listView.setVisibility(View.GONE);
                    }
                    if(submitAttendanceButton.getVisibility()== View.VISIBLE)
                    {
                        submitAttendanceButton.setVisibility(View.GONE);
                    }

                    Toast.makeText(getActivity(),"Failure, Unable to fetch data, try again.", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            Toast.makeText(getActivity(),"No internet connection.", Toast.LENGTH_SHORT).show();

        }

    }*/


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id==R.id.submitAttendanceButton)
        {
            if(days<=0&&days>=-2) {
               // SaveAttendance();
            }
            else{
                Toast.makeText(getActivity(),R.string.you_can_not, Toast.LENGTH_LONG).show();
            }
        }
        if(id==R.id.attendanceDateImageIcon || id== R.id.attendanceDateTextView)
        {
            dialog = new Dialog(this.getActivity());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog.setContentView(R.layout.date_picker_layout);
            }
            else
            {
                dialog.setContentView(R.layout.date_picker_layout_under_lolipop);
            }
            dialog.setTitle("Select Attendance Date:");

            TextView date_type_text = (TextView) dialog.findViewById(R.id.date_type_text);
            date_type_text.setText(R.string.attendance_date);

            Button setDateButton = (Button) dialog.findViewById(R.id.cancelContactButton);

            setDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatePicker startDate =  dialog.findViewById(R.id.ac_date_picker);


                    int sDay = startDate.getDayOfMonth();
                    int sMonth = startDate.getMonth();
                    int sYear = startDate.getYear();

                    attendanceDay = Calendar.getInstance();
                    attendanceDay.set(Calendar.DAY_OF_MONTH,sDay);
                    attendanceDay.set(Calendar.MONTH,sMonth); // 0-11 so 1 less
                    attendanceDay.set(Calendar.YEAR, sYear);

                    long difference = attendanceDay.getTimeInMillis() - today.getTimeInMillis();
                    days = difference / (24 * 60 * 60 * 1000);


                if(days>0)
                    {
                        Toast.makeText(getActivity(),"Attendance date can not be a future date.", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                       // dayName = android.text.format.DateFormat.format("EEEE", attendanceDay).toString();
                        String mm ="";
                        String dd ="";
                        if((sMonth+1)<10)
                        {
                            mm="0"+(sMonth+1);
                        }
                        else{
                            mm=""+(sMonth+1);
                        }
                         if(sDay<10)
                        {
                            dd="0"+(sDay);
                        }
                         else{
                             dd=""+(sDay);
                         }

                        attendanceDate = sYear+"/"+mm+"/"+dd;
                        attendanceDateTextView.setText(sYear+"/"+mm+"/"+dd);

                        sp=  rootView.findViewById(R.id.groupInfoAttendenceSpinner);
                        sp.setSelection(0);
                        listView.setVisibility(View.GONE);
                        submitAttendanceButton.setVisibility(View.GONE);
                        //ViewStudentList();

                        if(days<=-2)
                        {
                            Toast.makeText(getActivity(),"You can not change attendance more than 2 days past.", Toast.LENGTH_LONG).show();
                        }
                    }

                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

 /*   private void SaveAttendance()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait while loading...");
        progressDialog.setCancelable(false);
        if (NetworkAvailability.isNetworkAvailable(getActivity())){
            progressDialog.show();
            GroupBasic groupBasic = new GroupBasic(groupId, UserSession.UserId,"");
            SaveAttendanceModel saveAttendanceModel = new SaveAttendanceModel(studInfoList, groupBasic);

            Call<CommonResponse> commonResponseCall = apiInterface.getSaveAttendanceResponse(saveAttendanceModel);

            commonResponseCall.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                    try {

                        if (response.body()!=null){
                            if (response.body().getMessageCode().equals("Y")){
                                progressDialog.dismiss();
                                //UserInfo userInfo = response.body().getContentUserInfo();
                                Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Unable to fetch data, try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Failure, Unable to fetch data, try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(getActivity(),"No internet connection.", Toast.LENGTH_SHORT).show();
        }

    }*/

    /*private void GetGroupInfo()
    {
        groupInfoNames =new ArrayList<String>();
        groupInfoNames.add("Select a class");
        groupInfoHashMap =new HashMap<>();
        if (NetworkAvailability.isNetworkAvailable(getActivity())){

            //     listView  = (ListView)rootView.findViewById(R.id.notice_list_view);

            UserBasic userBasic = new UserBasic(UserSession.UserEmail, UserSession.UserId,"");

            Call<ParameterGroupResponse> commonResponseCall = apiInterface.getParameterGrpups(userBasic);

            commonResponseCall.enqueue(new Callback<ParameterGroupResponse>() {
                @Override
                public void onResponse(Call<ParameterGroupResponse> call, @NonNull Response<ParameterGroupResponse> response) {
                    try {

                        if (response.body()!=null){
                            if (response.body().getMessageCode().equals("Y")){


                                ///adapter=new ArrayAdapter<GroupInfo>(getActivity(),android.R.layout.simple_list_item_1,response.body().getGroupInfo());
                                groupInfo = response.body().getGroupInfo();

                                for (int i = 0; i < groupInfo.size(); i++)
                                {

                                    groupInfoNames.add(groupInfo.get(i).getGroupTitle());
                                    groupInfoHashMap.put(i, groupInfo.get(i).getGroupId());
                                }


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
                public void onFailure(Call<ParameterGroupResponse> call, Throwable t) {

                    Toast.makeText(getActivity(),"Failure, Unable to fetch data, try again.", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            Toast.makeText(getActivity(),"No internet connection.", Toast.LENGTH_SHORT).show();

        }

    }*/


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        /*String docName = adapterView.getItemAtPosition(i).toString();
        if (docName.equals("Select a class")) {
            listView.setVisibility(View.GONE);
            submitAttendanceButton.setVisibility(View.GONE);
        } else {
            String docId = groupInfoHashMap.get(i-1);
            groupId = docId;
            listView.setVisibility(View.VISIBLE);
            submitAttendanceButton.setVisibility(View.VISIBLE);
            ViewStudentList();

            if(days<=-2||days>0)
            {
                submitAttendanceButton.setVisibility(View.GONE);
            }

        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
