package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrganizationDashboard extends AppCompatActivity {

    Toolbar toolbar;
    String email , logintype , category , orgname;

    CardView emergency , donreq;
    FirebaseFirestore firestore;
    FirebaseUser user ;
    FirebaseAuth auth;
    DocumentReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_dashboard);
        init();

        donreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent ;
                intent = new Intent(OrganizationDashboard.this , donreqlist.class);
                intent.putExtra("Cat" , category);
                intent.putExtra("Org name" , orgname);
                Log.e("taggg" , ""+category+","+orgname);
                startActivity(intent);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(OrganizationDashboard.this , emergency.class);
                startActivity(intent);
            }
        });

    }

    void init(){
        emergency = findViewById(R.id.emergencyCard);
        donreq = findViewById(R.id.requestcard);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        email = getIntent().getStringExtra("Email");
        logintype = getIntent().getStringExtra("Login Type");
        firestore = FirebaseFirestore.getInstance();
        dref = firestore.collection("Organization Details").document(user.getEmail());
        dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
             category = task.getResult().getString("Organization Category");
             orgname = task.getResult().getString("Name");
            }
        });


    }

}