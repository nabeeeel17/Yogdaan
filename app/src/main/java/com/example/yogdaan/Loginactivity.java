package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    RadioGroup radioGroup;
    RadioButton radioButtton1, radioButton2 ;

    FirebaseAuth firebaseAuth;

    FirebaseFirestore firestore;
    String  selectedbutton , radiovalue ;


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


                if (emal.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(Loginactivity.this, "Please Enter All The Credentials", Toast.LENGTH_SHORT).show();
                } else {
                    radiovalue = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    Log.e("Name" , radiovalue);
                    userdetails(emal , pass , radiovalue);
                    loginuser(emal, pass);
                }
            }
        });
    }

    private void loginuser(String email, String pass) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    if (selectedbutton.equals(R.id.donorradio)) {
                        startActivity(new Intent(Loginactivity.this, Homeactivity.class));
                        Toast.makeText(Loginactivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(Loginactivity.this, Organisationdetails.class));
                        Toast.makeText(Loginactivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }


                    task.getResult();
                } else {
                    Toast.makeText(Loginactivity.this, "Please Enter Valid Email And Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailfield);
        password = findViewById(R.id.passwordfield);
        login = findViewById(R.id.loginButton);
        sign = findViewById(R.id.noacc);
        firestore = FirebaseFirestore.getInstance();
        radioGroup = findViewById(R.id.rg);
        radioButtton1 = findViewById(R.id.donorradio);
        radioButton2 = findViewById(R.id.orgradio);
        selectedbutton = (String.valueOf(findViewById(radioGroup.getCheckedRadioButtonId())));

    }

    void userdetails(String emaill, String password, String typeofuser) {

        Map<String, Object> User = new HashMap<>();

        User.put("Name", emaill);
        User.put("Type of User" ,typeofuser );

        firestore.collection("Users Details").document(emaill.toString())
                .set(User)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("6969", "USer Details Added");
                        Toast.makeText(Loginactivity.this, "User Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("6969", "Error writing document", e);
                    }
                });
    }
}