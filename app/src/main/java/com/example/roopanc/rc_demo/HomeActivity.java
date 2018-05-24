package com.example.roopanc.rc_demo;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;

import static com.example.roopanc.rc_demo.SplashActivity.FIRST_LAUNCH_STR;
import static com.example.roopanc.rc_demo.SplashActivity.PREF_NAME;

public class HomeActivity extends AppCompatActivity {

    TextView textView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    ImageView imageView;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    FirebaseRemoteConfigSettings remoteConfigSettings;
    String toolbar_color = "#4f8ff7";
    String welcome_text = "Welcome <user> !";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build();

        mFirebaseRemoteConfig.setConfigSettings(remoteConfigSettings);

        HashMap<String, Object> defaults = new HashMap<>();
        defaults.put("toolbar_color", toolbar_color);
        defaults.put("welcome_text", welcome_text);
        mFirebaseRemoteConfig.setDefaults(defaults);

        fetchConfig();

        toolbar = findViewById(R.id.toolbarhome);
        toolbar.setTitle("R`C");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setBackgroundColor(Color.parseColor(toolbar_color));
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.welcometv);
        imageView = findViewById(R.id.imagewelcome);

        /*if (BuildConfig.FLAVOR.equals("Free")){
            imageView.setVisibility(View.GONE);
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_out)
        {
            editor.putBoolean(FIRST_LAUNCH_STR, true);
            editor.commit();
            finish();
            Toast.makeText(this, "sign out", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void fetchConfig() {
        mFirebaseRemoteConfig.fetch(0)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            mFirebaseRemoteConfig.activateFetched();
                            if (mFirebaseRemoteConfig.getString("toolbar_color") != null)
                            {
                                toolbar_color = mFirebaseRemoteConfig.getString("toolbar_color");
                                welcome_text = mFirebaseRemoteConfig.getString("welcome_text");
                                editor.putString("toolbar_color", toolbar_color);
                                editor.putString("welcome_text", welcome_text);
                                editor.commit();

                                toolbar.setBackgroundColor(Color.parseColor(toolbar_color));
                                textView.setText(welcome_text);
                                textView.setTextColor(Color.parseColor(toolbar_color));

                            }
                        }
                    }
                });
    }
}
