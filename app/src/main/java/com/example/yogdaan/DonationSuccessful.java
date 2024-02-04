package com.example.yogdaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class DonationSuccessful extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_successful);

        lottieAnimationView=findViewById(R.id.tick);
        lottieAnimationView.setAnimation(R.raw.bluetick);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Loginactivity.isSplashPlayed=true;
                Intent itick = new Intent(DonationSuccessful.this, Homeactivity.class);
                startActivity(itick);
                finish();
            }
        },5500);

    }
}