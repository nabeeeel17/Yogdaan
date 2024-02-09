package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CLothesDonatedOrg extends AppCompatActivity {
    TextView dname , dmbno , demail , dcforage , dmshirt , dmtshirt , dmjeans , dfshirts , dftshirts , dfjeans , dfethnic;
    Button call;
    String donorname;
    private static final int REQUEST_PHONE_CALL = 1;

    Intent callintent;
    FirebaseFirestore firestore;
    CollectionReference cref;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_donated_org);
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
        if(ActivityCompat.checkSelfPermission(CLothesDonatedOrg.this , android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CLothesDonatedOrg.this , new String[]{Manifest.permission.CALL_PHONE} , REQUEST_PHONE_CALL);
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
        dname = findViewById(R.id.dnametv);
        dmbno = findViewById(R.id.dmbnotv);
        demail = findViewById(R.id.demailtv);
        dcforage = findViewById(R.id.donatedage_edittext);
        dmshirt = findViewById(R.id.edittext_donatedshirts);
        dmtshirt = findViewById(R.id.edittext_donatedtshirt);
        dmjeans = findViewById(R.id.edittext_donatedjeans);
        dfshirts = findViewById(R.id.edittext_donatedshirtsf);
        dftshirts = findViewById(R.id.edittext_tshirtsdonatedf);
        dfjeans = findViewById(R.id.edittext_jeansdonatedf);
        dfethnic = findViewById(R.id.edittext_donatedethnicwearf);
        call = findViewById(R.id.callbutton);
        donorname = getIntent().getStringExtra("Donor Name");
        Log.e("tgg" , ""+donorname);

        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
         firestore.collection("Clothes Donation Details").document(donorname).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                dname.setText(task.getResult().getString("Donor Name"));
               dmbno.setText(task.getResult().getString("Donor Phone No"));
               demail.setText(task.getResult().getString("Donor Email"));
               dcforage.setText(task.getResult().getString("Clothes For Age"));
               dmjeans.setText(task.getResult().getString("No Of Jeans for Male"));
               dmshirt.setText(task.getResult().getString("No Of Shirts for Male"));
               dmtshirt.setText(task.getResult().getString("No Of T Shirts for Male"));
               dfshirts.setText(task.getResult().getString("No Of Shirts for Female"));
               dftshirts.setText(task.getResult().getString("No Of T Shirts for Female"));
               dfjeans.setText(task.getResult().getString("No Of Jeans for Female"));
               dfethnic.setText(task.getResult().getString("No Of Ethnic Wears for Female"));
               Log.e("tggg" , ""+task.getResult().getString("Donor Phone No"));
            }
        });
    }
}