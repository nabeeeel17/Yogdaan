package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OrganizationDashboard extends AppCompatActivity {
    String email;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_dashboard);
        init();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();

                if(id==R.id.opthelp){
                    Intent ihelp=new Intent(OrganizationDashboard.this,OrgHelpPage.class);
                    ihelp.putExtra("Email",email);
                    startActivity(ihelp);
                } else if (id==R.id.optlogout) {
                    firebaseAuth.signOut();
                    Intent ilogout=new Intent(OrganizationDashboard.this,Loginactivity.class);
                    ilogout.putExtra("Email",email);
                    startActivity(ilogout);


                }
                else if (id==R.id.optprofile){
                    Intent iorgprofile=new Intent(OrganizationDashboard.this,OrgProfile.class);
                    iorgprofile.putExtra("Email",email);
                    startActivity(iorgprofile);

                }
                else {
                    Toast.makeText(OrganizationDashboard.this, "Already on Home Page", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.opthome);
    }
void init()
{
    bottomNavigationView=findViewById(R.id.orgbottomnav);
    firebaseAuth=FirebaseAuth.getInstance();
    user = firebaseAuth.getCurrentUser();
    email = user.getEmail();
}
}