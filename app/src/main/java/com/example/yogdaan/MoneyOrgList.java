package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class MoneyOrgList extends AppCompatActivity {

    RecyclerView recyclerView;
    MoneyAdapter adapter;
    Toolbar toolbar;
    ArrayList<MoneyModel> arrayList;
    CollectionReference cref;
    FirebaseFirestore firestore;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_org_list);
        init();
        getDetails();
    }

    void getDetails(){
        query = cref;


        firestore.collection("Organization Details").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MoneyModel mm = new MoneyModel();
                                CharityModel cm = new CharityModel();
                                Log.e("tag", document.getId() + " => " + document.getData());
                                mm.setName(document.getString("Name"));
                                mm.setAddress(document.getString("Organization Address"));
                                mm.setUpi(document.getString("Organisation UPI ID"));
                                Log.e("org name", "" + document.getString("Organisation UPI ID"));
                                arrayList.add(mm);

                            }
                          adapter = new MoneyAdapter(arrayList , MoneyOrgList.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MoneyOrgList.this));
                            Log.e("array sizde", "" + arrayList.size());
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.e("tag", "Error getting documents: ", task.getException());

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MoneyOrgList.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void init(){
        recyclerView = findViewById(R.id.moneyrecycler);
        arrayList = new ArrayList<MoneyModel>();
        firestore = FirebaseFirestore.getInstance();
        cref = firestore.collection("Organization Details");
        Intent intent = getIntent();
        intent.getStringExtra("Email");
    }
}