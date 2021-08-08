package com.teacher.modern.modernteacher.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;


import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;
import com.teacher.modern.modernteacher.connectivity.NetworkAvailability;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;
import com.teacher.modern.modernteacher.models.User;
import com.teacher.modern.modernteacher.models.viewmodels.LoginVM;
import com.teacher.modern.modernteacher.service_models.security_models.UserSession;

import java.util.Locale;

import retrofit2.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;
    View rootView;

    TextInputLayout passwordEditText,phoneEditText;
    Button loginButton,changeLanguage;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loadSharedpref();
        rootView = inflater.inflate(R.layout.fragment_login, container, false);


        apiInterface = ServiceGenerator.createService(ApiInterface.class);

        loginButton =  rootView.findViewById(R.id.loginButton);
        changeLanguage = rootView.findViewById(R.id.changeLanguage);
        loginButton.setOnClickListener(this);
        changeLanguage.setOnClickListener(this);

        passwordEditText =  rootView.findViewById(R.id.passwordEditText);
        phoneEditText =  rootView.findViewById(R.id.emailEditText);

        if(UserSession.UserLoggedIn.equals("Y"))
        {
            GoToHomeFragment();
        }



        return rootView;
    }

    public void initiateLogin(final long phone, final String password){
      //  hideKeyboard();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        if (NetworkAvailability.isNetworkAvailable(getActivity())){
            progressDialog.show();

            LoginVM loginVM = new LoginVM(phone,password,2);
            Call<User> getLoginResponse = apiInterface.getLoginResponse(loginVM);

            /*getLoginResponse.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.body()!=null){
                        //Toast.makeText(getActivity(),"Login Successfull",Toast.LENGTH_LONG).show();
                        MyDatabase myDatabase = new MyDatabase(getActivity());
                        myDatabase.insertLoginInfo(phoneEditText.getEditText().getText().toString(), passwordEditText.getEditText().getText().toString());

                        TeacherSession.UserLoggedIn = "Y";
                        TeacherSession.Birthday = response.body().Birthday;
                        TeacherSession.Email = response.body().Email;
                        TeacherSession.FirstName = response.body().FirstName;
                        TeacherSession.Gender = response.body().Gender;
                        TeacherSession.LastName = response.body().LastName;
                        TeacherSession.Password = response.body().Password;
                        TeacherSession.Status = response.body().Status;
                        TeacherSession.UId = response.body().UId;
                        TeacherSession.UserTypeId = response.body().UserTypeId;

                        manager = getFragmentManager();
                        currentFragment = new HomeFragment();
                        transaction = manager.beginTransaction();
                        transaction.replace(R.id.login_fragment,currentFragment);
                        transaction.addToBackStack("HomeFragment");
                        transaction.commit();
                        progressDialog.hide();
                        hideKeyboard();
                    }
                    else {

                            manager = getFragmentManager();
                            currentFragment = new LoginFragment();
                            transaction = manager.beginTransaction();
                            transaction.replace(R.id.login_fragment,currentFragment);
                            transaction.addToBackStack("LoginFragment");
                            transaction.commit();
                            progressDialog.dismiss();
                        hideKeyboard();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });*/
            }
        else {
            Toast.makeText(getActivity(),R.string.no_internet, Toast.LENGTH_SHORT).show();
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
    }

    private void GoToHomeFragment()
    {
        manager = getFragmentManager();
        currentFragment = new HomeFragment();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.main_fragment,currentFragment);
        transaction.addToBackStack("HomeFragment");
        transaction.commit();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id== R.id.loginButton){

            if(!NetworkAvailability.isNetworkAvailable(this.getActivity())) {
                //ShowDialogs.noInternetDialog(this.getActivity());
                Toast.makeText(getActivity(), R.string.no_internet,  Toast.LENGTH_LONG).show();
            }
            else {
                String phone_text=phoneEditText.getEditText().getText().toString().trim();
                String password=passwordEditText.getEditText().getText().toString().trim();
                if (phone_text.equals(null)||phone_text.equals(""))
                {
                    Toast.makeText(getActivity(), R.string.please_enter_uid,  Toast.LENGTH_LONG).show();
                }
                else  if (password.equals(null)||password.equals(""))
                {
                    Toast.makeText(getActivity(), R.string.please_enter_pass,  Toast.LENGTH_LONG).show();
                }
                else{
                    long phone = Long.parseLong(phone_text);
                    initiateLogin(phone,password);
                }


            }
        }

        if(id== R.id.changeLanguage){
            final String[] languages = {"English","বাংলা"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Select Language");
            builder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(i==0){
                        setLocale("en");
                        getActivity().recreate();
                    }
                    else if(i==1){
                        setLocale("bn");
                        getActivity().recreate();
                    }
                }
            });

            AlertDialog mDialog = builder.create();
            mDialog.show();

        }
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(configuration,getActivity().getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Settings",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language",lang);
        editor.apply();
    }

    public  void loadSharedpref(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Settings",MODE_PRIVATE);
        String lang = sharedPreferences.getString("language","");
        setLocale(lang);

    }


}
