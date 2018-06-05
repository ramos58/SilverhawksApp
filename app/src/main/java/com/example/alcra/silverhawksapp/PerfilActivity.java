package com.example.alcra.silverhawksapp;

import android.content.Context;
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

import com.example.alcra.silverhawksapp.entities.Address;
import com.example.alcra.silverhawksapp.entities.Atleta;
import com.example.alcra.silverhawksapp.entities.Chamada;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class PerfilActivity extends AppCompatActivity {

    private static final String ATLETA = "atletaatual";
    private Toolbar toolbar;
    private TextView nameComp;
    private TextView rg;
    private TextView cpf;
    private TextView birthday;
    private TextView celPhone;
    private TextView email;
    private TextView posNumUni;
    private TextView isActive;
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        initToolbar();

        nameComp = findViewById(R.id.tv_nome);
        rg = findViewById(R.id.tv_rgview);
        cpf = findViewById(R.id.tv_cpfview);
        birthday = findViewById(R.id.tv_birthdayview);
        celPhone = findViewById(R.id.tv_celview);
        email = findViewById(R.id.tv_emailview);
        posNumUni = findViewById(R.id.tv_posicao);
        isActive = findViewById(R.id.tv_status);
        address = findViewById(R.id.tv_endview);

        if(getIntent().hasExtra(ATLETA)){
            Atleta atleta = (Atleta) getIntent().getExtras().getSerializable(ATLETA);

            nameComp.setText(atleta.getNameComp());
            rg.setText(atleta.getRg());
            cpf.setText(atleta.getCpf());
            birthday.setText(atleta.getBirthday());
            celPhone.setText(atleta.getCelPhone());
            email.setText(atleta.getEmail());
            posNumUni.setText(atleta.getPosicao()+" #"+atleta.getNumber()+" - "+atleta.getUnidade());
            if(atleta.getActive().equals("false"))
                isActive.setText("Atleta Inativa");
            else
                isActive.setText("Atleta Ativa");

            address.setText(atleta.getAddress().getCep()+"\n"+atleta.getAddress().getStreet()+",\n"+
                    atleta.getAddress().getNumber()+" - "+atleta.getAddress().getComplement()+" - "+
                    atleta.getAddress().getNeighborhood()+"\n"+atleta.getAddress().getCity()+"/"+
                    atleta.getAddress().getState());
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
            setTitle(getString(R.string.nomeTelaPerfil));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////
////        getMenuInflater().inflate(R.menu.main_menu, menu);
////
////        return super.onCreateOptionsMenu(menu);
//    }
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public static Intent newIntent(Context context, Atleta atleta) {
        Intent intent = new Intent(context, PerfilActivity.class);

        intent.putExtra(ATLETA, atleta);

        return intent;
    }
}
