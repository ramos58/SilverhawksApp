package com.example.alcra.silverhawksapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
    }

    public void onClick(View view) {
        if (valid()){
            loginUser(emailText.getText().toString(),passText.getText().toString());
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alerta");
            builder.setMessage("Todos os campos devem ser preenchidos!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        }
    }

    private boolean valid() {
        if (emailText.getText().toString().isEmpty()){
            return false;
        }else if (passText.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }

    private void loginUser(String email, final String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Logando...");
        dialog.show();

        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();

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
