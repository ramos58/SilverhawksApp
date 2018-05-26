package com.example.alcra.silverhawksapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by alcra on 01/04/2018.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton;
    private EditText emailText, passText;

    private FirebaseAuth auth;

    private ConstraintLayout activity_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //View
        loginButton = findViewById(R.id.bt_login);
        emailText = findViewById(R.id.et_email);
        passText = findViewById(R.id.et_pass);
        activity_login = findViewById(R.id.activity_login);

        loginButton.setOnClickListener(this);

        //Inicia Firebase Auth
        auth = FirebaseAuth.getInstance();

        //Verifica se já está logado, se sim vai para a tela principal
        if(auth.getCurrentUser() != null)
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

    public void onClick(View view) {
        loginUser(emailText.getText().toString(),passText.getText().toString());
    }

    private void loginUser(String email, final String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            if(password.length() < 6)
                            {
                                Snackbar snackBar = Snackbar.make(activity_login,"Password length must be over 6",Snackbar.LENGTH_SHORT);
                                snackBar.show();
                            }
                            else{
                                Snackbar snackBar = Snackbar.make(activity_login,"ERRO",Snackbar.LENGTH_SHORT);
                                snackBar.show();
                            }
                        }
                        else{
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }

                    }
                });
    }
}
