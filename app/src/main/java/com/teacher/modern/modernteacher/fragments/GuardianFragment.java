package com.teacher.modern.modernteacher.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.adapter.GuardianStudentListAdapter;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;
import com.teacher.modern.modernteacher.connectivity.NetworkAvailability;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;
import com.teacher.modern.modernteacher.models.Subject;
import com.teacher.modern.modernteacher.models.TeacherSession;
import com.teacher.modern.modernteacher.models.viewmodels.StudentGuardianVM;
import com.teacher.modern.modernteacher.service_models.notice_models.GroupInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuardianFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View rootView;
    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;
    private ApiInterface apiInterface;

    private ListView listView;
    private GuardianStudentListAdapter adapter;
    List<StudentGuardianVM> memberList;

    List<Subject> subjectLists;


    //List<String> groupInfoNames;
    List<String> subjectNames;

    String groupId = "";
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialogMembers;
    ArrayList<GroupInfo> groupInfo = new ArrayList<GroupInfo>();
    List<String> groupInfoNames;
    private HashMap<Integer, String> groupInfoHashMap;
    private ArrayAdapter<String> groupInfoAdapter;
    Spinner groupInfoMembersSpinner;
    private LinearLayout noDataFoundLayout;

    public GuardianFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_members, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.guardian_list);
        apiInterface = ServiceGenerator.createService(ApiInterface.class);

        groupInfo = new ArrayList<GroupInfo>();
        listView = rootView.findViewById(R.id.member_list_view);
        noDataFoundLayout = rootView.findViewById(R.id.noDataFoundLayoutID);

        groupInfoMembersSpinner = rootView.findViewById(R.id.groupInfoMembersSpinner);
        //GetGroupInfo();
        subjectLists = new ArrayList<>();
        subjectLists = TeacherSession.subjects;
        subjectNames = new ArrayList<String>();

        subjectNames.add(getString(R.string.select_a_class));

        if (subjectLists != null) {
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
            groupInfoMembersSpinner.setAdapter(groupInfoAdapter);
            groupInfoMembersSpinner.setOnItemSelectedListener(this);
        }


        return rootView;
    }


    private void ViewMemberList(int position) {
        position = position - 1;
        progressDialogMembers = new ProgressDialog(getActivity());
        progressDialogMembers.setTitle(getString(R.string.loading));
        progressDialogMembers.setMessage(getString(R.string.please_wait));
        progressDialogMembers.setCancelable(false);
        if (NetworkAvailability.isNetworkAvailable(getActivity())) {
            progressDialogMembers.show();
            listView = rootView.findViewById(R.id.member_list_view);

            Call<List<StudentGuardianVM>> getGuardianLists = apiInterface.getGuardianLists("Guardian/getGuardianLists?classId=" + subjectLists.get(position).ClassId + "&sectionId=" + subjectLists.get(position).SectionId);
            getGuardianLists.enqueue(new Callback<List<StudentGuardianVM>>() {
                @Override
                public void onResponse(Call<List<StudentGuardianVM>> call, Response<List<StudentGuardianVM>> response) {
                    progressDialogMembers.dismiss();
                    if (response.body().size()>0) {

                        memberList = response.body();
                        listView.setVisibility(View.VISIBLE);
                        adapter = new GuardianStudentListAdapter(getActivity(), memberList);
                        listView.setAdapter(adapter);
                        noDataFoundLayout.setVisibility(View.GONE);
                    } else {

                        listView.setVisibility(View.GONE);
                        noDataFoundLayout.setVisibility(View.VISIBLE);

                    }

                }

                @Override
                public void onFailure(Call<List<StudentGuardianVM>> call, Throwable t) {

                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        } else {
            Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            listView.setVisibility(View.GONE);
            noDataFoundLayout.setVisibility(View.VISIBLE);

        }

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String docName = adapterView.getItemAtPosition(position).toString();

        if (docName.equals(getString(R.string.select_a_class))) {

        } else {
            if(position>0){

                if(NetworkAvailability.isNetworkAvailable(getActivity())){

                    ViewMemberList(position);

                }
                else{

                    ViewMemberList(position);


                }

            }



        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
