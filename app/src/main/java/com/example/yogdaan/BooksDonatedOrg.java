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

public class BooksDonatedOrg extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseUser user ;
    String donorname ;
    TextView dname , dmbno , demail , date , typeofbook , nofobook;
    Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_donated_org);
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
        dname = findViewById(R.id.bkdnametv);
        dmbno = findViewById(R.id.bkmbnotv);
        demail = findViewById(R.id.bkemailtv);
        call = findViewById(R.id.bkcallbutton);
        typeofbook = findViewById(R.id.donatedbooktype);
        nofobook = findViewById(R.id.noofbooksdonated);
        user = FirebaseAuth.getInstance().getCurrentUser();
        donorname = getIntent().getStringExtra("Donor Name");
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("Book Donation Details").document(donorname).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                dname.setText(task.getResult().getString("Donor Name"));
                dmbno.setText(task.getResult().getString("Donor Phone No"));
                demail.setText(task.getResult().getString("Donor Email"));
                typeofbook.setText(task.getResult().getString("Book Type"));
                nofobook.setText(task.getResult().getString("No of Books Donor wants to Donate"));

            }
        });

    }
}