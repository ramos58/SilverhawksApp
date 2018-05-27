package com.example.alcra.silverhawksapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.alcra.silverhawksapp.entities.Presenca;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alcra on 27/05/2018.
 */

public class ChamadaActivity extends AppCompatActivity {

    RecyclerView chamadaRecycler;
    ChamadaListAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamada);

        initToolbar();
        initList();

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initList() {
        List<Presenca> presencaList = new ArrayList<>();

        presencaList.add(new Presenca("Amanda Lúcia Carstens Ramos", Presenca.P));
        presencaList.add(new Presenca("José Eduardo Lima dos Santos", Presenca.F));

        adapter = new ChamadaListAdapter(presencaList);

        chamadaRecycler = findViewById(R.id.rv_chamada);
        chamadaRecycler.setLayoutManager(new LinearLayoutManager(this));
        chamadaRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
