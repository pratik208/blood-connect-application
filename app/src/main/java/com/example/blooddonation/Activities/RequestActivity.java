package com.example.blooddonation.Activities;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.R;

import java.util.HashMap;
import java.util.Map;

public class RequestActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private EditText editTextMessage;

    private String url = "https://tkiet-blood-donor.000webhostapp.com/upload.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        editTextNumber=findViewById(R.id.editTextNumber);
        editTextMessage = findViewById(R.id.editTextMessage);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String number = editTextNumber.getText().toString();
                if(number.isEmpty() || number.length()!=10)
                {
                    showMessage("Invalid mobile number, number should be of 10 digits ");

                }
                else {
                        String message = editTextMessage.getText().toString();
                        sendDataToAPI(number, message);
                }
            }
        });
    }

    private void sendDataToAPI(final String number, final String message) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle successful response from the API
                        Toast.makeText(RequestActivity.this, "Your request is sent. Be patient and deep breath", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RequestActivity.this, MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response from the API
                        Toast.makeText(RequestActivity.this, "Error sending data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("number", number);
                params.put("message", message);
                return params;
            }
        };

        queue.add(request);
    }
    private void showMessage(String msg)
    {
        Toast.makeText(RequestActivity.this,msg ,  Toast.LENGTH_SHORT).show();
    }
}
