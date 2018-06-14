package com.example.alcra.silverhawksapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.alcra.silverhawksapp.entities.Atleta;


public class EditPerfilActivity extends AppCompatActivity {

    private static final String ATLETA = "atletaatual";
    private Toolbar toolbar;
    private EditText nameComp;
    private EditText rg;
    private EditText cpf;
    private EditText birthday;
    private EditText celPhone;
    private EditText email;
    private EditText number;
    private RadioGroup isActive;
    private EditText address;
    private EditText contato;
    private EditText contatoNumber;
    private ImageView fotoPerfil;
    private Spinner posicao, unidade;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);

        initToolbar();

        nameComp = findViewById(R.id.et_nome);
        rg = findViewById(R.id.et_rgview);
        cpf = findViewById(R.id.et_cpfview);
        birthday = findViewById(R.id.et_birthdayview);
        celPhone = findViewById(R.id.et_celview);
        email = findViewById(R.id.et_emailview);
        number = findViewById(R.id.et_number);
        isActive = findViewById(R.id.rg_status);
        address = findViewById(R.id.et_endview);
        contato = findViewById(R.id.et_contactview);
        contatoNumber = findViewById(R.id.et_contactnumberview);
        fotoPerfil = findViewById(R.id.iv_photo);
        posicao = findViewById(R.id.sp_posicao);
        unidade = findViewById(R.id.sp_unidade);

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
            number.setText("#"+atleta.getNumber());
            contato.setText(atleta.getContatoNome()+" ("+atleta.getContatoParentesco()+")");
            str = atleta.getContatoTel();
            if(str.length()>10)
                contatoNumber.setText("("+str.subSequence(0,str.length()-9)+") "+str.subSequence(str.length()-9,str.length()-4)+"-"+str.subSequence(str.length()-4,str.length()));
            else
                contatoNumber.setText("("+str.subSequence(0,str.length()-8)+") "+str.subSequence(str.length()-8,str.length()-4)+"-"+str.subSequence(str.length()-4,str.length()));

            Glide.with(EditPerfilActivity.this).load(atleta.getPicURL()).into(fotoPerfil);

            if(atleta.getActive().equals("false"))
                isActive.check(R.id.rb_inativa);
            else
                isActive.check(R.id.rb_ativa);

            str = atleta.getAddress().getCep();
            address.setText(str.subSequence(0,str.length()-6)+"."+
                    str.subSequence(str.length()-6,str.length()-3)+"-"+
                    str.subSequence(str.length()-3,str.length())+"\n"+
                    atleta.getAddress().getStreet()+",\n"+
                    atleta.getAddress().getNumber()+" - "+atleta.getAddress().getComplement()+" - "+
                    atleta.getAddress().getNeighborhood()+"\n"+atleta.getAddress().getCity()+"/"+
                    atleta.getAddress().getState());

            str = atleta.getPosicao();
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.position_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            posicao.setAdapter(adapter);
            if (str != null) {
                int spinnerPosition = adapter.getPosition(str);
                posicao.setSelection(spinnerPosition);
            }
            str = atleta.getUnidade();
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.unity_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            unidade.setAdapter(adapter2);
            if (str != null) {
                int spinnerPosition = adapter2.getPosition(str);
                unidade.setSelection(spinnerPosition);
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit_perfil_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.salva_perfil:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public static Intent newIntent(Context context, Atleta atleta) {
        Intent intent = new Intent(context, EditPerfilActivity.class);

        intent.putExtra(ATLETA, atleta);

        return intent;
    }
//    public CharSequence subSequence(int beginIndex, int endIndex) {
//        return this.substring(beginIndex, endIndex);
//    }
}
