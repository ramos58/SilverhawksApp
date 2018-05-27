package com.example.alcra.silverhawksapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alcra on 11/04/2018.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private DocumentReference mDocRef;

    private Toolbar toolbar;
    private FirebaseAuth auth;
    private String UID;
    private Button EnviarButton;
    EditText userEditText;
    EditText numberEditText;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.include);

        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();

        userEditText = findViewById(R.id.et_nome);
        numberEditText = findViewById(R.id.et_numero);
        EnviarButton = findViewById(R.id.bt_editarProfile);
        UID = auth.getUid();
        mDocRef = db.document("usuários/"+UID);

        EnviarButton.setOnClickListener(this);
    }

    protected void onStart() {
        super.onStart();

        mDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    String user = documentSnapshot.getString("nome");
                    String numero = documentSnapshot.getString("numero");
                    userEditText.setText(user);
                    numberEditText.setText(numero);
                } else if (e != null){
                    Log.w("gravação", "Got an exception!", e);
                }
            }
        });

    }

    private void logoutUser() {
        auth.signOut();
        if(auth.getCurrentUser() == null)
        {
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_logout:
                logoutUser();
                break;
            case R.id.menu_perfil:
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        Map<String, Object> user = new HashMap<>();
        user.put("nome", userEditText.getText().toString());
        user.put("numero", numberEditText.getText().toString());

        db.document("usuários/"+UID).set(user);
    }
}
