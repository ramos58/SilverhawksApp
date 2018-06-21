package com.example.alcra.silverhawksapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.graphics.drawable.AnimationDrawable;
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
import com.example.alcra.silverhawksapp.entities.Address;
import com.example.alcra.silverhawksapp.entities.Atleta;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


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
    private EditText parentesco;
    private EditText contatoNumber;
    private EditText street;
    private EditText cep;
    private EditText addressNumber;
    private EditText complement;
    private EditText neighborhood;
    private EditText city;
    private EditText healthPlanName;
    private EditText healthPlanNumber;
    private Spinner state;
    private ImageView fotoPerfil;
    private Spinner posicao, unidade;
    private String str;
    private Atleta atleta;

    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

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
        contato = findViewById(R.id.et_contactview);
        parentesco = findViewById(R.id.et_contactconnectionview);
        contatoNumber = findViewById(R.id.et_contactnumberview);
        fotoPerfil = findViewById(R.id.iv_photo);
        posicao = findViewById(R.id.sp_posicao);
        unidade = findViewById(R.id.sp_unidade);
        cep = findViewById(R.id.et_cepview);
        street = findViewById(R.id.et_streetview);
        addressNumber = findViewById(R.id.et_addressnumberview);
        complement = findViewById(R.id.et_complementview);
        neighborhood = findViewById(R.id.et_neighborhoodview);
        city = findViewById(R.id.et_cityview);
        state = findViewById(R.id.sp_stateview);
        healthPlanName = findViewById(R.id.et_healthplannameview);
        healthPlanNumber = findViewById(R.id.et_healthplannumberview);

        if (getIntent().hasExtra(ATLETA)) {
            atleta = (Atleta) getIntent().getExtras().getSerializable(ATLETA);

            nameComp.setText(atleta.getNameComp());
            str = atleta.getRg();
            rg.setText(str.subSequence(0, str.length() - 7) + "." + str.subSequence(str.length() - 7, str.length() - 4) + "." + str.subSequence(str.length() - 4, str.length() - 1) + "-" + str.subSequence(str.length() - 1, str.length()));
            str = atleta.getCpf();
            cpf.setText(str.subSequence(0, str.length() - 8) + "." + str.subSequence(str.length() - 8, str.length() - 5) + "." + str.subSequence(str.length() - 5, str.length() - 2) + "-" + str.subSequence(str.length() - 2, str.length()));
            birthday.setText(atleta.getBirthday());
            str = atleta.getCelPhone();
            if (str.length() > 10)
                celPhone.setText("(" + str.subSequence(0, str.length() - 9) + ") " + str.subSequence(str.length() - 9, str.length() - 4) + "-" + str.subSequence(str.length() - 4, str.length()));
            else
                celPhone.setText("(" + str.subSequence(0, str.length() - 8) + ") " + str.subSequence(str.length() - 8, str.length() - 4) + "-" + str.subSequence(str.length() - 4, str.length()));
            email.setText(atleta.getEmail());
            number.setText(atleta.getNumber());
            contato.setText(atleta.getContatoNome());
            parentesco.setText(atleta.getContatoParentesco());
            str = atleta.getContatoTel();
            if (str.length() > 10)
                contatoNumber.setText("(" + str.subSequence(0, str.length() - 9) + ") " + str.subSequence(str.length() - 9, str.length() - 4) + "-" + str.subSequence(str.length() - 4, str.length()));
            else
                contatoNumber.setText("(" + str.subSequence(0, str.length() - 8) + ") " + str.subSequence(str.length() - 8, str.length() - 4) + "-" + str.subSequence(str.length() - 4, str.length()));

            Glide.with(EditPerfilActivity.this).load(atleta.getPicURL()).into(fotoPerfil);

            if (atleta.getActive().equals("false"))
                isActive.check(R.id.rb_inativa);
            else
                isActive.check(R.id.rb_ativa);

            str = atleta.getAddress().getCep();
            cep.setText(str.subSequence(0, str.length() - 6) + "." +
                    str.subSequence(str.length() - 6, str.length() - 3) + "-" +
                    str.subSequence(str.length() - 3, str.length()));
            street.setText(atleta.getAddress().getStreet());
            addressNumber.setText(atleta.getAddress().getNumber());
            complement.setText(atleta.getAddress().getComplement());
            neighborhood.setText(atleta.getAddress().getNeighborhood());
            city.setText(atleta.getAddress().getCity());

            str = atleta.getAddress().getState();
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.state_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            state.setAdapter(adapter);
            if (str != null) {
                int spinnerPosition = adapter.getPosition(str);
                state.setSelection(spinnerPosition);
            }

            str = atleta.getPosicao();
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.position_array, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            posicao.setAdapter(adapter2);
            if (str != null) {
                int spinnerPosition = adapter2.getPosition(str);
                posicao.setSelection(spinnerPosition);
            }

            str = atleta.getUnidade();
            ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.unity_array, android.R.layout.simple_spinner_item);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            unidade.setAdapter(adapter3);
            if (str != null) {
                int spinnerPosition = adapter3.getPosition(str);
                unidade.setSelection(spinnerPosition);
            }

            healthPlanName.setText(atleta.getHealthPlanNome());
            healthPlanNumber.setText(atleta.getHealthPlanNumber());

        } else {
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
                salvaAtleta(atleta);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvaAtleta(Atleta atleta) {

        Address address = new Address();


        atleta.setNameComp(nameComp.getText().toString());
        atleta.setPosicao(posicao.getSelectedItem().toString());
        atleta.setNumber(number.getText().toString());
        atleta.setUnidade(unidade.getSelectedItem().toString());
        switch (isActive.getCheckedRadioButtonId()) {
            case R.id.rb_ativa:
                atleta.setActive("Ativa");
                break;
            case R.id.rb_inativa:
                atleta.setActive("Inativa");
                break;
        }
        atleta.setRg(rg.getText().toString().replaceAll("[ .-]", ""));
        atleta.setCpf(cpf.getText().toString().replaceAll("[ .-]", ""));
        atleta.setBirthday(birthday.getText().toString());
        atleta.setCelPhone(celPhone.getText().toString().replaceAll("[ ()-]", ""));
        atleta.setEmail(email.getText().toString());

        address.setStreet(street.getText().toString());
        address.setNumber(addressNumber.getText().toString());
        address.setComplement(complement.getText().toString());
        address.setNeighborhood(neighborhood.getText().toString());
        address.setCep(cep.getText().toString().replaceAll("[ .-]", ""));
        address.setCity(city.getText().toString());
        address.setState(state.getSelectedItem().toString());
        atleta.setAddress(address);

        atleta.setContatoNome(contato.getText().toString());
        atleta.setContatoParentesco(parentesco.getText().toString());
        atleta.setContatoTel(contatoNumber.getText().toString().replaceAll("[ ()-]", ""));

        atleta.setHealthPlanNome(healthPlanName.getText().toString());
        atleta.setHealthPlanNumber(healthPlanNumber.getText().toString().replaceAll("[ .-]", ""));

        mFirestore.collection(Atleta.COLLECTION_ATLETAS).document(atleta.getAtletaID()).set(atleta).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                setResult(Activity.RESULT_OK);
                finish();

            }
        });
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
