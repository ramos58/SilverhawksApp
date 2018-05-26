package com.example.alcra.silverhawksapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by alcra on 01/04/2018.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserLogged();
            }
        }, 1000);
    }

    private void checkUserLogged() {

        UserPreferences preferences = new UserPreferences(getApplicationContext());

        if (preferences.getUserLogged()) {
            Intent it = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(it);
        } else {
            Intent it = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(it);
        }
        finish();
    }
}
