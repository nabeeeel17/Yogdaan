package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class ClothingCharityList extends AppCompatActivity {
Toolbar toolbar;
RecyclerView recyclerView;
ArrayList<CharityModel> arrayList;
CollectionReference cref;
FirebaseFirestore firestore;
Query query;
Adapter adapter;
Button paytoorg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_charity_list);
        init();
        setToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getDetails();

    }

    void getDetails(){

        query = cref;
        query.whereEqualTo("Organization Category" , "Clothes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                        //setting up adapter


                    }
                    adapter=new Adapter(arrayList,getApplicationContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(ClothingCharityList.this));
                    Log.e("array size" , ""+arrayList.size());
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("tag", "Error getting documents: ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ClothingCharityList.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public  void init(){

        toolbar = findViewById(R.id.clothingtoolbar);
        recyclerView = findViewById(R.id.clothrecycleview);
        arrayList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        cref = firestore.collection("Organization Details");


    }
}