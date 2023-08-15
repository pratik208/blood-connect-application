package com.pratikpatil.blooddonation.Activities;
import static com.pratikpatil.blooddonation.Utils.Endpoints.deleterecord;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class DeleteActivity extends AppCompatActivity {

    private EditText editTextRecordId;
    private Button buttonDelete;

    private RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        editTextRecordId = findViewById(R.id.editTextRecordId);
        buttonDelete = findViewById(R.id.buttonDelete);

        requestQueue = Volley.newRequestQueue(this);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recordId = editTextRecordId.getText().toString().trim();
                deleteRecord(recordId);
            }
        });
    }

    private void deleteRecord(final String recordId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, deleterecord,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DeleteActivity.this, "Record deleted successfully", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("isLoggedIn");
                        editor.apply();
                        startActivity(new Intent(DeleteActivity.this , LoginActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DeleteActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("number", recordId);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
