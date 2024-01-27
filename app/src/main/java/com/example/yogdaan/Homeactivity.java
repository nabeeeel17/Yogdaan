package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class Homeactivity extends AppCompatActivity {
    CardView clothing , food , grocery , blood , money , books;
    String email;
    Intent intent;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ImageView imageView;
    FirebaseFirestore firestore;
    String uri ;
    Query query;
    CollectionReference collectionReference , collectionReference2;
    DocumentReference documentReference;

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

       seturi();

    }

    private void seturi() {
        firestore.collection("Users Details").document(email.toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               Log.e("tagggggg" , ""+task.getResult());
               String base64 = task.getResult().getString("uri");
               //Log.e("taggg" , ""+uri);
                byte[] decodedstr = Base64.decode(base64 , Base64.DEFAULT);
                Bitmap bitmap = byteArrayToBitmap(decodedstr);
                imageView.setImageBitmap(bitmap);

            }
        });


    }

    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
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
        user = firebaseAuth.getCurrentUser();
        intent = getIntent();
        email = intent.getStringExtra("Email");
        Log.e("Email" , ""+email);
        uri = getIntent().getStringExtra("uri");
        Log.e("uri" , ""+uri);
        imageView = findViewById(R.id.userprofilepic);
        firestore= FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("Users Details");
    documentReference = collectionReference.document(email);



    }
}