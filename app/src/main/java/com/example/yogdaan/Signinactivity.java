package com.example.yogdaan;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signinactivity extends AppCompatActivity {
    EditText email , password ;
    Button signin;
    TextView gotolog;
    FirebaseAuth firebaseAuth;

    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinactivity);
        init();

        gotolog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signinactivity.this, Loginactivity.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emal = email.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(emal)|| TextUtils.isEmpty(pass)){
                    Toast.makeText(Signinactivity.this, "Please enter all the credentials", Toast.LENGTH_SHORT).show();
                }
                else{
                    registeruser(emal , pass);
                }


            }
        });
    }

    private void registeruser(String email , String pass) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                   userdetails();
                    Toast.makeText(Signinactivity.this, "Registration Successful ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Signinactivity.this,Loginactivity.class));


                }
                else{
                    Toast.makeText(Signinactivity.this, "Registration Failed ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    void init(){
        email = findViewById(R.id.semailfield);
        password = findViewById(R.id.spasswordfield);
        signin= findViewById(R.id.signinButton);
        gotolog = findViewById(R.id.gotolog);
        firebaseAuth=  FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

    }

    void userdetails(){
        Map<String , Object>User = new HashMap<>();
        User.put("Name" ,email.getText().toString());

        firestore.collection("Users Registration Details").document(email.getText().toString())
                .set(User)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User Added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });


    }
}