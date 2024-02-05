package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GroceryCategoryList extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    String orgname;
    Calendar calendar;
    int year , month , day;
    TextView selecteddate;
    CollectionReference cref;
    DocumentReference dref;
    FirebaseAuth auth;
    String  donorname , donoremail , donorphoneno , sdate;
    EditText rawmaterial, rawmaterialamnt;
    Button Donate , date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_category_list);
        init();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.DAY_OF_MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog =new DatePickerDialog(GroceryCategoryList.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        sdate =day+"-"+month+"-"+year;
                        selecteddate.setText("Selected Date : "+sdate);
                    }
                },year,month , day);
                datePickerDialog.show();


            }
        });

        Donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rawmaterial.getText().toString().isEmpty()){
                    Toast.makeText(GroceryCategoryList.this, "Please Enter Raw Material Name", Toast.LENGTH_SHORT).show();
                } else if (rawmaterialamnt.getText().toString().isEmpty()) {
                    Toast.makeText(GroceryCategoryList.this, "Please Enter Raw Material Amount", Toast.LENGTH_SHORT).show();
                } else if (sdate == null) {
                    Toast.makeText(GroceryCategoryList.this, "Please enter the donation date", Toast.LENGTH_SHORT).show();
                } else {
                    setDonationDetails();
                }
            }
        });
    }

    private void setDonationDetails() {
        Map<String , Object> Donation = new HashMap<>();
        Donation.put("Donor Name" , donorname);
        Donation.put("Donation Raw Material Name" , rawmaterial.getText().toString());
        Donation.put("Donation Raw Material Amount" , rawmaterialamnt.getText().toString());
        Donation.put("Donation Date" , sdate);
        Donation.put("Donated to" , orgname);
        Donation.put("Donor Email" , donoremail);

        firestore.collection("Grocery Donation Details").document(donoremail).set(Donation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(GroceryCategoryList.this, "Donation Registered Successfully", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(GroceryCategoryList.this);
                builder.setMessage( "\n1. The Organization Will Contact For Further Details.\n" +
                        "\n2. Please ensure that the groceries are in good condition and not stale");
                builder.setTitle("Important Points to remember");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(GroceryCategoryList.this , DonationSuccessful.class);
                        startActivity(intent);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    public void init() {
        rawmaterial = findViewById(R.id.edtrawmaterial);
        rawmaterialamnt = findViewById(R.id.edtamountinkg);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        cref = firestore.collection("Users Details");
        dref = cref.document(firebaseUser.getEmail());
        Donate = findViewById(R.id.donategrocerybtn);
        orgname = getIntent().getStringExtra("Org Name");
        date = findViewById(R.id.grocerydate);
        selecteddate = findViewById(R.id.groceryselecteddate);
        dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                donorname  = task.getResult().getString("Name");
                donoremail = task.getResult().getString("Email");
                donorphoneno = task.getResult().getString("Phone No");
            }
        });
    }
}