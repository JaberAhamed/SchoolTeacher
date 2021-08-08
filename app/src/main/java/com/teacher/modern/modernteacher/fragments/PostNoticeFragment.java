package com.teacher.modern.modernteacher.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;
import com.teacher.modern.modernteacher.connectivity.NetworkAvailability;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;
import com.teacher.modern.modernteacher.database.MyDatabase;
import com.teacher.modern.modernteacher.models.Notice;
import com.teacher.modern.modernteacher.models.Subject;
import com.teacher.modern.modernteacher.models.TeacherSession;
import com.teacher.modern.modernteacher.models.viewmodels.PostNoticeVM;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostNoticeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;
    View rootView;
    private ApiInterface apiInterface;
    EditText titleEditText,descriptionEditText;
    Button submitPostButton,anotherPostButton;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialogGroups;
    String groupId="";
    //ArrayList<GroupInfo> groupInfo = new ArrayList<GroupInfo>();
    List<Subject> subjectLists = new ArrayList<Subject>();

    int dayGlobal=-1, monthGlobal, yearGlobal;

    Dialog dialog;
    Calendar attendanceDay;
    Calendar today;
    TextView attendanceDateTextView;
    ImageView attendanceDateImageIcon;
    long days=0;
    String attendanceDate="";


    //List<String> groupInfoNames;
    List<String> subjectNames;
    private HashMap<Integer, String> groupInfoHashMap;
    private ArrayAdapter<String> groupInfoAdapter;

    TextView success_message_tv;
    LinearLayout anotherPostlayout, postNoticeLayout;
    //TextView dateEditText;
    private MyDatabase myDatabase;
    private long teacherUID;
    //DatePicker datePicker;

    Spinner sp;
    public PostNoticeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_post_notice, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.create_notice);
        apiInterface = ServiceGenerator.createService(ApiInterface.class);
        titleEditText = rootView.findViewById(R.id.titleEditText);
        //datePicker = rootView.findViewById(R.id.date_picker);
        descriptionEditText = rootView.findViewById(R.id.descriptionEditText);


        attendanceDateTextView = rootView.findViewById(R.id.attendanceDateTextView);
        attendanceDateImageIcon = rootView.findViewById(R.id.attendanceDateImageIcon);
        attendanceDateImageIcon.setOnClickListener(this);

        attendanceDateTextView.setOnClickListener(this);
        today = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.yyy));
        attendanceDate = dateFormat.format(today.getTime());
        //attendanceDateTextView.setText("Select notice post valid date");

        submitPostButton = rootView.findViewById(R.id.submitPostButton);
        submitPostButton.setOnClickListener(this);
        anotherPostButton = rootView.findViewById(R.id.anotherPostButton);
        anotherPostButton.setOnClickListener(this);
        sp=  rootView.findViewById(R.id.groupInfoSpinner);
        //groupInfo = new ArrayList<GroupInfo>();
        subjectLists = TeacherSession.subjects;
       subjectNames = new ArrayList<String>();





        if (subjectLists.size()>0) {
            for (int i = 0; i < subjectLists.size(); i++) {
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
            groupInfoAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subjectNames);
            groupInfoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(groupInfoAdapter);
            sp.setOnItemSelectedListener(this);
        }
        myDatabase = new MyDatabase(getActivity());
        Cursor cursor =  myDatabase.getAllData();
        while (cursor.moveToNext()){
            teacherUID = cursor.getLong(2);
        }




       // postNoticeLayout = getActivity().findViewById(R.id.postNoticeLayout);

        // Posted date for
        /*final Calendar myCalendar = Calendar.getInstance();
        dateEditText = rootView.findViewById(R.id.postedDateFor);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                dayGlobal = dayOfMonth;
                monthGlobal = monthOfYear+1;
                yearGlobal = year;

                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                String dateStr = sdf.format(myCalendar.getTime());

                dateEditText.setText(dateStr);
            }

        };*/
        /*dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                *//*datePicker.setVisibility(View.VISIBLE);
                datePicker.init(Calendar.YEAR, Calendar.MONTH,
                        Calendar.DAY_OF_MONTH, null);*//*


               *//* DatePickerDialog datePickerDialog =  new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
*//*



            }
        });*/

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.submitPostButton) {

            if(dayGlobal==-1){
                Toast.makeText(getActivity(),R.string.please_notice,Toast.LENGTH_LONG).show();
            }
            else {
                String title=titleEditText.getText().toString().trim();
                String description=descriptionEditText.getText().toString().trim();

                if(title.equals("") || description.equals("")){
                    Toast.makeText(getActivity(),R.string.title_description,Toast.LENGTH_LONG).show();
                }
                else {
                    PostNotice();
                }


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
            dialog.setTitle("Select Post Date:");


            TextView date_type_text = (TextView) dialog.findViewById(R.id.date_type_text);
            date_type_text.setText("Post Date");

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

                    /*long difference = attendanceDay.getTimeInMillis() - today.getTimeInMillis();
                    days = difference / (24 * 60 * 60 * 1000);*/

                    dayGlobal = sDay;
                    monthGlobal = sMonth+1;
                    yearGlobal = sYear;

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


                    /*if(days>0)
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

                        *//*sp=  rootView.findViewById(R.id.groupInfoAttendenceSpinner);
                        sp.setSelection(0);*//*
                        *//*listView.setVisibility(View.GONE);
                        submitAttendanceButton.setVisibility(View.GONE);*//*
                        //ViewStudentList();

                        if(days<=-2)
                        {
                            Toast.makeText(getActivity(),"You can not change attendance more than 2 days past.", Toast.LENGTH_LONG).show();
                        }
                    }
*/
                    dialog.dismiss();
                }
            });

            dialog.show();
        }


    }

    private void PostNotice()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        String title=titleEditText.getText().toString().trim();
        String description=descriptionEditText.getText().toString().trim();
        String datePostedFor = attendanceDateTextView.getText().toString();


        if (NetworkAvailability.isNetworkAvailable(getActivity())){
            progressDialog.show();
            //NoticeInfo noticeInfo = new NoticeInfo("",groupId, UserSession.UserId,title,description);

            int selectedPosition = sp.getSelectedItemPosition();

            Notice notice = new Notice(99,teacherUID,subjectLists.get(selectedPosition).MaskingSubjectId,description,title,"1234","---",datePostedFor);

            PostNoticeVM postNoticeVM = new PostNoticeVM();
            postNoticeVM.notice = notice;
            postNoticeVM.Day = dayGlobal;
            postNoticeVM.Year = yearGlobal;
            postNoticeVM.Month = monthGlobal;

            Call<Boolean> postNotice = apiInterface.postNotice(postNoticeVM);

            postNotice.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.body()!=null){
                        if(response.body()==true){
                            progressDialog.hide();
                            Toast.makeText(getActivity(),R.string.succ_post,Toast.LENGTH_LONG).show();
                            getFragmentManager().popBackStack();
                            hideKeyboard();
                        }
                    }
                    else {
                        Toast.makeText(getActivity(),"Returning null",Toast.LENGTH_LONG).show();
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

   /* private void GetGroupInfo()
    {

        groupInfoNames =new ArrayList<String>();
        groupInfoNames.add("Select a subject");
        groupInfoHashMap =new HashMap<>();
        progressDialogGroups = new ProgressDialog(getActivity());
        progressDialogGroups.setTitle("Loading");
        progressDialogGroups.setMessage("Please wait while loading...");
        progressDialogGroups.setCancelable(false);
        if (NetworkAvailability.isNetworkAvailable(getActivity())){
            progressDialogGroups.show();
       //     listView  = (ListView)rootView.findViewById(R.id.notice_list_view);

            UserBasic userBasic = new UserBasic(UserSession.UserEmail, UserSession.UserId,"");

            Call<ParameterGroupResponse> commonResponseCall = apiInterface.getParameterGrpups(userBasic);

            commonResponseCall.enqueue(new Callback<ParameterGroupResponse>() {
                @Override
                public void onResponse(Call<ParameterGroupResponse> call, @NonNull Response<ParameterGroupResponse> response) {
                    try {

                        if (response.body()!=null){
                            if (response.body().getMessageCode().equals("Y")){

                                progressDialogGroups.dismiss();
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
                        progressDialogGroups.dismiss();
                    } catch (Exception e){
                        progressDialogGroups.dismiss();
                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ParameterGroupResponse> call, Throwable t) {
                    progressDialogGroups.dismiss();
                    Toast.makeText(getActivity(),"Failure, Unable to fetch data, try again.", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            Toast.makeText(getActivity(),"No internet connection.", Toast.LENGTH_SHORT).show();

        }

    }


    private  void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    } */


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String docName = adapterView.getItemAtPosition(i).toString();
        if (docName.equals("Select a class")) {

        } else {
            //String docId = groupInfoHashMap.get(i-1);
            //groupId = docId;
           // Log.d("docId",docId);
            //Log.d("docName",docName);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private  void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
