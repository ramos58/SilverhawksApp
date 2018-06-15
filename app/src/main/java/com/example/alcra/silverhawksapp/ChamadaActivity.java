package com.example.alcra.silverhawksapp;

import android.content.Context;
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
import com.example.alcra.silverhawksapp.entities.AtletaExcel;
import com.example.alcra.silverhawksapp.entities.Chamada;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alcra on 11/04/2018.
 */

public class ChamadaActivity extends AppCompatActivity {

    private DocumentReference mDocRef;

    private Toolbar toolbar;
    private RecyclerView recycler;
    private ChamadaListAdapter chamadaListAdapter;
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private List<Chamada> chamadaList;
    List<DocumentReference> refChamada = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamada);
        toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Lista de Chamadas");
        }

        chamadaList = new ArrayList<>();
        chamadaListAdapter = new ChamadaListAdapter(chamadaList);
        chamadaListAdapter.setOnClick(new ChamadaListAdapter.OnClick() {
            @Override
            public void click(int position) {
                startActivity(EditChamadaActivity.newIntent(ChamadaActivity.this,refChamada.get(position).getId()));
            }
        });

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(chamadaListAdapter);

        mFirestore.collection(Chamada.COLLECTION_CHAMADA).orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("FireLog", "Error: " + e.getMessage());
                }

                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Chamada chamada = doc.getDocument().toObject(Chamada.class);
                        chamadaList.add(chamada);
                        refChamada.add(doc.getDocument().getReference());
                        chamadaListAdapter.notifyDataSetChanged();
                    }
                    if (doc.getType() == DocumentChange.Type.MODIFIED) {
//                        Chamada chamada = doc.getDocument().toObject(Chamada.class);
//
//                        chamadaList.remove(doc.getOldIndex());
//                        chamadaList.add(doc.getOldIndex(), chamada);
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
                startActivity(new Intent(ChamadaActivity.this, NovaChamadaActivity.class));
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.teste:
                try {
                    addAtletas();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addAtletas() throws ParseException, IOException {

//        mFirestore.collection(Atleta.COLLECTION_ATLETAS).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot documentSnapshots) {
//                for (DocumentSnapshot document:documentSnapshots.getDocuments()) {
//                    Atleta atleta = document.toObject(Atleta.class);
//                    atleta.setHealthPlanNome("-");
//                    atleta.setHealthPlanNumber("-");
//
//                    mFirestore.collection(Atleta.COLLECTION_ATLETAS).document(document.getId()).set(atleta);
//                }
//            }
//        });
//
//        getBaseContext().getAssets().open("atletas.json");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("atletas.json")));
//
//        Gson gson = new Gson();
//        List<AtletaExcel> list = gson.fromJson(bufferedReader,AtletaExcel.Atletas.class).atletas;
//        List<Atleta> athlete = new ArrayList<>();
//        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM-dd-yy");
//        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd/MM/YYYY");
//
//        for (AtletaExcel atletaExcel:
//                list) {
//            Atleta user = new Atleta();
//            Address address = new Address();
//            user.setNameComp(atletaExcel.nomeComp);
//            user.setFirstName(atletaExcel.firstName);
//            user.setLastName(atletaExcel.lastName);
//            user.setRg(atletaExcel.rg);
//            user.setCpf(atletaExcel.cpf);
//            user.setBirthday(simpleDateFormat2.format((simpleDateFormat1.parse(atletaExcel.birthday))));
//            user.setCelPhone(atletaExcel.celPhone);
//            user.setEmail(atletaExcel.email);
//
//            address.setStreet(atletaExcel.street);
//            address.setNumber(atletaExcel.addressNumber);
//            address.setComplement(atletaExcel.complement);
//            address.setNeighborhood(atletaExcel.neighborhood);
//            address.setCep(atletaExcel.cep);
//            address.setCity(atletaExcel.city);
//            address.setState(atletaExcel.state);
//            user.setAddress(address);
//
//            user.setNumber(atletaExcel.number);
//            user.setPosicao(atletaExcel.posicao);
//            user.setUnidade(atletaExcel.unidade);
//            user.setActive(atletaExcel.isActive);
//            user.setContatoNome(atletaExcel.contatoNome);
//            user.setContatoParentesco(atletaExcel.contatoParentesco);
//            user.setContatoTel(atletaExcel.contatoTel);
//            user.setPicURL(atletaExcel.picURL);
//
//            atletas.add(user);
//        }
    }
}
