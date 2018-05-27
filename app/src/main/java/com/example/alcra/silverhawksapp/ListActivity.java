package com.example.alcra.silverhawksapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alcra.silverhawksapp.entities.Address;
import com.example.alcra.silverhawksapp.entities.Atleta;
import com.example.alcra.silverhawksapp.entities.Presenca;
import com.example.alcra.silverhawksapp.entities.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alcra on 11/04/2018.
 */

public class ListActivity extends AppCompatActivity {

    private DocumentReference mDocRef;

    private Toolbar toolbar;
    private RecyclerView mMainList;
    private UsersListAdapter usersListAdapter;
    private List<Users> usersList;
    private List<DocumentChange> atletasList;
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        usersList = new ArrayList<>();
        atletasList = new ArrayList<>();
        usersListAdapter = new UsersListAdapter(usersList);

        mMainList = findViewById(R.id.main_List);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(usersListAdapter);

        mFirestore.collection(Atleta.COLLECTION_ATLETAS).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("FireLog", "Error: " + e.getMessage());
                }

                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        atletasList.add(doc);
                    }
                    if (doc.getType() == DocumentChange.Type.MODIFIED) {
//                        Atleta atleta = doc.getDocument().toObject(Atleta.class);
//
//                        atletasList.remove(doc.getOldIndex());
//                        atletasList.add(doc.getOldIndex(), atleta);
                    }
                }
            }
        });
    }

    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.chamada_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_chamada:
                startActivity(new Intent(ListActivity.this, ChamadaActivity.class));
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.teste:
                addAtletas();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addAtletas() {
        CollectionReference atletas = mFirestore.collection(Atleta.COLLECTION_ATLETAS);
//        Atleta atleta = new Atleta();
//        atleta.setNameComp("Amanda Lúcia Carstens Ramos");
//        atleta.setFirstName("Atleta");
//        atleta.setLastName("1");
//        atleta.setNumber("99");
//        atleta.setPosicao("Defensive Line");
//
//        Address address = new Address();
//        address.setCep("80035230");
//        atleta.setAddress(address);

        for (int i = 0; i < 10; i++) {
            Atleta atleta = new Atleta();
            atleta.setFirstName("Atleta");
            atleta.setLastName(String.valueOf(i));
            atleta.setNumber(String.valueOf(i));
            atletas.add(atleta);
        }
//
//        for (DocumentChange athlete :
//                atletasList) {
//
//            Atleta atleta1 = athlete.getDocument().toObject(Atleta.class);
//
//            if (atleta1.getNameComp().equals(atleta.getNameComp())){
//                updateAthlete(atleta, athlete);
//
//            } else {
//                atletas.add(atleta);
//            }
//        }
    }
}
