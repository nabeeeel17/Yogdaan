package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BloodCategoryList extends AppCompatActivity {
    String doncategory , email, orgname;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    FirebaseFirestore firestore;
    String donorname , bg , donoremail , donorphone;
    CollectionReference cref;
    DocumentReference dref;
    FirebaseUser user;
EditText medicalh , hft , wkg;
Button donate ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_category);
        init();
        donate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if(hft.getText().toString().isEmpty()){
                    Toast.makeText(BloodCategoryList.this, "Please Enter Donor Hieght", Toast.LENGTH_SHORT).show();
                } else if (wkg.getText().toString().isEmpty()) {
                    Toast.makeText(BloodCategoryList.this, "Please Enter Donor Weight", Toast.LENGTH_SHORT).show();

                }
                else {
                    setdonationdetails();
                }
            }
        });
    }

    private void setdonationdetails() {
        Map<String , Object>Donation = new HashMap<>();
        Donation.put("Donor Name" , donorname);
        Donation.put("Donor Medical History" , medicalh.getText().toString());
        Donation.put("Donor Height" , hft.getText().toString());
        Donation.put("Donor Weight" , wkg.getText().toString());
        Donation.put("Donor Blood Group" ,bg);
        Donation.put("Donated to" , orgname);
        Donation.put("Donor Phone No" , donorphone);
        Donation.put("Donor Email" , donoremail);

        firestore.collection("Blood Donation Details").document(donorname).set(Donation)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(BloodCategoryList.this, "Donation Registered Successfuly", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(BloodCategoryList.this);
                        builder.setMessage( "\n1. The Organization Will Contact For Further Details.\n" +
                                "\n2. Please Carry A Document like Adhaar Card or PAN Card or any other Official Document with you ensuring that You are 18 or Above 18 years of age");
                        builder.setTitle("Important Points to remember");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(BloodCategoryList.this , Homeactivity.class);
                                startActivity(intent);
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });

    }

    private void init(){
        medicalh = findViewById(R.id.edtmedicalhistory);
        hft = findViewById(R.id.Heightinft);
        wkg = findViewById(R.id.edit_weight);
        doncategory= getIntent().getStringExtra("Category");
        Log.e("catr" , ""+doncategory);
        firestore = FirebaseFirestore.getInstance();
        spinner = findViewById(R.id.spinnerblood);
        user = FirebaseAuth.getInstance().getCurrentUser();
        cref = firestore.collection("Users Details");
        dref = cref.document(user.getEmail());
        orgname = getIntent().getStringExtra("Org Name");
        Log.e("orgg" , ""+orgname);
       dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               donorname = task.getResult().getString("Name");
               bg = task.getResult().getString("Blood Type");
               donoremail = task.getResult().getString("Email");
               donorphone = task.getResult().getString("Phone No");

           }
       });

        firestore.collection("Users Details").document(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String selected = task.getResult().getString("Blood Type");
                Log.e("heyyy", "" + selected);
                int pos = adapter.getPosition(selected);
                spinner.setSelection(pos);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        list = new ArrayList<>();
        list.add("A+");
        list.add("A-");
        list.add("B+");
        list.add("B-");
        list.add("AB+");
        list.add("AB-");
        list.add("O+");
        list.add("O-");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        medicalh = findViewById(R.id.edtmedicalhistory);
        hft = findViewById(R.id.Heightinft);
        wkg = findViewById(R.id.edit_weight);
        donate = findViewById(R.id.Donatebtn);

    }
}