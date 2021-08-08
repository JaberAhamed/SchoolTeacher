package com.teacher.modern.modernteacher.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;
import com.teacher.modern.modernteacher.database.MyDatabase;
import com.teacher.modern.modernteacher.fragments.LoginFragment;
import com.teacher.modern.modernteacher.models.TeacherSession;
import com.teacher.modern.modernteacher.models.User;
import com.teacher.modern.modernteacher.models.viewmodels.LoginVM;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;
    private String lastFragment;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        apiInterface = ServiceGenerator.createService(ApiInterface.class);

            MyDatabase myDatabase = new MyDatabase(this);
            if(myDatabase.getEmail().equals("Not found")){
                manager = getFragmentManager();
                currentFragment = new LoginFragment();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.login_fragment,currentFragment);
                transaction.addToBackStack("LoginFragment");
                transaction.commit();
            }
            else{
                String phone_text = myDatabase.getEmail();
                String password = myDatabase.getPassword();

                long phone = Long.parseLong(phone_text);

                LoginVM loginVM = new LoginVM(phone,password,2);
                Call<User> getLoginResponse = apiInterface.getLoginResponse(loginVM);

                getLoginResponse.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.body()!=null){
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

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            manager = getFragmentManager();
                            currentFragment = new LoginFragment();
                            transaction = manager.beginTransaction();
                            transaction.replace(R.id.login_fragment,currentFragment);
                            transaction.addToBackStack("LoginFragment");
                            transaction.commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });



            }



    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);

            int pid = android.os.Process.myPid();
            android.os.Process.killProcess(pid);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click again to go Exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }


}
