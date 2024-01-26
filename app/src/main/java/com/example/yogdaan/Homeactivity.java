package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Homeactivity extends AppCompatActivity {
    CardView clothing , food , grocery , blood , money , books;
    String email;
    Intent intent;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getSupportActionBar()!=null) {

            toolbar.setTitle("my toolbar");

        }
        init();



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.toolbaritems, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.opthome) {
            Toast.makeText(this, "Already on the homepage", Toast.LENGTH_SHORT).show();

            return super.onOptionsItemSelected(item);
        }else if (itemId==R.id.opthelp){
            Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
        }
        else if(itemId==R.id.optlogout) {
            Intent intent7=new Intent(Homeactivity.this,Loginactivity.class);
            firebaseAuth.signOut();

            startActivity(intent7);
            finish();
        }
        else {
            Toast.makeText(this, "Exitted", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    void init(){
        clothing = findViewById(R.id.clothingCard);
        food = findViewById(R.id.foodcard);
        grocery = findViewById(R.id.grocerycard);
        blood = findViewById(R.id.bloodcard);
        money = findViewById(R.id.moneycard);
        books = findViewById(R.id.bookcard);
        firebaseAuth=FirebaseAuth.getInstance();
        intent = getIntent();
        email = intent.getStringExtra("Email");
        Log.e("Email" , ""+email);
    }
}