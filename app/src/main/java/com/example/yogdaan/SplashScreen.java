package com.example.yogdaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        lottieAnimationView=findViewById(R.id.splash);
        lottieAnimationView.setAnimation(R.raw.lottie2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Loginactivity.isSplashPlayed=true;
                Intent isplash = new Intent(SplashScreen.this, Loginactivity.class);
                startActivity(isplash);
                finish();
            }
        },3000);

    }

}