package com.example.roopanc.rc_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;

import static com.example.roopanc.rc_demo.SplashActivity.FIRST_LAUNCH_STR;
import static com.example.roopanc.rc_demo.SplashActivity.PREF_NAME;
import static com.example.roopanc.rc_demo.SplashActivity.USER_TYPE;

public class ChooseActivity extends AppCompatActivity {


    Button customerBT, supervisorBT;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    String user_type;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        customerBT = findViewById(R.id.customerbt);
        supervisorBT = findViewById(R.id.supervisorbt);

        final Intent intent = new Intent(ChooseActivity.this, HomeActivity.class);

        customerBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_type = customerBT.getText().toString();
                editor.putString(USER_TYPE, user_type);
                editor.putBoolean(FIRST_LAUNCH_STR, false);
                editor.commit();
                mFirebaseAnalytics.setUserProperty("user_type", user_type);
                startActivity(intent);
                finish();
            }
        });

        supervisorBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_type = supervisorBT.getText().toString();
                editor.putString(USER_TYPE, user_type);
                editor.putBoolean(FIRST_LAUNCH_STR, false);
                editor.commit();
                mFirebaseAnalytics.setUserProperty("user_type", user_type);
                startActivity(intent);
                finish();
            }
        });




    }


}
