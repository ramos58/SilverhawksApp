package com.example.alcra.silverhawksapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by alcra on 01/04/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        //Inicia Firebase Auth
        auth = FirebaseAuth.getInstance();
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

        //Verifica se já está logado, se sim vai para a tela principal
        if(auth.getCurrentUser() != null)
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        else
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }
}
