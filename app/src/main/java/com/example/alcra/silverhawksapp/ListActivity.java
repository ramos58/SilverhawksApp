package com.example.alcra.silverhawksapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alcra on 11/04/2018.
 */

public class ListActivity extends AppCompatActivity {


    private static final String TAG = "FireLog";
    private DocumentReference mDocRef;

    private Toolbar toolbar;
    private FirebaseAuth auth;
    private String UID;
    private RecyclerView mMainList;
    private UsersListAdapter usersListAdapter;
    private List<Users> usersList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        UID = auth.getUid();
        usersList = new ArrayList<>();
        usersListAdapter = new UsersListAdapter(usersList);

        mMainList = findViewById(R.id.main_List);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(usersListAdapter);

        db.collection("usuários").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Users users = doc.getDocument().toObject(Users.class);
                        usersList.add(users);

                        usersListAdapter.notifyDataSetChanged();
                    }
                    if (doc.getType() == DocumentChange.Type.MODIFIED) {
                        Users users = doc.getDocument().toObject(Users.class);
                        usersList.add(users);

                        usersListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    protected void onStart() {
        super.onStart();
    }

    private void logoutUser() {
        auth.signOut();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(ListActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_logout:
                logoutUser();
                break;
            case R.id.menu_perfil:
                startActivity(new Intent(ListActivity.this, ListActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
