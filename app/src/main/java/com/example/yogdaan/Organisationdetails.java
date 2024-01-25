package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Organisationdetails extends AppCompatActivity {
    Spinner spinner;
    String[] category;
    EditText name , orgmbo , orgaddress,orgpincode , orgemail , orgupi;
    FirebaseFirestore firestore;
    Button proceed;
    static String passupi;
    ArrayList<String> list ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisationdetails);
        init();
     spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
             String item = adapterView.getItemAtPosition(position).toString();
             Log.e("6969" , ""+item);
         }

         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {

         }
     });
         list = new ArrayList<>();
        list.add("Food");
        list.add("Grocery");
        list.add("Blood");
        list.add("Books");
        list.add("Toys");
        list.add("Clothes");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_item ,list);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String n = name.getText().toString();
                    String mbo = orgmbo.getText().toString();
                    String emal = orgemail.getText().toString();
                    String add = orgaddress.getText().toString();
                    String pc = orgpincode.getText().toString();
                    String orupi = orgupi.getText().toString();
                     passupi = orupi;
                    orgdetails(n, mbo, emal, add, pc , orupi);
                }
        });
    }

    void orgdetails(String name , String no , String Email , String address , String Pincode , String UPIID ){
        if(name.isEmpty()){
            Toast.makeText(this, "Please Enter Organization Name", Toast.LENGTH_SHORT).show();
        } else if (no.isEmpty()) {
            Toast.makeText(this, "Please Enter Organization Number", Toast.LENGTH_SHORT).show();
        } else if (Email.isEmpty()) {
            Toast.makeText(this, "Please Enter Organization Mail", Toast.LENGTH_SHORT).show();
        } else if (address.isEmpty()) {
            Toast.makeText(this, "Please Enter Organization Address", Toast.LENGTH_SHORT).show();
        } else if (Pincode.isEmpty()) {
            Toast.makeText(this, "Please Enter Organization PinCode", Toast.LENGTH_SHORT).show();
        } else if (UPIID.isEmpty()) {
            Toast.makeText(this, "Please Enter UPI ID", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, Object> Category = new HashMap<>();
            Category.put("Name", name);
            Category.put("Organization Number", no);
            Category.put("Organization Email", Email);
            Category.put("Organization Address", address);
            Category.put("Organization Pin Code", Pincode);
            Category.put("Organization Category", spinner.getSelectedItem().toString());
            Category.put("Organisation UPI ID" , UPIID);
            firestore.collection("Organization Details").document(name).set(Category)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.e("6969", "" + name);
                            Toast.makeText(Organisationdetails.this, "Details added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("UPI ID" , orgupi.getText());
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("6969", "Details failed", e);
                        }
                    });
        }


    }



    public  void init(){
    name = findViewById(R.id.orgname);
    orgmbo = findViewById(R.id.orgmbo);
    orgaddress = findViewById(R.id.orgaddress);
    orgpincode = findViewById(R.id.orgpinc);
    orgemail = findViewById(R.id.orgemail);
    spinner= findViewById(R.id.spinner);
    orgupi = findViewById(R.id.orgupiid);
    category = new String[]{"Food" , "Grocery" , "Blood" , "Toys" , "Books"};
    firestore= FirebaseFirestore.getInstance();
    proceed = findViewById(R.id.proceed);

    }
}
