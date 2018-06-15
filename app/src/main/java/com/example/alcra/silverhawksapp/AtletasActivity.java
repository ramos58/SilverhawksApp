package com.example.alcra.silverhawksapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.alcra.silverhawksapp.entities.Atleta;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alcra on 04/06/2018.
 */

public class AtletasActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private List<Atleta> atletaList;
    private RecyclerView recycler;
    private AtletaListAdapter atletaListAdapter;
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atletas);

        initToolbar();

        atletaList = new ArrayList<>();
        atletaListAdapter = new AtletaListAdapter(this, atletaList);
        atletaListAdapter.setOnClick(new AtletaListAdapter.OnClick() {
            @Override
            public void click(Atleta atleta) {
                startActivity(PerfilActivity.newIntent(AtletasActivity.this,atleta));
            }
        });

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.setAdapter(atletaListAdapter);

        mFirestore.collection(Atleta.COLLECTION_ATLETAS).orderBy("nameComp")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("FireLog", "Error: " + e.getMessage());
                        }

                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                Atleta atleta = doc.getDocument().toObject(Atleta.class);

                                atletaList.add(atleta);
                                atletaListAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(getString(R.string.nomeTelaAtletas));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
