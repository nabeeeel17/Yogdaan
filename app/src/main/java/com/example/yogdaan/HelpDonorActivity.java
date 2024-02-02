package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HelpDonorActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FirebaseUser user;
    FirebaseAuth authh;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_donor);
        init();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();

                if(id==R.id.opthome){
                    Intent ihome=new Intent(HelpDonorActivity.this,Homeactivity.class);
                    startActivity(ihome);
                } else if (id==R.id.optprofile) {
                    Intent iprofile=new Intent(HelpDonorActivity.this,UserProfile.class);
                    iprofile.putExtra("Email",email);
                    Log.e("Email",""+email);
                    startActivity(iprofile);
                }
                else if (id==R.id.optlogout){
                    Intent ilogout=new Intent(HelpDonorActivity.this,Loginactivity.class);
                    authh.signOut();
                    startActivity(ilogout);

                }
                else {
                    Toast.makeText(HelpDonorActivity.this, "Already on Help Page", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.opthelp);
    }
    void init()
    {

        bottomNavigationView=findViewById(R.id.bottomnavHelp);
        authh = FirebaseAuth.getInstance();
        email = getIntent().getStringExtra("Email");
        Log.e("init Email",""+email);

    }

}