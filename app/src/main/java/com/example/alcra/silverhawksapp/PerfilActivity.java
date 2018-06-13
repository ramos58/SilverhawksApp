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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

import org.w3c.dom.Text;


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
    private TextView contato;
    private TextView contatoNumber;
    private ImageView fotoPerfil;
    private String str;

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
        contato = findViewById(R.id.tv_contactview);
        contatoNumber = findViewById(R.id.tv_contactnumberview);
        fotoPerfil = findViewById(R.id.iv_photo);

        if(getIntent().hasExtra(ATLETA)){
            Atleta atleta = (Atleta) getIntent().getExtras().getSerializable(ATLETA);

            nameComp.setText(atleta.getNameComp());
            str = atleta.getRg();
            rg.setText(str.subSequence(0,str.length()-7)+"."+str.subSequence(str.length()-7,str.length()-4)+"."+str.subSequence(str.length()-4,str.length()-1)+"-"+str.subSequence(str.length()-1,str.length()));
            str = atleta.getCpf();
            cpf.setText(str.subSequence(0,str.length()-8)+"."+str.subSequence(str.length()-8,str.length()-5)+"."+str.subSequence(str.length()-5,str.length()-2)+"-"+str.subSequence(str.length()-2,str.length()));
            birthday.setText(atleta.getBirthday());
            str = atleta.getCelPhone();
            if(str.length()>10)
                celPhone.setText("("+str.subSequence(0,str.length()-9)+") "+str.subSequence(str.length()-9,str.length()-4)+"-"+str.subSequence(str.length()-4,str.length()));
            else
                celPhone.setText("("+str.subSequence(0,str.length()-8)+") "+str.subSequence(str.length()-8,str.length()-4)+"-"+str.subSequence(str.length()-4,str.length()));
            email.setText(atleta.getEmail());
            posNumUni.setText(atleta.getPosicao()+" #"+atleta.getNumber()+" - "+atleta.getUnidade());
            contato.setText(atleta.getContatoNome()+" ("+atleta.getContatoParentesco()+")");
            str = atleta.getContatoTel();
            if(str.length()>10)
                contatoNumber.setText("("+str.subSequence(0,str.length()-9)+") "+str.subSequence(str.length()-9,str.length()-4)+"-"+str.subSequence(str.length()-4,str.length()));
            else
                contatoNumber.setText("("+str.subSequence(0,str.length()-8)+") "+str.subSequence(str.length()-8,str.length()-4)+"-"+str.subSequence(str.length()-4,str.length()));

            Glide.with(PerfilActivity.this).load(atleta.getPicURL()).into(fotoPerfil);

            if(atleta.getActive().equals("false"))
                isActive.setText("Atleta Inativa");
            else
                isActive.setText("Atleta Ativa");

            str = atleta.getAddress().getCep();
            address.setText(str.subSequence(0,str.length()-6)+"."+
                    str.subSequence(str.length()-6,str.length()-3)+"-"+
                    str.subSequence(str.length()-3,str.length())+"\n"+
                    atleta.getAddress().getStreet()+",\n"+
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
//    public CharSequence subSequence(int beginIndex, int endIndex) {
//        return this.substring(beginIndex, endIndex);
//    }
}
