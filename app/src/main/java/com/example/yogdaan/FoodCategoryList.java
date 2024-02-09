package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;

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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FoodCategoryList extends AppCompatActivity {
    Toolbar toolbar;
    EditText food , noofpeople;
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    Calendar calendar;
    int year , month , day;
    String orgname;
    CollectionReference cref;
    DocumentReference dref;
    FirebaseAuth auth;
    String  donorname , donoremail , donorphoneno , sdate;
    Button donate , date;
    TextView selecteddate ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_categorylist);
        //setToolbar();
        init();

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 calendar = Calendar.getInstance();
                 year = calendar.get(Calendar.YEAR);
                 month = calendar.get(Calendar.DAY_OF_MONTH);
                 day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog =new DatePickerDialog(FoodCategoryList.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    sdate =day+"-"+(month)+"-"+year;
                    selecteddate.setText("Selected Date : "+sdate);
                    }
                },year,month , day);
                datePickerDialog.show();


            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (food.getText().toString().isEmpty()){
                    Toast.makeText(FoodCategoryList.this, "Please Enter Food You Want to Donate", Toast.LENGTH_SHORT).show();
                } else if (noofpeople.getText().toString().isEmpty()) {
                    Toast.makeText(FoodCategoryList.this, "Please Enter No of People for which you want to donate food", Toast.LENGTH_SHORT).show();
                } else if (selecteddate.getText().toString().isEmpty()) {
                    Toast.makeText(FoodCategoryList.this, "Please select donation date", Toast.LENGTH_SHORT).show();
                }
                else {
                    setDonationDetails();
                }
            }
        });
    }

    private void setDonationDetails() {
        Map<String , Object>Donation = new HashMap<>();
        Donation.put("Donor Name" , donorname);
        Donation.put("Donor Phone No" , donorphoneno);
        Donation.put("Donor Email" , donoremail);
        Donation.put("Food Name" , food.getText().toString());
        Donation.put("No of People Donor wants to Donate" , noofpeople.getText().toString());
        Donation.put("Donated to" , orgname);
        Donation.put("Donation Date" , selecteddate.getText().toString());

        firestore.collection("Food Donation Details").document(donorname).set(Donation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(FoodCategoryList.this, "Donation Registered Successfully", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(FoodCategoryList.this);
                builder.setMessage( "\n1. The Organization Will Contact you before your selected For Further Details.\n" +
                        "\n2. Please ensure that the food is in good condition");
                builder.setTitle("Important Points to remember");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(FoodCategoryList.this , DonationSuccessful.class);
                        startActivity(intent);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


    public  void init(){
        toolbar=findViewById(R.id.foodcategorytoolbar);
        food = findViewById(R.id.food_edittext);
        date = findViewById(R.id.date);
        selecteddate = findViewById(R.id.selecteddate);
        noofpeople = findViewById(R.id.noofpeopel_edittxt);
        donate = findViewById(R.id.fooddonatebutton);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        cref = firestore.collection("Users Details");
        dref = cref.document(firebaseUser.getEmail());
        orgname = getIntent().getStringExtra("Org Name");

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