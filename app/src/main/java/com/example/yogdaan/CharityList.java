package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CharityList extends AppCompatActivity {
RecyclerView recyclerView;
Adapter adapter;
Toolbar toolbar;
ArrayList<CharityModel> arrayList;
CollectionReference cref;
FirebaseFirestore firestore;
Query query;
String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_list);
        init();
        setToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //  setAdapter();
        getDetails();


    }

    void setAdapter(){
        //dummy logic
        CharityModel cm = new CharityModel();

        cm.setName("Nabeel");
        arrayList.add(cm);

        //setting up adapter
        adapter=new Adapter(arrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    void getDetails(){

        query = cref;
        query.whereEqualTo("Organization Category" , "Books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        CharityModel cm = new CharityModel();
                        Log.e("tag", document.getId() + " => " + document.getData());
                       cm.setName(document.getString("Name"));
                       cm.setAddress(document.getString("Organization Address"));
                        Log.e("org name" ,""+document.getString("Name"));
                        arrayList.add(cm);

                    }
                    adapter=new Adapter(arrayList,getApplicationContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(CharityList.this));
                    Log.e("array sizde" , ""+arrayList.size());
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("tag", "Error getting documents: ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CharityList.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

        }
    public void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void init(){
        toolbar= findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.bookrecycleview);
        arrayList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        cref = firestore.collection("Organization Details");
        Intent intent = getIntent();
        intent.getStringExtra("Email");

    }
}