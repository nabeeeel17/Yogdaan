package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrganizationDashboard extends AppCompatActivity {


    String email , logintype , category , orgname;

    CardView emergency , donreq;
    FirebaseFirestore firestore;
    BottomNavigationView bottomNavigationView;
    FirebaseUser user ;
    FirebaseAuth auth;
    DocumentReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_dashboard);
        init();

        donreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent ;
                intent = new Intent(OrganizationDashboard.this , donreqlist.class);
                intent.putExtra("Cat" , category);
                intent.putExtra("Org name" , orgname);
                Log.e("taggg" , ""+category+","+orgname);
                startActivity(intent);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(OrganizationDashboard.this , emergency.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();

                if(id==R.id.opthelp){
                   // Intent ihelp=new Intent(OrganizationDashboard.this,HelpDonorActivity.class);
                  //  ihelp.putExtra("Email",email);
                  //  startActivity(ihelp);
                } else if (id==R.id.optlogout) {
                   auth.signOut();
                   Intent ilogout=new Intent(OrganizationDashboard.this,Loginactivity.class);
                   ilogout.putExtra("Email",email);
                   startActivity(ilogout);


                }
                else if (id==R.id.optprofile){
                    Intent iprofile=new Intent(OrganizationDashboard.this, OrgProfile.class);
                    iprofile.putExtra("Email",email);
                    startActivity(iprofile);

                }
                else {
                    Toast.makeText(OrganizationDashboard.this, "Already on Home Page", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.opthome);

    }


    void init(){
        emergency = findViewById(R.id.emergencyCard);
        donreq = findViewById(R.id.requestcard);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        email = getIntent().getStringExtra("Email");
        logintype = getIntent().getStringExtra("Login Type");
        bottomNavigationView = findViewById(R.id.orgbottomnav);
        firestore = FirebaseFirestore.getInstance();
        dref = firestore.collection("Organization Details").document(user.getEmail());
        dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                category = task.getResult().getString("Organization Category");
                orgname = task.getResult().getString("Name");
            }
        });


    }
}