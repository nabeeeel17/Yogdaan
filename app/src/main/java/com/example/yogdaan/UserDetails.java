package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDetails extends AppCompatActivity {
    ArrayList<String> list ;
    Spinner spinner;
    Toolbar toolbar;
    Button submit;
    EditText username  , userphoneno;
    FirebaseFirestore firestore;
    String logintype , useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        init();
        setToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(spinner.getSelectedItem().equals("Please Select Your Blood Type")){
                    Toast.makeText(UserDetails.this, "Please Select Your Blood Type", Toast.LENGTH_SHORT).show();
                }
                String item = adapterView.getItemAtPosition(position).toString();

                Log.e("6969" , ""+item);
              //  userdetails(useremail.toString() , item , logintype , userphoneno.toString() , username.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    useremail = getIntent().getStringExtra("Email");
                    userdetails(logintype);

                }
            });


        }


        void userdetails( String typeofuser ) {

            Map<String, Object> User = new HashMap<>();
            String blood = spinner.getSelectedItem().toString();

            User.put("Name",username.getText().toString());
            User.put("Email" , useremail);
            User.put("Phone No" , userphoneno.getText().toString());
            User.put("Type of User" , typeofuser.toString());
            User.put("Blood Type" ,blood );


            firestore.collection("Users Details").document(username.getText().toString())
                    .set(User)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e("6969", "USer Details Added");
                            Toast.makeText(UserDetails.this, "User Added", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("6969", "Error writing document", e);
                        }
                    });
        }
    public void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
        public void init(){
            username = findViewById(R.id.username);
            toolbar=findViewById(R.id.userdetail);
            //useremail = findViewById(R.id.emailid);
            userphoneno = findViewById(R.id.userphone);
            firestore = FirebaseFirestore.getInstance();
            spinner = findViewById(R.id.spinneruserbloodtype);
            logintype = getIntent().getStringExtra("Login Type");
            submit = findViewById(R.id.submitbutton);




            list = new ArrayList<>();
            list.add("Please Select Your Blood Type");
            list.add("A+");
            list.add("A-");
            list.add("B+");
            list.add("B-");
            list.add("AB+");
            list.add("AB-");
            list.add("O+");
            list.add("O-");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_item ,list);
            adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            spinner.setAdapter(adapter);

        }
    }
