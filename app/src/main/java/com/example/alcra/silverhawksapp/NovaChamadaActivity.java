package com.example.alcra.silverhawksapp;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.RadioGroup;

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

public class NovaChamadaActivity extends AppCompatActivity {

    RecyclerView chamadaRecycler;
    NovaChamadaListAdapter adapter;
    Toolbar toolbar;
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    List<Presenca> presencaList = new ArrayList<>();
    List<DocumentChange> refList = new ArrayList<>();
    EditText localEditText;
    EditText dataEditText;
    RadioGroup tipoRadioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_chamada);

        localEditText = findViewById(R.id.et_local);
        dataEditText = findViewById(R.id.et_data);
        tipoRadioGroup = findViewById(R.id.rg_tipo);

        initToolbar();
        initList();
    }


    private void initToolbar() {
        toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Nova Chamada");
        }
    }

    private void initList() {

        adapter = new NovaChamadaListAdapter(presencaList);
        tipoRadioGroup.check(R.id.rb_pratico);
        mFirestore.collection(Atleta.COLLECTION_ATLETAS).orderBy("nameComp")
                .whereEqualTo("active", "true")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("FireLog", "Error: " + e.getMessage());
                        }

                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                Atleta atleta = doc.getDocument().toObject(Atleta.class);
                                Presenca presenca = new Presenca();
                                presenca.setName(atleta.getFirstName() + " " + atleta.getLastName());
                                presenca.setTipo(Presenca.P);
                                presenca.setUserId(doc.getDocument().getId());
                                presencaList.add(presenca);
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
                enviaChamada();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void enviaChamada() {
        ProgressDialog builder = new ProgressDialog(this);
        builder.setMessage(getString(R.string.text_enviando));
        builder.setIndeterminate(true);
        builder.show();

        String local = localEditText.getText().toString();
        String data = dataEditText.getText().toString();
        Chamada.Tipo tipo = null;
        String month = data.split("/")[1];

        switch (tipoRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_pratico:
                tipo = Chamada.Tipo.PRATICO;
                break;
            case R.id.rb_teorico:
                tipo = Chamada.Tipo.TEORICO;
                break;
        }

        DocumentReference doc = mFirestore.collection(Chamada.COLLECTION_CHAMADA).document();

        Chamada chamada = new Chamada(data, local, tipo);

        int p = 0, j = 0, f = 0;
        for (int i = 0; i < presencaList.size(); i++) {
            presencaList.get(i).setDate(data);
            presencaList.get(i).setLocal(chamada.getLocal());
            presencaList.get(i).setMes(month);
            presencaList.get(i).setIdChamada(doc.getId());
            mFirestore.collection(Presenca.COLLECTION_PRESENCA).add(presencaList.get(i));
            switch (presencaList.get(i).getTipo()) {
                case Presenca.P:
                    p++;
                    break;
                case Presenca.J:
                    j++;
                    break;
                case Presenca.F:
                    f++;
                    break;
            }
        }

        chamada.setTotalAtletas(presencaList.size());
        chamada.setTotalPresencas(p);
        chamada.setTotalJustificativas(j);
        chamada.setTotalFaltas(f);

        doc.set(chamada);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
    }
}
