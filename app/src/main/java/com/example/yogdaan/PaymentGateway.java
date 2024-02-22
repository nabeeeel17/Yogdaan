package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;


public class PaymentGateway extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);


        lottieAnimationView=findViewById(R.id.comingsoonfeature);
        lottieAnimationView.setAnimation(R.raw.comingsoon);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Loginactivity.isSplashPlayed=true;
                Intent icomingsoon = new Intent(PaymentGateway.this, Homeactivity.class);
                startActivity(icomingsoon);
                finish();
            }
        },2000);

    }

}