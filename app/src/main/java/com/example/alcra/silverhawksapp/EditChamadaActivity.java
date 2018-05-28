package com.example.alcra.silverhawksapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alcra.silverhawksapp.entities.Atleta;
import com.example.alcra.silverhawksapp.entities.Chamada;
import com.example.alcra.silverhawksapp.entities.Presenca;
import com.example.alcra.silverhawksapp.utils.DateUtils;
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
 * Created by alcra on 27/05/2018.
 */

public class EditChamadaActivity extends AppCompatActivity {

    private static final String CHAMADA_ID = "chamadaid";

    public static Intent newIntent(Context context, String chamada) {
        Intent intent = new Intent(context, EditChamadaActivity.class);
        intent.putExtra(CHAMADA_ID, chamada);

        return intent;
    }

    RecyclerView chamadaRecycler;
    NovaChamadaListAdapter adapter;
    Toolbar toolbar;
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    List<Presenca> presencaList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_chamada);

        initToolbar();

        if(getIntent().hasExtra(CHAMADA_ID)){
            initList(getIntent().getStringExtra(CHAMADA_ID));
        }
        else{
            finish();
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initList(String chamadaId) {

        adapter = new NovaChamadaListAdapter(presencaList);

        mFirestore.collection(Presenca.COLLECTION_PRESENCA).whereEqualTo("idChamada",chamadaId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("FireLog", "Error: " + e.getMessage());
                }

                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Presenca presenca = doc.getDocument().toObject(Presenca.class);
                        presencaList.add(0,presenca);
                        adapter.notifyDataSetChanged();
                    }
                    if (doc.getType() == DocumentChange.Type.MODIFIED) {

                    }
                }
            }
        });


        chamadaRecycler = findViewById(R.id.rv_chamada);
        chamadaRecycler.setLayoutManager(new LinearLayoutManager(this));
        chamadaRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nova_chamada_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.envia_chamada:
               // enviaChamada();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void enviaChamada() {
        ProgressDialog builder = new ProgressDialog(this);
        builder.setMessage(getString(R.string.text_enviando));
        builder.setIndeterminate(true);
        builder.show();

        String date = DateUtils.getFormatedDate(new Date());
        String month = DateUtils.getMonth(new Date());

        DocumentReference doc = mFirestore.collection(Chamada.COLLECTION_CHAMADA).document();

        Chamada chamada = new Chamada(date, "Parque Barigui", Chamada.Tipo.PRATICO);
        doc.set(chamada);

        for (int i = 0; i < presencaList.size(); i++) {
            presencaList.get(i).setDate(date);
            presencaList.get(i).setLocal("Parque Barigui");
            presencaList.get(i).setMes(month);
            presencaList.get(i).setIdChamada(doc.getId());
            mFirestore.collection(Presenca.COLLECTION_PRESENCA).add(presencaList.get(i));
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
    }
}
