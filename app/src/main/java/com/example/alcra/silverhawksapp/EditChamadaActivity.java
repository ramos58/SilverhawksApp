package com.example.alcra.silverhawksapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.alcra.silverhawksapp.entities.Chamada;
import com.example.alcra.silverhawksapp.entities.Presenca;
import com.example.alcra.silverhawksapp.utils.DateUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.alcra.silverhawksapp.entities.Chamada.Tipo.PRATICO;
import static com.example.alcra.silverhawksapp.entities.Chamada.Tipo.TEORICO;

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
    List<DocumentSnapshot> refPresencaList = new ArrayList<>();
    EditText localEditText;
    EditText dataEditText;
    RadioGroup tipoRadioGroup;
    Date chamadaDate;
    Chamada chamada;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_chamada);

        localEditText = findViewById(R.id.et_local);
        dataEditText = findViewById(R.id.et_data);
        tipoRadioGroup = findViewById(R.id.rg_tipo);

        initToolbar();

        if(getIntent().hasExtra(CHAMADA_ID)){
            initList(getIntent().getStringExtra(CHAMADA_ID));

            mFirestore.collection(Chamada.COLLECTION_CHAMADA)
                    .document(getIntent().getStringExtra(CHAMADA_ID))
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    chamada = documentSnapshot.toObject(Chamada.class);
                    chamadaDate = chamada.getCalendarDate();
                    localEditText.setText(chamada.getLocal());
                    dataEditText.setText(chamada.getDate());
                    switch (chamada.getTipo()){
                        case PRATICO:
                            tipoRadioGroup.check(R.id.rb_pratico);
                            break;
                        case TEORICO:
                            tipoRadioGroup.check(R.id.rb_teorico);
                            break;
                    }
                }
            });
        }
        else{
            finish();
        }

        initDatePicker();
        initPlacePicker();
    }

    private void initPlacePicker() {
        localEditText.setFocusable(false);
        localEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditChamadaActivity.this);
                builder.setItems(R.array.practice_places, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        localEditText.setText(Arrays.asList(getResources().getStringArray(R.array.practice_places)).get(i));
                        dialogInterface.dismiss();
                    }
                });
                builder.setTitle("Selecione o local:");
                builder.create().show();
            }
        });
    }

    private void initDatePicker() {
        dataEditText.setFocusable(false);
        dataEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditChamadaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dataEditText.setText(String.format(getString(R.string.date_place_holder), i2, i1 + 1, i));

                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year =  datePicker.getYear();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);

                        chamadaDate = calendar.getTime();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(getString(R.string.nomeTelaEditChamada));
        }
    }

    private void initList(String chamadaId) {

        adapter = new NovaChamadaListAdapter(presencaList);

        mFirestore.collection(Presenca.COLLECTION_PRESENCA).whereEqualTo("idChamada",chamadaId)
                .orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("FireLog", "Error: " + e.getMessage());
                }

                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Presenca presenca = doc.getDocument().toObject(Presenca.class);
                        refPresencaList.add(doc.getDocument());
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
               editaChamada();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void editaChamada() {
        ProgressDialog builder = new ProgressDialog(this);
        builder.setMessage(getString(R.string.text_editando));
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
                tipo = TEORICO;
                break;
        }

        DocumentReference doc = mFirestore.collection(Chamada.COLLECTION_CHAMADA)
                .document(getIntent().getStringExtra(CHAMADA_ID));

        chamada.setCalendarDate(chamadaDate);
        chamada.setLocal(local);
        chamada.setTipo(tipo);
        chamada.setDate(data);

        int p = 0, j = 0, f = 0;
        for (int i = 0; i < presencaList.size(); i++) {
            DocumentReference docPresenca = mFirestore.collection(Presenca.COLLECTION_PRESENCA)
                    .document(refPresencaList.get(i).getId());
            Presenca presenca = refPresencaList.get(i).toObject(Presenca.class);

            presenca.setDate(data);
            presenca.setLocal(chamada.getLocal());
            presenca.setMes(month);
            presenca.setIdChamada(doc.getId());
            presenca.setName(presencaList.get(i).getName());
            presenca.setUserId(presencaList.get(i).getUserId());
            presenca.setTipo(presencaList.get(i).getTipo());

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

            docPresenca.set(presenca);
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