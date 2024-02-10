package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FoodDnatedOrg extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseUser user ;
    String donorname ;
    private static final int REQUEST_PHONE_CALL = 1;


    Intent callintent;
    TextView dname , dmbno , demail , date , fname , noofpeople;
    Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_dnated_org);
        init();
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkpermissionandmakecall();
            }
        });
    }




    private void callperson() {
        callintent = new Intent(Intent.ACTION_CALL);
        String no = "+91" + dmbno.getText().toString();
        callintent.setData(Uri.parse("tel:" + no));
        startActivity(callintent);
    }

    private void checkpermissionandmakecall(){
        if(ActivityCompat.checkSelfPermission(FoodDnatedOrg.this , android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(FoodDnatedOrg.this , new String[]{Manifest.permission.CALL_PHONE} , REQUEST_PHONE_CALL);
        }
        else {
            callperson();
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

    public  void init(){
        dname = findViewById(R.id.fnametv);
        dmbno = findViewById(R.id.fmbnotv);
        demail = findViewById(R.id.femailtv);
        call = findViewById(R.id.fcallbutton);
        user = FirebaseAuth.getInstance().getCurrentUser();
        date = findViewById(R.id.datefood);
        fname = findViewById(R.id.donatedfood_edittext);
        noofpeople = findViewById(R.id.foodnoofpeopel_edittxt);
        donorname = getIntent().getStringExtra("Donor Name");
        firestore = FirebaseFirestore.getInstance();


        firestore.collection("Food Donation Details").document(donorname).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        dname.setText(task.getResult().getString("Donor Name"));
                        dmbno.setText(task.getResult().getString("Donor Phone No"));
                        demail.setText(task.getResult().getString("Donor Email"));
                        fname.setText(task.getResult().getString("Food Name"));
                        noofpeople.setText(task.getResult().getString("No of People Donor wants to Donate"));
                        date.setText(task.getResult().getString("Donation Date"));

                    }
                });
    }
}