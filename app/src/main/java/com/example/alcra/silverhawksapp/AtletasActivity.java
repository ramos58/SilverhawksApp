package com.example.alcra.silverhawksapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.alcra.silverhawksapp.R;
import com.example.alcra.silverhawksapp.entities.Atleta;
import com.example.alcra.silverhawksapp.entities.Chamada;
import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atletas);

        initToolbar();

        atletaList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            Atleta atleta = new Atleta();
            atleta.setNameComp("Teste "+i);
            atleta.setNumber("00");
            atletaList.add(atleta);
        }

        atletaListAdapter = new AtletaListAdapter(atletaList);
        atletaListAdapter.setOnClick(new AtletaListAdapter.OnClick() {
            @Override
            public void click(int position) {
                //startActivity(EditChamadaActivity.newIntent(ChamadaActivity.this,refChamada.get(position).getId()));
                startActivity(new Intent(AtletasActivity.this,PerfilActivity.class));
            }
        });

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.setAdapter(atletaListAdapter);
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
