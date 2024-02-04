package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loginactivity extends AppCompatActivity {

    //public static boolean isSplashPlayed=false;
    EditText email;
    FirebaseUser currentuser;
    EditText password;
    Button login;
    TextView sign;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2;
    SharedPreferences preferences ;




    FirebaseAuth firebaseAuth;

    FirebaseFirestore firestore;
    FirebaseUser user;
    int selected;
    String type;
    CollectionReference cref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
      //  SplashScreen();
        init();
        checklogin();



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
                    loginuser(emal, pass);
                }

            }
        });
    }


        void checklogin () {


            if (currentuser != null) {
                firestore.collection("Users Details").document(currentuser.getEmail().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            String type = task.getResult().getString("Type of User");
                            if (type != null) {


                                if (type.equals("Donor")) {
                                    Intent intent = new Intent(Loginactivity.this, Homeactivity.class);
                                    intent.putExtra("Email", user.getEmail());

                                    Log.e("Email", "" + email);
                                    startActivity(intent);
                                    finish();
                                } else {
                                   startActivity(new Intent(Loginactivity.this, OrganizationDashboard.class));
                                   finish();

                                }
                            }
                        }
                    }
                });
            }
        }

        private void loginuser (String email, String pass){
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        if (radioGroup.getCheckedRadioButtonId() == -1) {
                            Log.e("6969", "" + selected);
                            Toast.makeText(Loginactivity.this, "Please Select Login Type", Toast.LENGTH_SHORT).show();
                        } else if (radioButton1.isChecked()) {
                            type = radioButton1.getText().toString();

                            boolean check = preferences.getBoolean("isDetailsSubmitted?", false);
                            if (!check){

                                Intent intent;
                                intent = new Intent(Loginactivity.this, UserDetails.class);
                                intent.putExtra("Email", email);
                                intent.putExtra("Login Type", "Donor");
                                startActivity(intent);
                                Toast.makeText(Loginactivity.this, type + " Login Successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {



                                Intent intent;
                                intent = new Intent(Loginactivity.this, Homeactivity.class);
                                intent.putExtra("Email", email);
                                intent.putExtra("Login Type", "Donor");
                                startActivity(intent);
                                Toast.makeText(Loginactivity.this, type + " Login Successful", Toast.LENGTH_SHORT).show();

                                finish();
                            }

                        } else if (radioButton2.isChecked()) {
                            boolean check2 = preferences.getBoolean("isorgsubmitted" , false);
                            if (!check2) {

                                type = radioButton2.getText().toString();
                                Intent intent = new Intent(Loginactivity.this, Organisationdetails.class);
                                intent.putExtra("Email", email);
                                intent.putExtra("Login Type", type);
                                startActivity(intent);
                                Toast.makeText(Loginactivity.this, type + " Login Successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                       else {
                                type = radioButton2.getText().toString();
                                Intent intent = new Intent(Loginactivity.this, OrganizationDashboard.class);
                                intent.putExtra("Email", email);
                                intent.putExtra("Login Type", type);
                                startActivity(intent);
                                Toast.makeText(Loginactivity.this, type + " Login Successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                    } else {
                        Toast.makeText(Loginactivity.this, "Please Enter Valid Email And Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


        void init () {

            firebaseAuth = FirebaseAuth.getInstance();
            email = findViewById(R.id.emailfield);
            password = findViewById(R.id.passwordfield);
            login = findViewById(R.id.loginButton);
            sign = findViewById(R.id.noacc);
            firestore = FirebaseFirestore.getInstance();
            radioGroup = findViewById(R.id.radioGroup);
            radioButton1 = findViewById(R.id.donorradio);
            radioButton2 = findViewById(R.id.orgradio);
            currentuser = firebaseAuth.getCurrentUser();
            selected = radioGroup.getCheckedRadioButtonId();
            firebaseAuth = FirebaseAuth.getInstance();
            user = firebaseAuth.getCurrentUser();
            preferences = getSharedPreferences("state" , MODE_PRIVATE);


        }
//        void SplashScreen ()
//        {
//            if(!isSplashPlayed) {
//                Intent isplash = new Intent(Loginactivity.this, SplashScreen.class);
//                startActivity(isplash);
//            }
//        }

        }
