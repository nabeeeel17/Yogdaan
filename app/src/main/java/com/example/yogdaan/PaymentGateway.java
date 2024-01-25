package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;


public class PaymentGateway extends AppCompatActivity {

    EditText amnt , desc;
    Button pay;
    String unique , uniqueref;
    String upi , payeename;
    Float amt;
    String orgupi;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    String passupi = Organisationdetails.passupi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);
        init();



        pay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    amt = Float.parseFloat(amnt.getText().toString())

                    ;
                    paymentgateway();
                    Log.e("amnt", "" + amt);
                }catch (Exception e){

                }
            }
        });
    }

     void paymentgateway() {

     }


    public void init (){
       amnt = findViewById(R.id.amount);
       desc = findViewById(R.id.description);
       pay = findViewById(R.id.pay);
unique = UUID.randomUUID().toString();
        Intent intent = getIntent();
        orgupi = intent.getStringExtra("UPI");
        Log.e("upi" ,""+orgupi);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        payeename = auth.getCurrentUser().getEmail();
        uniqueref = UUID.randomUUID().toString();
        Log.e("display" , ""+payeename);



    }
}