package com.example.yogdaan;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Loginactivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    TextView sign;

    FirebaseAuth firebaseAuth;

    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        init();

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Loginactivity.this, Signinactivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emal = email.getText().toString();
                String pass = password.getText().toString();
                userdetails();
                loginuser(emal , pass);
            }
        });
    }

    private void loginuser(String email, String pass) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    Toast.makeText(Loginactivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Loginactivity.this, Homeactivity.class));
                    task.getResult();
                }
                else {
                    Toast.makeText(Loginactivity.this, "Please Enter Valid Email And Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    void init(){
     firebaseAuth = FirebaseAuth.getInstance();
     email=findViewById(R.id.emailfield);
     password=findViewById(R.id.passwordfield);
     login=findViewById(R.id.loginButton);
     sign=findViewById(R.id.noacc);
     firestore= FirebaseFirestore.getInstance();
    }

    void userdetails(){
        Map<String , Object> User = new HashMap<>();
        User.put("Name" ,email.getText().toString());

        firestore.collection("Users Details").document(email.getText().toString())
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