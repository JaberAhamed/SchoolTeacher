package com.teacher.modern.modernteacher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.teacher.modern.modernteacher.activities.MainActivity;
import com.teacher.modern.modernteacher.activities.NewLoginActivity;
import com.teacher.modern.modernteacher.connectivity.NetworkAvailability;
import com.teacher.modern.modernteacher.database.MyDatabase;
import com.teacher.modern.modernteacher.service_models.security_models.UserSession;

import io.fabric.sdk.android.Fabric;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    int trackInternet = 0;
    String android_id="";

    public static final int PERMISSIONS_REQUEST = 100;
    ProgressBar progressBar;
    int progressStatus = 0;
    TextView progressPercentage;
    Handler handler = new Handler();
    private Timer autoUpdate;
    private MyDatabase myDatabase;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        //    String uniqueID = UUID.randomUUID().toString();
        UserSession.AndroidId = android_id;
        myDatabase = new MyDatabase(SplashActivity.this);
        Cursor cursor =  myDatabase.getAllData();
        while (cursor.moveToNext()){
            email = cursor.getString(0);
        }

    }


    private void updateOnCreate() {
        // your logic here
        if (NetworkAvailability.isNetworkAvailable(SplashActivity.this)) {

            progressPercentage =  findViewById(R.id.load_per);
            progressBar =  findViewById(R.id.progressBar1);
            progressBar.setScaleY(3f);
            progressBar.getProgressDrawable().setColorFilter(Color.rgb(122, 187, 106), PorterDuff.Mode.SRC_IN);
            new Thread(new Runnable() {
                public void run() {
                    while (progressStatus < 100) {
                        progressStatus += 1;

                        if (progressStatus == 10) {

                            // functions
                        }
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressStatus);
                                progressPercentage.setText(progressStatus + "%");
                            }
                        });
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    //Log.d("userCodeN",SecurityInfo.getUserCode());
                    if (progressStatus == 100) {

                        if (email== null ){
                            Intent intent = new Intent(SplashActivity.this, NewLoginActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else {
                            try {
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();


                            } catch (Exception e) {
                                Log.d("error", e + "");

                            }
                        }

                    }
                }
            }).start();
        } else {
            if (trackInternet >= 1) {
                finish();
                startActivity(getIntent());

            }
            //    ShowDialogs.noInternetDialog(this);
            trackInternet++;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        autoUpdate = new Timer();

        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        updateOnCreate();
                    }
                });
            }
        }, 0, 5000); // updates each 40 secs
    }

    @Override
    public void onPause() {
        autoUpdate.cancel();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        finish();
        System.exit(0);

        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
    }



}
