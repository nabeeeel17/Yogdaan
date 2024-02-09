package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class donreqlist extends AppCompatActivity {
RecyclerView recyclerView;
ArrayList<DonorModel> list ;
    CollectionReference cref;
    String category , orgname , donationtype;
    FirebaseFirestore firestore;
    DocumentReference documentReference;
    Query query;
    DonationAdapter adapter;
    private static final int REQUEST_PHONE_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donreqlist);
        init();
        getdetails();
    }

    private void getdetails() {
        firestore.collection(donationtype).whereEqualTo("Donated to" , orgname).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        DonorModel dm = new DonorModel();
                        dm.setDonorname(document.getString("Donor Name"));
                        dm.setDonorphoneno(document.getString("Donor Phone No"));
                        dm.setDonationtype(donationtype);
                        list.add(dm);
                        Toast.makeText(donreqlist.this, "iffff", Toast.LENGTH_SHORT).show();
                    }
                    adapter=new DonationAdapter(list,getApplicationContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(donreqlist.this));
                    Log.e("array size" , ""+list.size());
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }


    public  void init(){
        recyclerView = findViewById(R.id.donreqrecyclerview);
        list = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        cref = firestore.collection("Organization Details");
        category = getIntent().getStringExtra("Cat");
        orgname = getIntent().getStringExtra("Org name");

        if(category.equals("Books")){
            donationtype = "Book Donation Details";
        } else if (category.equals("Blood")) {
            donationtype = "Blood Donation Details";
        } else if (category.equals("Food")) {
            donationtype= "Food Donation Details";
        } else if (category.equals("Grocery")) {
            donationtype= "Grocery Donation Details";
        }else if (category.equals("Clothes")) {
            donationtype="Clothes Donation Details";
        }



    }


}