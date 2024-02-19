package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class emergency extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<UserModel> list ;
    CollectionReference cref;
    String category , orgname , donationtype;
    FirebaseFirestore firestore;
    DocumentReference documentReference;
    Query query;
    EmergencyAdapter adapter;
    UserModel userModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        init();
        getdetails();
    }

    private void getdetails() {
        firestore.collection("Users Details").whereNotEqualTo("Name" , null).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        UserModel dm = new UserModel();
                        dm.setUsername(document.getString("Name"));
                        dm.setUserphoneno(document.getString("Phone No"));
                        dm.setUserbloodtype(document.getString("Blood Type"));
                        dm.setEmail(document.getString("Email"));
                        Log.e("email" ,""+document.getString("Email"));

                        list.add(dm);
                        Toast.makeText(emergency.this, "iffff", Toast.LENGTH_SHORT).show();
                    }
                    adapter = new EmergencyAdapter(getApplicationContext(), list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(emergency.this));
                    Log.e("array size", "" + list.size());
                    recyclerView.setAdapter(adapter);
                }
            }
        });

    }
    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }



    public void init(){
        recyclerView = findViewById(R.id.emergencylist);
        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
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