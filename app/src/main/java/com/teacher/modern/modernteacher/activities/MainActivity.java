package com.teacher.modern.modernteacher.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.database.MyDatabase;
import com.teacher.modern.modernteacher.fragments.GuardianFragment;
import com.teacher.modern.modernteacher.fragments.HomeFragment;
import com.teacher.modern.modernteacher.fragments.New;
import com.teacher.modern.modernteacher.fragments.NoticeListFragment;
import com.teacher.modern.modernteacher.fragments.PostNoticeFragment;
import com.teacher.modern.modernteacher.fragments.StatisticsFragment;
import com.teacher.modern.modernteacher.fragments.newAttendanceFragment;
import com.teacher.modern.modernteacher.fragments.newExamFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {
    boolean doubleBackToExitPressedOnce = false;
    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;
    private String lastFragment;
    AlertDialog mDialog;
    MyDatabase myDatabase;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSharedpref();

        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        myDatabase = new MyDatabase(this);
        Cursor cursor =  myDatabase.getAllData();
        while (cursor.moveToNext()){
            email = cursor.getString(0);
        }

            manager = getFragmentManager();
            manager.addOnBackStackChangedListener(this);
            currentFragment = new HomeFragment();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment,currentFragment);
            transaction.addToBackStack("HomeFragment");
            transaction.commit();


    }

    @Override
    protected void onPause() {

        super.onPause();
        if(mDialog!=null){
            mDialog.dismiss();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else {

            int count = manager.getBackStackEntryCount();
            if (count > 0) {
                FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(count - 1);
                lastFragment = entry.getName();

                if(lastFragment.equals("LoginFragment")){
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
                else if(lastFragment.equals("HomeFragment")){
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
                else if(lastFragment.equals("GuardianFragment")){
                    manager = getFragmentManager();
                    manager.addOnBackStackChangedListener(this);
                    currentFragment = new HomeFragment();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.main_fragment,currentFragment);
                    transaction.addToBackStack("HomeFragment");
                    transaction.commit();
                }
                else if(lastFragment.equals("ChatFragment")){
                    manager = getFragmentManager();
                    manager.addOnBackStackChangedListener(this);
                    currentFragment = new GuardianFragment();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.main_fragment,currentFragment);
                    transaction.addToBackStack("GuardianFragment");
                    transaction.commit();
                }
                else if(lastFragment.equals("NewMembersFragment")){
                    manager = getFragmentManager();
                    manager.addOnBackStackChangedListener(this);
                    currentFragment = new GuardianFragment();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.main_fragment,currentFragment);
                    transaction.addToBackStack("GuardianFragment");
                    transaction.commit();
                }
                else{
                    manager = getFragmentManager();
                    manager.addOnBackStackChangedListener(this);
                    currentFragment = new HomeFragment();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.main_fragment,currentFragment);
                    transaction.addToBackStack("HomeFragment");
                    transaction.commit();
                }


            } else {
                super.onBackPressed();
            }
            // Back Pressed Custom End

        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final String[] languages = {"English","বাংলা"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Select Language");
            builder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(i==0){
                        Locale locale = new Locale("en");
                        Locale.setDefault(locale);

                        Configuration configuration = new Configuration();
                        configuration.locale = locale;
                        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

                        recreate();


                    }
                    else if(i==1){
                        Locale locale = new Locale("bn");
                        Locale.setDefault(locale);

                        Configuration configuration = new Configuration();
                        configuration.locale = locale;
                        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

                        recreate();


                    }
                }
            });

            mDialog = builder.create();
            mDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            manager = getFragmentManager();
            manager.addOnBackStackChangedListener(this);
            currentFragment = new HomeFragment();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment,currentFragment);
            transaction.addToBackStack("HomeFragment");
            transaction.commit();
        } else if (id == R.id.nav_post_notice) {

                manager = getFragmentManager();
                manager.addOnBackStackChangedListener(this);
                currentFragment = new PostNoticeFragment();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, currentFragment);
                transaction.addToBackStack("PostNoticeFragment");
                transaction.commit();


        } else if (id == R.id.nav_all_notice) {

                manager = getFragmentManager();
                manager.addOnBackStackChangedListener(this);
                currentFragment = new NoticeListFragment();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, currentFragment);
                transaction.addToBackStack("NoticeListFragment");
                transaction.commit();


        } else if (id == R.id.nav_members) {

                manager = getFragmentManager();
                manager.addOnBackStackChangedListener(this);
                currentFragment = new GuardianFragment();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, currentFragment);
                transaction.addToBackStack("GuardianFragment");
                transaction.commit();

        } else if (id == R.id.nav_exams) {

                manager = getFragmentManager();
                manager.addOnBackStackChangedListener(this);
                currentFragment = new newExamFragment();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, currentFragment);
                transaction.addToBackStack("ExamFragment");
                transaction.commit();

        } else if (id == R.id.nav_attandance) {

                manager = getFragmentManager();
                manager.addOnBackStackChangedListener(this);
                currentFragment = new newAttendanceFragment();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, currentFragment);
                transaction.addToBackStack("AttendanceFragment");
                transaction.commit();

        }

        else if (id == R.id.nav_statistics) {

                manager = getFragmentManager();
                manager.addOnBackStackChangedListener(this);
                currentFragment = new StatisticsFragment();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, currentFragment);
                transaction.addToBackStack("StatisticsFragment");
                transaction.commit();

        }

        else if (id == R.id.nav_calender) {
                manager = getFragmentManager();
                manager.addOnBackStackChangedListener(this);
                currentFragment = new New();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, currentFragment);
                transaction.addToBackStack("New");
                transaction.commit();

        }
        else if (id == R.id.nav_change_language) {
            final String[] languages = {"English","বাংলা"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

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

        else if (id == R.id.nav_logout) {
            myDatabase.deleteGuardian(email);
            Intent intent = new Intent(MainActivity.this,NewLoginActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackStackChanged() {
        int count = manager.getBackStackEntryCount();
        for(int i = count-1; i >= 0 ; i-- ){
            FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(i);
            lastFragment = entry.getName();
            //Toast.makeText(this,entry.getName(),Toast.LENGTH_SHORT).show();
            //  Log.i(TAG, "FoundFragment: "+i +" --> "+ entry.getName());
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
   /* @Override
    protected void onDestroy() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        super.onDestroy();
    }*/
}
