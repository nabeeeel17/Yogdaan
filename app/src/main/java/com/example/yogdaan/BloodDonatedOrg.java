package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BloodDonatedOrg extends AppCompatActivity {
    TextView dname , dmbno , demail , mhistory , h , w , bloodtype , date;
    String donorname;
    Button call;
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donated_org);
        init();

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String no = "+91"+dmbno.getText().toString();
                intent.setData(Uri.parse("tel:"+no));
                startActivity(intent);

            }
        });

    }
    public  void init(){
        dname = findViewById(R.id.bdnametv);
        dmbno = findViewById(R.id.bdmbnotv);
        demail = findViewById(R.id.bdemailtv);
        mhistory = findViewById(R.id.edtmedicalhistory1);
        h = findViewById(R.id.Heightinft_edittxt);
        w =  findViewById(R.id.edittxt_weight);
        bloodtype = findViewById(R.id.bloodtype);
        date = findViewById(R.id.bloodtype);
        donorname = getIntent().getStringExtra("Donor Name");
        call = findViewById(R.id.bcallbutton);
        Log.e("tgg" , ""+donorname);
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firestore.collection("Blood Donation Details").document(donorname).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                donorname = task.getResult().getString("Donor Name");
                dname.setText(task.getResult().getString("Donor Name"));
                dmbno.setText(task.getResult().getString("Donor Phone No"));
                demail.setText(task.getResult().getString("Donor Email"));
                mhistory.setText(task.getResult().getString("Donor Medical History"));
                date.setText(task.getResult().getString("Donor Blood Group"));
                h.setText(task.getResult().getString("Donor Height"));
                w.setText(task.getResult().getString("Donor Weight"));
                bloodtype.setText(task.getResult().getString("Donor Blood Group"));

            }
        });

    }
}