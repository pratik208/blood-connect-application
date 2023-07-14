package com.example.blooddonation.Activities;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonation.R;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    private ListView listViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        listViewData = findViewById(R.id.listViewData);

        // Fetch the data from the server
        // Parse the JSON response and extract the number and message data

        // Create an ArrayList to store the data
        ArrayList<String> dataList = new ArrayList<>();

        // Add the number and message data to the ArrayList
       // dataList.add("Number: " + number1 + ", Message: " + message1);
    //    dataList.add("Number: " + number2 + ", Message: " + message2);
        // Add more data as needed

        // Create an ArrayAdapter and set it on the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_data, dataList);
        listViewData.setAdapter(adapter);
    }
}
