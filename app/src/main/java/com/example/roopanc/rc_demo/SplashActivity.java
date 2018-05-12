package com.example.roopanc.rc_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    public static int TIME_OUT = 2000;
    public static String PREF_NAME = "pref_name";
    public static String FIRST_LAUNCH_STR = "first_launch";
    public static String USER_TYPE = "first_launch";
    boolean FIRST_LAUNCH = true;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        FIRST_LAUNCH = sharedPreferences.getBoolean(FIRST_LAUNCH_STR, true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FIRST_LAUNCH) {
                    Intent intent = new Intent(SplashActivity.this, ChooseActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, TIME_OUT);
    }
}
