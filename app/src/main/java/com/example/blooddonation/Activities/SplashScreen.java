package com.example.blooddonation.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.blooddonation.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler= new Handler();
        handler.postDelayed(() -> {

            // Check if the user has already logged in

            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);


            if (!isLoggedIn) {

                startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
                finish();
            } else {

                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }


           // startActivity(new Intent(SplashScreen.this,LoginActivity.class));

        },2500 );
    }
}