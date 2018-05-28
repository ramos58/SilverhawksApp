package com.example.alcra.silverhawksapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alcra.silverhawksapp.entities.Atleta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private FirebaseAuth auth;
    private String UID;
    private Button chamadaButton;
    private TextView userTextView;
    private TextView numberTextView;

    //Retorna a referência ao root (raiz) da árvore JSON no Firebase.
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = rootRef.child("user");
    DatabaseReference numberRef = userRef.child("number");
    private DocumentReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.include);
        toolbar.setTitle("Main Activity");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        auth = FirebaseAuth.getInstance();

        userTextView = findViewById(R.id.tvUser);
        numberTextView = findViewById(R.id.tvNumber);
        UID = auth.getUid();
        mDocRef = FirebaseFirestore.getInstance().document("usuários/"+UID);

        chamadaButton = findViewById(R.id.bt_chamada);
        chamadaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ChamadaActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
            if(documentSnapshot.exists()){
                String user = documentSnapshot.getString("nome");
                String numero = documentSnapshot.getString("numero");
                userTextView.setText(user);
                numberTextView.setText("#"+numero);
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
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
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
                startActivity(new Intent(MainActivity.this, ChamadaActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
