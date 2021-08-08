package com.teacher.modern.modernteacher.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.adapter.ExamMarksAdapter;
import com.teacher.modern.modernteacher.adapter.ExamMarksAdapterView;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;
import com.teacher.modern.modernteacher.models.ExamType;
import com.teacher.modern.modernteacher.models.Subject;
import com.teacher.modern.modernteacher.models.TeacherSession;
import com.teacher.modern.modernteacher.models.viewmodels.InputExamMarksVM;
import com.teacher.modern.modernteacher.models.viewmodels.RequestClassTestMarksVM;
import com.teacher.modern.modernteacher.models.viewmodels.RequestExamMarksVM;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class newExamFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    View rootView;
    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;
    private ApiInterface apiInterface;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener datePicker;

    private ListView student_marks_list;

    Spinner groupInfoExSpinner, ExamTypeSpinner;

    List<InputExamMarksVM> inputExamMarksVMList;

    List<Subject> subjectLists = new ArrayList<Subject>();
    List<String> subjectNames;
    List<ExamType> examTypes;

    private ArrayAdapter<String> groupInfoAdapter;
    private ArrayAdapter<String> examGroupInfoAdapter;

    Button submit;
    EditText total_marks_edit;
    String dateString;
    int classId = 0, sectionId = 0, maskingId = 0, examTypeId = 0, subjectId = 0;
    double totalMarks = 0;
    long studentId = 0;

    LinearLayout edit_view_button_layout, view_exam_marks_layout, edit_exam_marks_layout, class_test_date_layout, layout_no_data_found;
    ListView student_marks_list_view;

    Button view_marks_button, edit_marks_button;

    TextView class_test_date;
    ImageView classTestDateImageIcon;
    ProgressBar progressBar;

    int dayGlobal, monthGlobal, yearGlobal;
    int viewOrEdit = -1;

    public newExamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_new_exam, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.exams_text);
        apiInterface = ServiceGenerator.createService(ApiInterface.class);

        myCalendar = Calendar.getInstance();

        student_marks_list = rootView.findViewById(R.id.student_marks_list);
        groupInfoExSpinner = rootView.findViewById(R.id.groupInfoExSpinner);
        ExamTypeSpinner = rootView.findViewById(R.id.ExamTypeSpinner);
        submit = rootView.findViewById(R.id.submit);
        total_marks_edit = rootView.findViewById(R.id.total_marks_edit);
        edit_view_button_layout = rootView.findViewById(R.id.edit_view_button_layout);
        view_marks_button = rootView.findViewById(R.id.view_marks_button);
        edit_marks_button = rootView.findViewById(R.id.edit_marks_button);
        student_marks_list_view = rootView.findViewById(R.id.student_marks_list_view);
        edit_exam_marks_layout = rootView.findViewById(R.id.edit_exam_marks_layout);
        view_exam_marks_layout = rootView.findViewById(R.id.view_exam_marks_layout);
        class_test_date_layout = rootView.findViewById(R.id.class_test_date_layout);
        class_test_date = rootView.findViewById(R.id.class_test_date);
        classTestDateImageIcon = rootView.findViewById(R.id.classTestDateImageIcon);
        layout_no_data_found = rootView.findViewById(R.id.noDataFoundLayoutID);
        progressBar = rootView.findViewById(R.id.progressbarId);
        setGroupInfoExSpinner();
        GetExamTypes();

        ExamTypeSpinner.setOnItemSelectedListener(this);

        datePicker = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                dateString = new SimpleDateFormat("yyyy/MM/dd").format(myCalendar.getTime());
                String[] parts = dateString.split("/");
                dayGlobal = Integer.parseInt(parts[2]); // 004
                monthGlobal = Integer.parseInt(parts[1]);
                yearGlobal = Integer.parseInt(parts[0]);

                class_test_date.setText(dateString);
                progressBar.setVisibility(View.VISIBLE);

                if (viewOrEdit == 1) {
                    final RequestClassTestMarksVM request = new RequestClassTestMarksVM();
                    request.ClassId = classId;
                    request.SectionId = sectionId;
                    //request.MaskingId = maskingId;
                    request.examTypeId = examTypeId;
                    request.MaskingSubjectId = maskingId;
                    request.day = dayGlobal;
                    request.month = monthGlobal;
                    request.year = yearGlobal;


                    Call<List<InputExamMarksVM>> GetViewableClassTestMarksByClassIdSectionId = apiInterface.GetViewableClassTestMarksByClassIdSectionId(request);
                    GetViewableClassTestMarksByClassIdSectionId.enqueue(new Callback<List<InputExamMarksVM>>() {
                        @Override
                        public void onResponse(Call<List<InputExamMarksVM>> call, Response<List<InputExamMarksVM>> response) {

                            if (response.body()!=null) {
                                view_exam_marks_layout.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                inputExamMarksVMList = response.body();
                                ExamMarksAdapterView examMarksAdapter = new ExamMarksAdapterView(getActivity(), response.body(), 1);
                                student_marks_list_view.setAdapter(examMarksAdapter);
                                examMarksAdapter.notifyDataSetChanged();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage(R.string.no_data_found).show();
                                progressBar.setVisibility(View.GONE);
                                //Toast.makeText(getActivity(),"No data found",Toast.LENGTH_LONG).show();-
                            }


                        }

                        @Override
                        public void onFailure(Call<List<InputExamMarksVM>> call, Throwable t) {

                        }
                    });
                } else if (viewOrEdit == 2) {


                }


            }

        };


        class_test_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), datePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        classTestDateImageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), datePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();

            }
        });

        view_marks_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //view_exam_marks_layout.setVisibility(View.VISIBLE);
                edit_view_button_layout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                groupInfoExSpinner.setEnabled(false);
                groupInfoExSpinner.setClickable(false);

                ExamTypeSpinner.setEnabled(false);
                ExamTypeSpinner.setClickable(false);

                if (examTypeId == 4) {// If class test

                    class_test_date_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    viewOrEdit = 1;


                } else {


                    final RequestExamMarksVM request = new RequestExamMarksVM();
                    request.ClassId = classId;
                    request.SectionId = sectionId;
                    request.MaskingSubjectId = maskingId;
                    request.examTypeId = examTypeId;
                  //  request.SubjectId = subjectId;


                    Call<List<InputExamMarksVM>> GetViewableExamMarksByClassIdSectionId = apiInterface.GetViewableExamMarksByClassIdSectionId(request);
                    GetViewableExamMarksByClassIdSectionId.enqueue(new Callback<List<InputExamMarksVM>>() {
                        @Override
                        public void onResponse(Call<List<InputExamMarksVM>> call, Response<List<InputExamMarksVM>> response) {

                            if (response.body() != null && response.body().size() > 0) {
                                view_exam_marks_layout.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                inputExamMarksVMList = response.body();
                                ExamMarksAdapterView examMarksAdapter = new ExamMarksAdapterView(getActivity(), response.body(), 1);
                                student_marks_list_view.setAdapter(examMarksAdapter);
                                examMarksAdapter.notifyDataSetChanged();
                            } else {
                                view_exam_marks_layout.setVisibility(View.GONE);
                                layout_no_data_found.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<InputExamMarksVM>> call, Throwable t) {

                        }
                    });
                }
            }
        });

        edit_marks_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edit_exam_marks_layout.setVisibility(View.VISIBLE);
                edit_view_button_layout.setVisibility(View.GONE);

                /*groupInfoExSpinner.setVisibility(View.GONE);
                ExamTypeSpinner.setVisibility(View.GONE);*/
                groupInfoExSpinner.setEnabled(false);
                groupInfoExSpinner.setClickable(false);

                ExamTypeSpinner.setEnabled(false);
                ExamTypeSpinner.setClickable(false);

                if (examTypeId == 4) {// If class test

                    class_test_date_layout.setVisibility(View.VISIBLE);

                    final RequestClassTestMarksVM request = new RequestClassTestMarksVM();
                    request.ClassId = classId;
                    request.SectionId = sectionId;
                    /*request.MaskingId = maskingId;
                    request.examTypeId = examTypeId;*/
                    request.MaskingSubjectId = maskingId;
                    /*request.day = dayGlobal;
                    request.month = monthGlobal;
                    request.year = yearGlobal;*/


                    Call<List<InputExamMarksVM>> GetEditableClassTestMarksByClassIdSectionId = apiInterface.GetEditableClassTestMarksByClassIdSectionId(request);
                    GetEditableClassTestMarksByClassIdSectionId.enqueue(new Callback<List<InputExamMarksVM>>() {
                        @Override
                        public void onResponse(Call<List<InputExamMarksVM>> call, Response<List<InputExamMarksVM>> response) {

                            if (response.body().size() > 0) {
                                inputExamMarksVMList = response.body();

                                ExamMarksAdapter examMarksAdapter = new ExamMarksAdapter(getActivity(), inputExamMarksVMList, 0);
                                student_marks_list.setAdapter(examMarksAdapter);
                                examMarksAdapter.notifyDataSetChanged();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage(R.string.no_data_found).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<List<InputExamMarksVM>> call, Throwable t) {

                        }
                    });

                } else {
                    final RequestExamMarksVM request = new RequestExamMarksVM();
                    request.ClassId = classId;
                    request.SectionId = sectionId;
                    request.MaskingSubjectId = maskingId;
                    request.examTypeId = examTypeId;
                    //request.SubjectId = subjectId;


                    Call<List<InputExamMarksVM>> GetExamMarksByClassIdSectionId = apiInterface.GetEditableExamMarksByClassIdSectionId(request);
                    GetExamMarksByClassIdSectionId.enqueue(new Callback<List<InputExamMarksVM>>() {
                        @Override
                        public void onResponse(Call<List<InputExamMarksVM>> call, Response<List<InputExamMarksVM>> response) {

                            if (response.body()!= null) {
                                inputExamMarksVMList = response.body();

                                ExamMarksAdapter examMarksAdapter = new ExamMarksAdapter(getActivity(), inputExamMarksVMList, 0);
                                student_marks_list.setAdapter(examMarksAdapter);
                                examMarksAdapter.notifyDataSetChanged();
                            } else {
                                layout_no_data_found.setVisibility(View.VISIBLE);
                            }


                        }

                        @Override
                        public void onFailure(Call<List<InputExamMarksVM>> call, Throwable t) {

                        }
                    });
                }
                ;


            }
        });

        //setStudentMarksList();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (total_marks_edit.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.please_input).show();
                } else {


                    boolean isTotalMarks = false;
                    totalMarks = Double.parseDouble(total_marks_edit.getText().toString());
                    for (int i = 0; i < inputExamMarksVMList.size(); i++) {
                        if (inputExamMarksVMList.get(i).obtainedMarks > totalMarks) {
                            isTotalMarks = false;
                            break;
                        } else {
                            isTotalMarks = true;
                        }

                    }

                    // inputExamMarksVMList.remove(0);
                    if (isTotalMarks == true) {


                        if (inputExamMarksVMList != null) {
                            for (int i = 0; i < inputExamMarksVMList.size(); i++) {
                                InputExamMarksVM inputExamMarksVM = (InputExamMarksVM) student_marks_list.getItemAtPosition(i);
                                // Toast.makeText(getActivity(),inputExamMarksVM.obtainedMarks+"",Toast.LENGTH_LONG).show();

                                if (inputExamMarksVMList.get(i).obtainedMarks == -99) {
                                    Toast.makeText(getActivity(), "Something went wrong..", Toast.LENGTH_LONG).show();
                                    inputExamMarksVMList.get(i).obtainedMarks = 0;
                                } else {

                                    inputExamMarksVMList.get(i).obtainedMarks = inputExamMarksVM.obtainedMarks;
                                }

                                inputExamMarksVMList.get(i).StudentId = inputExamMarksVM.StudentId;
                                inputExamMarksVMList.get(i).StudentName = inputExamMarksVM.StudentName;
                                inputExamMarksVMList.get(i).StudentRoll = inputExamMarksVM.StudentRoll;
                                inputExamMarksVMList.get(i).maskingId = maskingId;
                                inputExamMarksVMList.get(i).ExamTypeId = examTypeId;
                                inputExamMarksVMList.get(i).totalMarks = totalMarks;

                                if (examTypeId == 4) {
                                    inputExamMarksVMList.get(i).ExamDate = dateString;
                                }

                            }

                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            //Yes button clicked
                                            Call<Boolean> SetExamMarks;
                                            if (examTypeId == 4) {
                                                SetExamMarks = apiInterface.SetClassTestMarks(inputExamMarksVMList);
                                            } else {
                                                SetExamMarks = apiInterface.SetExamMarks(inputExamMarksVMList);
                                            }


                                            SetExamMarks.enqueue(new Callback<Boolean>() {
                                                @Override
                                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                    if (response.body() != null) {

                                                        Boolean check = response.body();

                                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                        builder.setMessage(R.string.successfully_updated).show();

                                                        manager = getFragmentManager();
                                                        currentFragment = new HomeFragment();
                                                        transaction = manager.beginTransaction();
                                                        transaction.replace(R.id.main_fragment, currentFragment);
                                                        transaction.addToBackStack("HomeFragment");
                                                        transaction.commit();

                                                        //Toast.makeText(getActivity(),"Done",Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Boolean> call, Throwable t) {

                                                }
                                            });

                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            //No button clicked
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(R.string.are_you_sure).setPositiveButton(getString(R.string.yes), dialogClickListener)
                                    .setNegativeButton(getString(R.string.no_), dialogClickListener).show();

                        }
                    } else {
                        Toast.makeText(getActivity(), R.string.obtain_marks_message, Toast.LENGTH_SHORT).show();
                    }


                }


            }

        });


        return rootView;
    }

    private void setGroupInfoExSpinner() {

        subjectLists = TeacherSession.subjects;
        subjectNames = new ArrayList<String>();
        //examTypes = new ArrayList<>();

       subjectNames.add(getString(R.string.select_a_class));
        if (subjectLists != null) {
            for (int i = 0; i <subjectLists.size(); i++) {
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
            groupInfoExSpinner.setAdapter(groupInfoAdapter);
            groupInfoAdapter.notifyDataSetChanged();
            groupInfoExSpinner.setOnItemSelectedListener(this);
        }


    }


    public void GetExamTypes() {

        Call<List<ExamType>> getAllExamTypes = apiInterface.getAllExamTypes("Exam/getAllExamTypes");

        getAllExamTypes.enqueue(new Callback<List<ExamType>>() {
            @Override
            public void onResponse(Call<List<ExamType>> call, Response<List<ExamType>> response) {
                if (response.body() != null) {
                    //Toast.makeText(getActivity(),"Okay",Toast.LENGTH_LONG).show();
                    examTypes = response.body();
                } else {
                    examTypes = null;
                }
            }

            @Override
            public void onFailure(Call<List<ExamType>> call, Throwable t) {

            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int spinnerId = adapterView.getId();

        if (spinnerId == R.id.groupInfoExSpinner) {
            String groupName = adapterView.getItemAtPosition(i).toString();

            if (groupName.equals(getString(R.string.select_a_class))) {

            } else {
                int position = i - 1; //Select a class
                classId = subjectLists.get(position).ClassId;
                sectionId = subjectLists.get(position).SectionId;
                maskingId = subjectLists.get(position).MaskingSubjectId;
                subjectId = subjectLists.get(position).SubjectId;

                ExamTypeSpinner.setVisibility(View.VISIBLE);

                List<String> examTypesStr = new ArrayList<>();
                examTypesStr.add(getString(R.string.select_an_exam));

                if (examTypes != null) {
                    for (int j = 0; j < examTypes.size(); j++) {
                        examTypesStr.add(examTypes.get(j).ExamTitle);
                    }
                    examGroupInfoAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, examTypesStr);
                    examGroupInfoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    examGroupInfoAdapter.notifyDataSetChanged();
                    ExamTypeSpinner.setAdapter(examGroupInfoAdapter);

                }
            }


        } else if (spinnerId == R.id.ExamTypeSpinner) {
            String examType = adapterView.getItemAtPosition(i).toString();
            if (examType.equals(getString(R.string.select_an_exam))) {

            } else {
                int p = i - 1;
                examTypeId = (int) examTypes.get(p).Id;

                edit_view_button_layout.setVisibility(View.VISIBLE);


                //setStudentMarksList();
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}