package com.teacher.modern.modernteacher.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.connectivity.ApiInterface;
import com.teacher.modern.modernteacher.connectivity.NetworkAvailability;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;
import com.teacher.modern.modernteacher.database.MyDatabase;
import com.teacher.modern.modernteacher.models.User;
import com.teacher.modern.modernteacher.models.viewmodels.LoginVM;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewLoginActivity extends AppCompatActivity {

    TextInputLayout passwordEditText,phoneEditText;
    Button loginButton,changeLanguage;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSharedpref();
        setContentView(R.layout.activity_new_login);

        apiInterface = ServiceGenerator.createService(ApiInterface.class);

        loginButton =  findViewById(R.id.loginButton);
        changeLanguage = findViewById(R.id.changeLanguage);
        passwordEditText =  findViewById(R.id.passwordEditText);
        phoneEditText =  findViewById(R.id.emailEditText);

        onClick();
    }

    private void onClick() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!NetworkAvailability.isNetworkAvailable(NewLoginActivity.this)) {
                    //ShowDialogs.noInternetDialog(this.getActivity());
                    Toast.makeText(NewLoginActivity.this, R.string.no_internet,  Toast.LENGTH_LONG).show();
                }
                else {
                    String phone_text=phoneEditText.getEditText().getText().toString().trim();
                    String password=passwordEditText.getEditText().getText().toString().trim();
                    if (phone_text.equals(null)||phone_text.equals(""))
                    {
                        Toast.makeText(NewLoginActivity.this, R.string.please_enter_uid,  Toast.LENGTH_LONG).show();
                    }
                    else  if (password.equals(null)||password.equals(""))
                    {
                        Toast.makeText(NewLoginActivity.this, R.string.please_enter_pass,  Toast.LENGTH_LONG).show();
                    }
                    else{
                        long phone = Long.parseLong(phone_text);
                        initiateLogin(phone,password);
                    }


                }
            }
        });


        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] languages = {"English","বাংলা"};
                AlertDialog.Builder builder = new AlertDialog.Builder(NewLoginActivity.this);

                builder.setTitle("Select Language");
                builder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            setLocale("en");
                            recreate();
                        }
                        else if(i==1){
                            setLocale("bn");
                            recreate();
                        }
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });
    }

    public void initiateLogin(final long phone, final String password){
        //  hideKeyboard();

        progressDialog = new ProgressDialog(NewLoginActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        if (NetworkAvailability.isNetworkAvailable(NewLoginActivity.this)){
            progressDialog.show();

            LoginVM loginVM = new LoginVM(phone,password,2);
            Call<User> getLoginResponse = apiInterface.getLoginResponse(loginVM);

            getLoginResponse.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.body()!=null){

                            MyDatabase myDatabase = new MyDatabase(NewLoginActivity.this);
                            myDatabase.insertLoginInfo(phoneEditText.getEditText().getText().toString(), passwordEditText.getEditText().getText().toString(),response.body().UId);

                            Intent intent = new Intent(NewLoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            progressDialog.hide();
                            hideKeyboard();



                    }
                    else {
                        
                        progressDialog.dismiss();
                        hideKeyboard();
                        Toast.makeText(NewLoginActivity.this, R.string.error_message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(NewLoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(NewLoginActivity.this,R.string.no_internet, Toast.LENGTH_SHORT).show();
        }

    }

    private  void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences sharedPreferences = getSharedPreferences("Settings",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language",lang);
        editor.apply();
    }

    public  void loadSharedpref(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings",MODE_PRIVATE);
        String lang = sharedPreferences.getString("language","");
        setLocale(lang);

    }
}
