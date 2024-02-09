package com.example.yogdaan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Homeactivity extends AppCompatActivity {
    CardView clothing , food , grocery , blood , money , books;
    String email;
    Intent intent;
    Toolbar toolbar;
    private static final int REQUEST_PHONE_CALL = 1;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    BottomNavigationView bottomNavigationView;
    FirebaseFirestore firestore;
    String uri ;
    Query query;
    CollectionReference collectionReference , collectionReference2;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        init();
        setToolbar();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();

               if(id==R.id.opthelp){
                   Intent ihelp=new Intent(Homeactivity.this,HelpDonorActivity.class);
                   ihelp.putExtra("Email",email);
                   startActivity(ihelp);
               } else if (id==R.id.optlogout) {
                   firebaseAuth.signOut();
                   Intent ilogout=new Intent(Homeactivity.this,Loginactivity.class);
                   ilogout.putExtra("Email",email);
                   startActivity(ilogout);


               }
               else if (id==R.id.optprofile){
                   Intent iprofile=new Intent(Homeactivity.this,UserProfile.class);
                   iprofile.putExtra("Email",email);
                   startActivity(iprofile);

               }
               else {
                   Toast.makeText(Homeactivity.this, "Already on Home Page", Toast.LENGTH_SHORT).show();
               }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.opthome);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent intent1 = new Intent(Homeactivity.this , CharityList.class);
           intent1.putExtra("Email" , email);
           startActivity(intent1);
            }
        });

        clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Homeactivity.this , ClothingCharityList.class);
                intent2.putExtra("Email" , email);
                startActivity(intent2);
            }
        });

        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(Homeactivity.this , BloodCharityList.class);
                intent3.putExtra("Email" , email);
                intent3.putExtra("Category" , "Blood");
                startActivity(intent3);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(Homeactivity.this , FoodCharityList.class);
                intent4.putExtra("Email" , email);
                startActivity(intent4);
            }
        });

        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(Homeactivity.this , GroceryCharityList.class);
                intent5.putExtra("Email" , email);
                startActivity(intent5);
            }
        });

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6 = new Intent(Homeactivity.this , PaymentGateway.class);
                intent6.putExtra("Email" , email);
                startActivity(intent6);
            }
        });


    }
    private void checkpermissionandmakecall(){
        if(ActivityCompat.checkSelfPermission(Homeactivity.this , android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "requesting permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(Homeactivity.this , new String[]{Manifest.permission.CALL_PHONE} , REQUEST_PHONE_CALL);
        }
        else {
            //callperson();
            Toast.makeText(this, "granted already", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_PHONE_CALL){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }
    }

    public void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    void init(){
        toolbar = findViewById(R.id.hometoolbar);
        clothing = findViewById(R.id.clothingCard);
        food = findViewById(R.id.foodcard);
        grocery = findViewById(R.id.grocerycard);
        blood = findViewById(R.id.bloodcard);
        money = findViewById(R.id.moneycard);
        books = findViewById(R.id.bookcard);
        bottomNavigationView=findViewById(R.id.bottomnav);
        firebaseAuth=FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        intent = getIntent();
        email = user.getEmail();
        Log.e("Email" , ""+email);
        uri = getIntent().getStringExtra("uri");
        Log.e("uri" , ""+uri);

        firestore= FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("Users Details");
    documentReference = collectionReference.document(email);



    }
}