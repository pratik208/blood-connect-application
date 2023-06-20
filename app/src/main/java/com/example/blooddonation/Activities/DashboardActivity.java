package com.example.blooddonation.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.blooddonation.R;

public class DashboardActivity extends AppCompatActivity {

    CardView help_to_others , raise_request , search_donors , help , logout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        help_to_others = findViewById(R.id.help_to_others);
        raise_request = findViewById(R.id.raise_request);

        search_donors = findViewById(R.id.search_donors);
        help = findViewById(R.id.help);
        logout = findViewById(R.id.logout);

        help_to_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            }
        });

        raise_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,RequestActivity.class));
            }
        });
        search_donors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, SearchlocActivity.class));
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, HelpActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            }
        });




    }
}