package com.teacher.modern.modernteacher.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.adapter.StudentAttendanceAdapter;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;
import com.teacher.modern.modernteacher.models.Subject;
import com.teacher.modern.modernteacher.models.TeacherSession;
import com.teacher.modern.modernteacher.models.viewmodels.AttendanceVM;
import com.teacher.modern.modernteacher.models.viewmodels.AttendanceVMCollections;
import com.teacher.modern.modernteacher.models.viewmodels.GetAttendanceVM;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class newAttendanceFragment extends Fragment implements View.OnClickListener {


    View rootView;
    private ApiInterface apiInterface;
    TextView attendance_date;
    ImageView attendanceDateImageIcon;
    Spinner subject_spinner;
    ListView student_list_view;
    Button submitAttendanceButton;
    Dialog dialog;
    Calendar attendanceDay;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener datePicker;
    int dayGlobal,monthGlobal,yearGlobal;
    ProgressBar progress_circular;

    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;

    List<Subject> subjectLists = new ArrayList<Subject>();
    List<String> subjectNames;
    List<AttendanceVM> attendanceVMList;
    private LinearLayout noDataFoundLayout;

    private int pos;

    private ArrayAdapter<String> subjectSpinnerAdapter;

    public newAttendanceFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_new_attendance, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.attendance_text);
        apiInterface = ServiceGenerator.createService(ApiInterface.class);
        myCalendar = Calendar.getInstance();

        attendance_date = rootView.findViewById(R.id.attendance_date);
        attendanceDateImageIcon = rootView.findViewById(R.id.attendanceDateImageIcon);
        subject_spinner = rootView.findViewById(R.id.subject_spinner);
        student_list_view = rootView.findViewById(R.id.student_list_view);
        submitAttendanceButton = rootView.findViewById(R.id.submitAttendanceButton);
        progress_circular =rootView.findViewById(R.id.progress_circular);
        noDataFoundLayout = rootView.findViewById(R.id.noDataFoundLayoutID);
        attendance_date.setOnClickListener(this);
        attendanceDateImageIcon.setOnClickListener(this);
        submitAttendanceButton.setOnClickListener(this);

        datePicker = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String dateString = new SimpleDateFormat("dd/MM/yyyy/ EEE").format(myCalendar.getTime());
                String[] parts = dateString.split("/");
                 dayGlobal = Integer.parseInt(parts[0]); // 004
                 monthGlobal = Integer.parseInt(parts[1]);
                 yearGlobal = Integer.parseInt(parts[2]);

                attendance_date.setText(dateString);
                subject_spinner.setVisibility(View.VISIBLE);

            }

        };

        subjectLists = TeacherSession.subjects;
        subjectNames = new ArrayList<String>();

        subjectNames.add(getString(R.string.select_a_class));

        if (subjectLists !=null){
            for(int i=0; i<subjectLists.size(); i++){
                if (subjectLists.get(i).ClassId==1 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "01" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==3 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "02" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==5 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "03" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                } if (subjectLists.get(i).ClassId==7 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "04" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==9 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "05" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==11 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "06" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==13 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "07" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==15 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "08" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==17 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "09" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==19 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "10" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==21 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "Play" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==23 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "Nursery" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==1 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "01" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }

                if (subjectLists.get(i).ClassId==3 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "02" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==5 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "03" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                } if (subjectLists.get(i).ClassId==7 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "04" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==9 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "05" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==11 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "06" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==13 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "07" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==15 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "08" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==17 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "09" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==19 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "10" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==21 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "Play" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }
                if (subjectLists.get(i).ClassId==23 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "Nursery" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Morning"+")");
                }

                //Day

                if (subjectLists.get(i).ClassId==2 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "01" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==4 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "02" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==6 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "03" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Day"+")");
                } if (subjectLists.get(i).ClassId==8 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "04" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==10 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "05" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==12 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "06" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==14 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "07" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==16 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "08" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==18 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "09" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==20 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "10" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==22 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "Play" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==24 && subjectLists.get(i).SectionId==1){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "Nursery" + ", " + getString(R.string._sec) + " " + "A" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==2 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "01" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }

                if (subjectLists.get(i).ClassId==4 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "02" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==6 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "03" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Day"+")");
                } if (subjectLists.get(i).ClassId==8 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "04" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==10 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "05" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==12 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "06" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==14 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "07" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==16 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "08" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==18 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "09" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==20 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "10" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==22 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "Play" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
                if (subjectLists.get(i).ClassId==24 && subjectLists.get(i).SectionId==2){
                    subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + "Nursery" + ", " + getString(R.string._sec) + " " + "B" + ", " + getString(R.string._shift) + " " + "Day"+")");
                }
            }
            subjectSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subjectNames);
            subjectSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subject_spinner.setAdapter(subjectSpinnerAdapter);
        }




        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){ // Select a class
                    //Toast.makeText(getActivity(),"Please select a class",Toast.LENGTH_LONG).show();


                }
                else {

                    student_list_view.setVisibility(View.VISIBLE);
                    progress_circular.setVisibility(View.VISIBLE);
                    pos=i;
                    setStudent_list_view(i);
                   // submitAttendanceButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        student_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ImageView attendance_image = view.findViewById(R.id.attendance_image);

                if(attendanceVMList.get(i).PresentStatus.equals("P         ") || attendanceVMList.get(i).PresentStatus.equals("P") ){
                    //Toast.makeText(getActivity(),"Absent kore disi",Toast.LENGTH_LONG).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        attendanceVMList.get(i).PresentStatus="A";
                        attendance_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_absent_icon));
                        submitAttendanceButton.setVisibility(View.VISIBLE);
                    }else {
                        attendanceVMList.get(i).PresentStatus = "A";
                        attendance_image.setImageDrawable(getResources().getDrawable(R.drawable.icon_absent));
                        submitAttendanceButton.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    //Toast.makeText(getActivity(),"Present kore disi",Toast.LENGTH_LONG).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        attendanceVMList.get(i).PresentStatus="P";
                        attendance_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_present_icon));
                        submitAttendanceButton.setVisibility(View.VISIBLE);
                    }
                    else {
                        attendanceVMList.get(i).PresentStatus = "P";
                        attendance_image.setImageDrawable(getResources().getDrawable(R.drawable.icon_present));
                        submitAttendanceButton.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        return rootView;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id==R.id.attendanceDateImageIcon || id== R.id.attendance_date)
        {
            DatePickerDialog datePickerDialog =  new DatePickerDialog(getActivity(), datePicker, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            datePickerDialog.show();
        }
        else if(id==R.id.submitAttendanceButton){
            AttendanceVMCollections attendanceVMCollections = new AttendanceVMCollections();
            attendanceVMCollections.attendanceVMs = attendanceVMList;
            attendanceVMCollections.maskingId = attendanceVMList.get(0).maskingId;
            attendanceVMCollections.day = dayGlobal;
            attendanceVMCollections.month = monthGlobal;
            attendanceVMCollections.year = yearGlobal;

            Call<Boolean> setOrUpdateAttendaces = apiInterface.setOrUpdateAttendaces(attendanceVMCollections);

            setOrUpdateAttendaces.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.body()!=null){
                        //Toast.makeText(getActivity(),"Not Null"+response.body(),Toast.LENGTH_LONG).show();
                        if(response.body()==true){

                           setStudent_list_view(pos);
                           Toast.makeText(getActivity(),R.string.successfully_updated_att,Toast.LENGTH_LONG).show();
                            //submitAttendanceButton.setVisibility(View.GONE);
                        }
                        else{

                        }
                    }
                    else {
                        Toast.makeText(getActivity(),"Null",Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        }

    }

    public void setStudent_list_view(int position){


        position =position-1; // Select a class

        GetAttendanceVM getAttendanceVM = new GetAttendanceVM(subjectLists.get(position).MaskingSubjectId,subjectLists.get(position).ClassId,subjectLists.get(position).SectionId,dayGlobal,yearGlobal,monthGlobal);

        Call<List<AttendanceVM>> getAttendanceRecord = apiInterface.getAttendanceRecord(getAttendanceVM);

        getAttendanceRecord.enqueue(new Callback<List<AttendanceVM>>() {
            @Override
            public void onResponse(Call<List<AttendanceVM>> call, Response<List<AttendanceVM>> response) {
                if(response.body()!=null){
                    //Toast.makeText(getActivity(),response.body().get(0).Name,Toast.LENGTH_LONG).show();
                    attendanceVMList = response.body();
                    StudentAttendanceAdapter adapter = new StudentAttendanceAdapter(getActivity(),response.body());
                    student_list_view.setAdapter(adapter);
                    student_list_view.setVisibility(View.VISIBLE);
                    noDataFoundLayout.setVisibility(View.GONE);
                    attendance_date.setClickable(false);
                    attendance_date.setLongClickable(false);
                    attendanceDateImageIcon.setClickable(false);
                    attendanceDateImageIcon.setLongClickable(false);
                    progress_circular.setVisibility(View.GONE);
                    submitAttendanceButton.setVisibility(View.VISIBLE);

                }
                else {
                    student_list_view.setVisibility(View.GONE);
                    //Toast.makeText(getActivity(),R.string.no_list,Toast.LENGTH_LONG).show();
                    progress_circular.setVisibility(View.GONE);
                    submitAttendanceButton.setVisibility(View.GONE);
                    noDataFoundLayout.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<List<AttendanceVM>> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.no_internet),Toast.LENGTH_LONG).show();
                progress_circular.setVisibility(View.GONE);
                submitAttendanceButton.setVisibility(View.GONE);
                noDataFoundLayout.setVisibility(View.VISIBLE);
            }
        });

    }




}
