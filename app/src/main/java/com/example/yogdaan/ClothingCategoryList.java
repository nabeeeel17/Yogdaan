package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ClothingCategoryList extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseUser user;
    FirebaseAuth auth;
    Toolbar toolbar;
    CollectionReference cref;
    DocumentReference dref;
    String orgname;
    Button donate;
    EditText edtshirts , edttshirts , edtjeans , edtshirtsf , edttshirtsf , edtjeansf , edtethnicf , age;

    String donorname , donorphno , donoremail ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothingcategory);
        setToolbar();
        init();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
donate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        registerdonation();
    }
});

    }

    private void registerdonation() {
        Map<String , Object> Donor = new HashMap<>();
        Donor.put("Donor Name" , donorname);
        Donor.put("Donor Email" , donoremail );
        Donor.put("Donor Phone No" , donorphno);
        Donor.put("Clothes For Age" , age.getText().toString() );
        Donor.put("No Of Shirts for Male" , edtshirts.getText().toString());
        Donor.put("No Of T Shirts for Male" , edttshirts.getText().toString());
        Donor.put("No Of Jeans for Male" , edtjeans.getText().toString());
        Donor.put("No Of Shirts for Female" , edtshirtsf.getText().toString());
        Donor.put("No Of T Shirts for Female" , edttshirtsf.getText().toString());
        Donor.put("No Of Jeans for Female" , edtjeansf.getText().toString());
        Donor.put("No Of Ethnic Wears for Female" , edtethnicf.getText().toString());

        firestore.collection("Clothes Donation").document(donorname).set(Donor).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ClothingCategoryList.this, "Donation Registered Successfully", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(ClothingCategoryList.this);
                builder.setTitle("Important Poitns");
                builder.setMessage("\n1. The organization will contact you before picking up the Clothes\n" +
                        "\n2. The clothes must be clean condition at the time of pickup\n" +
                        "\n3. Please make sure that the clothes are not torn or in a very bad condition\n");

                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ClothingCategoryList.this , DonationSuccessful.class);
                        intent.putExtra("Email",donoremail);
                        startActivity(intent);


                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }
    public void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private  void  init(){
        toolbar = findViewById(R.id.clothingcategorytoolbar);
        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        cref = firestore.collection("Users Details");
        dref = cref.document(user.getEmail());
        orgname = getIntent().getStringExtra("Org name");
        edtshirts = findViewById(R.id.edittext_shirts);
        edttshirts = findViewById(R.id.edittext_tshirt);
        edtjeans = findViewById(R.id.edittext_jeans);
        edtshirtsf = findViewById(R.id.edittext_shirtsf);
        edttshirtsf = findViewById(R.id.edittext_tshirtsf);
        edtjeansf = findViewById(R.id.edittext_jeansf);
        edtethnicf = findViewById(R.id.edittext_ethnicwearf);
        donate = findViewById(R.id.cDonatebtn);
        age = findViewById(R.id.age_edittext);

        dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                donorname = task.getResult().getString("Name");
                donorphno = task.getResult().getString("Phone No");
                donoremail = task.getResult().getString("Email");
            }
        });

    }
}