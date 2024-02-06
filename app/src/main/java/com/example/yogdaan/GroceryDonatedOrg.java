package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GroceryDonatedOrg extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseUser user ;
    String donorname ;
    TextView dname , dmbno , demail , date , rawm , rawmamount;
    Button call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_donated_org);
        init();
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String no = "+91"+dmbno.getText().toString();
                intent.setData(Uri.parse("tel:"+no));
                startActivity(intent);
            }
        });
    }

    public void init(){
        dname = findViewById(R.id.gdnametv);
        dmbno = findViewById(R.id.gdmbnotv);
        demail = findViewById(R.id.gdemailtv);
        call = findViewById(R.id.gcallbutton);
        user = FirebaseAuth.getInstance().getCurrentUser();
        date = findViewById(R.id.dategrocery);
        rawm = findViewById(R.id.edtdonatedrawmaterial);
        rawmamount = findViewById(R.id.edtdonatedamountinkg);
        donorname = getIntent().getStringExtra("Donor Name");
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("Grocery Donation Details").document(user.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        dname.setText(task.getResult().getString("Donor Name"));
                        dmbno.setText(task.getResult().getString("Donor Phone No"));
                        demail.setText(task.getResult().getString("Donor Email"));
                        rawm.setText(task.getResult().getString("Donation Raw Material Name"));
                        rawmamount.setText(task.getResult().getString("Donation Raw Material Amount")+" Kg");
                        date.setText(task.getResult().getString("Donation Date"));

                    }
                });


    }
}