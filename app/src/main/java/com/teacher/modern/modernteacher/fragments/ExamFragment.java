package com.teacher.modern.modernteacher.fragments;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;
import com.teacher.modern.modernteacher.connectivity.NetworkAvailability;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;
import com.teacher.modern.modernteacher.models.ExamType;
import com.teacher.modern.modernteacher.models.Subject;
import com.teacher.modern.modernteacher.models.TeacherSession;
import com.teacher.modern.modernteacher.service_models.exam_models.ExamGroupBasic;
import com.teacher.modern.modernteacher.service_models.exam_models.ExamGroupInfo;
import com.teacher.modern.modernteacher.service_models.exam_models.ExamInfo;
import com.teacher.modern.modernteacher.service_models.exam_models.ExamInfoResponse;
import com.teacher.modern.modernteacher.service_models.exam_models.StudentExamListAdapter;
import com.teacher.modern.modernteacher.service_models.notice_models.GroupInfo;
import com.teacher.modern.modernteacher.service_models.security_models.UserSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    View rootView;
    android.app.Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;
    private ApiInterface apiInterface;

    private ListView listView;
    private StudentExamListAdapter adapter;

    TextView examMarksTextView,examDateTextView;
    LinearLayout examMarksLayout;

    Dialog dialog;

    String groupId="";
    String examGroupId="";
    String examName="Asdf";
    String className="";
    String examMarks="";

    List<Subject> subjectLists = new ArrayList<Subject>();


    //List<String> groupInfoNames;
    List<String> subjectNames;

    ArrayList<ExamInfo> examInfoList;

    ArrayList<GroupInfo> groupInfo = new ArrayList<GroupInfo>();
    List<String> groupInfoNames;
    List<ExamType> examTypes;
    private HashMap<Integer, String> groupInfoHashMap;
    private ArrayAdapter<String> groupInfoAdapter;
    Spinner sp;

    ArrayList<ExamGroupInfo> examGroupInfos = new ArrayList<ExamGroupInfo>();
    List<String> examGroupInfoNames;
    private HashMap<Integer, String> examGroupInfoHashMap;
    private ArrayAdapter<String> examGroupInfoAdapter;

    Spinner groupExamSpinner;
    private ProgressDialog progressDialog;

    Button submitExamButton;

    int selectedMaskingId;
    int selectedSubjectPosition;

    public ExamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_exam, container, false);

       getActivity().setTitle(R.string.exam);

        apiInterface = ServiceGenerator.createService(ApiInterface.class);
        examMarksLayout = rootView.findViewById(R.id.examMarksLayout);
        examMarksTextView = rootView.findViewById(R.id.examMarksTextView);
        examDateTextView = rootView.findViewById(R.id.examDateTextView);
        groupExamSpinner = rootView.findViewById(R.id.groupExamSpinner);
        groupInfo = new ArrayList<GroupInfo>();
        examGroupInfos = new ArrayList<ExamGroupInfo>();

        listView  = rootView.findViewById(R.id.exam_list_view);
        sp=  rootView.findViewById(R.id.groupInfoExSpinner);
        //GetGroupInfo();
        subjectLists = TeacherSession.subjects;
        subjectNames = new ArrayList<String>();
        //examTypes = new ArrayList<>();

        subjectNames.add("Select a class");

        if (subjectLists != null) {
            for (int i = 0; i < subjectLists.size(); i++) {
                subjectNames.add(subjectLists.get(i).SubjectName + " (" + getString(R.string._class) + " " + TeacherSession.classNames.get(i) + ", " + getString(R.string._sec) + " " + TeacherSession.sectionNames.get(i) + ", " + getString(R.string._shift) + " " + TeacherSession.shifts.get(i) + ")");

            }
        }

        GetExamTypes();

        groupInfoAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subjectNames);
        groupInfoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(groupInfoAdapter);
        sp.setOnItemSelectedListener(this);



        submitExamButton=  rootView.findViewById(R.id.submitExamButton);
        submitExamButton.setOnClickListener(this);

        return rootView;
    }

    /*private void SaveUpdateExam()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait while loading...");
        progressDialog.setCancelable(false);
        if (NetworkAvailability.isNetworkAvailable(getActivity())){
            progressDialog.show();
            Call<CommonResponse> commonResponseCall = apiInterface.updateExamInfo(examInfoList);

            commonResponseCall.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                    try {

                        if (response.body()!=null){
                            if (response.body().getMessageCode().equals("Y")){
                                progressDialog.dismiss();
                                //UserInfo userInfo = response.body().getContentUserInfo();
                                Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                ViewStudentList();

                            } else {
                                Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Unable to fetch data, try again.", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    } catch (Exception e){
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
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

   /* private void GetGroupInfo()
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

    }

    private void GetExamGroupInfo()
    {
        examGroupInfoNames =new ArrayList<String>();
        examGroupInfoNames.add("Select an exam");
        examGroupInfoHashMap =new HashMap<>();
        if (NetworkAvailability.isNetworkAvailable(getActivity())){

            //     listView  = (ListView)rootView.findViewById(R.id.notice_list_view);

            GroupBasic groupBasic = new GroupBasic(groupId, UserSession.UserId,"");

            Call<ExamGroupResponse> commonResponseCall = apiInterface.getExamGroupInfoList(groupBasic);

            commonResponseCall.enqueue(new Callback<ExamGroupResponse>() {
                @Override
                public void onResponse(Call<ExamGroupResponse> call, @NonNull Response<ExamGroupResponse> response) {
                    try {

                        if (response.body()!=null){
                            if (response.body().getMessageCode().equals("Y")){

                                ///adapter=new ArrayAdapter<GroupInfo>(getActivity(),android.R.layout.simple_list_item_1,response.body().getGroupInfo());
                                examGroupInfos = response.body().getContentExamGroups();

                                for (int i = 0; i < examGroupInfos.size(); i++)
                                {

                                    examGroupInfoNames.add(examGroupInfos.get(i).getExamTitle());
                                    examGroupInfoHashMap.put(i, examGroupInfos.get(i).getExamGroupId());
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
                public void onFailure(Call<ExamGroupResponse> call, Throwable t) {

                    Toast.makeText(getActivity(),"Failure, Unable to fetch data, try again.", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            Toast.makeText(getActivity(),"No internet connection.", Toast.LENGTH_SHORT).show();

        }

    }*/

    private void ViewStudentList()
    {

        if (NetworkAvailability.isNetworkAvailable(getActivity())){

            listView  = rootView.findViewById(R.id.exam_list_view);

            ExamGroupBasic groupBasic = new ExamGroupBasic(groupId, examGroupId , UserSession.UserId,"");

            Call<ExamInfoResponse> commonResponseCall = apiInterface.getExamInfoByGroup(groupBasic);

            commonResponseCall.enqueue(new Callback<ExamInfoResponse>() {
                @Override
                public void onResponse(Call<ExamInfoResponse> call, @NonNull Response<ExamInfoResponse> response) {
                    try {

                        if (response.body()!=null){
                            if (response.body().getMessageCode().equals("Y")){

                                examInfoList = response.body().getContentExamInfo();
                                for (int i = 0; i < examInfoList.size(); i++)
                                {
                                    examInfoList.get(i).setExamMarks(examMarks);
                                    examInfoList.get(i).setExamGroupId(examGroupId);
                                    examInfoList.get(i).setStudentGroupId(groupId);
                                    examInfoList.get(i).setTeacherId(UserSession.UserId);
                                }

                                adapter = new StudentExamListAdapter(getActivity(), response.body().getContentExamInfo());
                                listView.setAdapter(adapter);

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
                public void onFailure(Call<ExamInfoResponse> call, Throwable t) {

                    Toast.makeText(getActivity(),"Failure, Unable to fetch data, try again.", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            Toast.makeText(getActivity(),"No internet connection.", Toast.LENGTH_SHORT).show();

        }

    }

   public void GetExamTypes(){

       Call<List<ExamType>> getAllExamTypes = apiInterface.getAllExamTypes("Exam/getAllExamTypes");

       getAllExamTypes.enqueue(new Callback<List<ExamType>>() {
           @Override
           public void onResponse(Call<List<ExamType>> call, Response<List<ExamType>> response) {
               if(response.body()!=null){
                   //Toast.makeText(getActivity(),"Okay",Toast.LENGTH_LONG).show();
                   examTypes = response.body();
               }
               else {
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

        if(spinnerId==R.id.groupInfoExSpinner)
        {
            String docName = adapterView.getItemAtPosition(i).toString();
            if (docName.equals("Select a class")) {
                listView.setVisibility(View.GONE);
                groupExamSpinner.setVisibility(View.GONE);
                examMarksLayout.setVisibility(View.GONE);
                submitExamButton.setVisibility(View.GONE);

            } else {
                groupExamSpinner=  rootView.findViewById(R.id.groupExamSpinner);


                List<String> examTypesStr = new ArrayList<>();
                examTypesStr.add("Select an exam");

                if(examTypes!=null){
                    for(int j=0; j<examTypes.size(); j++){
                        examTypesStr.add(examTypes.get(j).ExamTitle);
                    }
                    examGroupInfoAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, examTypesStr);
                    examGroupInfoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    groupExamSpinner.setAdapter(examGroupInfoAdapter);

                }


                //GetExamGroupInfo();
                 //groupExamSpinner.setOnItemSelectedListener(this);

                examMarksLayout.setVisibility(View.GONE);
                submitExamButton.setVisibility(View.GONE);
                groupExamSpinner.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);

                selectedSubjectPosition = i-1;
                selectedMaskingId = subjectLists.get(i-1).MaskingSubjectId;

            }


        }
        else if(spinnerId==R.id.groupExamSpinner)
        {
            String docName = adapterView.getItemAtPosition(i).toString();
            if (docName.equals("Select an exam")) {
                listView.setVisibility(View.GONE);
                examMarksLayout.setVisibility(View.GONE);
                submitExamButton.setVisibility(View.GONE);
            } else {
                String eId = examGroupInfoHashMap.get(i-1);
                examGroupId = eId;
                examName= subjectNames.get(selectedSubjectPosition);
                examMarks = "100";
                String exmDate =  "4-6-2019";
                if(examMarks.equals(null))
                {
                    examMarks="";
                }
                if(exmDate.equals(null))
                {
                    exmDate="";
                }

                examMarksTextView = rootView.findViewById(R.id.examMarksTextView);
                examMarksTextView.setText(examMarks.trim());
                examDateTextView = rootView.findViewById(R.id.examDateTextView);
                examDateTextView.setText(" ( Date: "+exmDate.replaceAll("12:00:00 AM","").trim()+" ) ");

                examMarksLayout.setVisibility(View.VISIBLE);
                submitExamButton.setVisibility(View.VISIBLE);
                groupExamSpinner.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                ViewStudentList();
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        if(id==R.id.submitExamButton)
        {
           // SaveUpdateExam();
        }


    }
}
