package com.pratikpatil.blooddonation.Activities;

import static com.pratikpatil.blooddonation.Utils.Endpoints.searchcity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SearchlocActivity extends AppCompatActivity {

    private EditText editCity;
    private Button btnSearch;
    private LinearLayout recordContainer;

    private static final String BASE_URL = searchcity;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchloc);

        editCity = findViewById(R.id.editCity);
        btnSearch = findViewById(R.id.btnSearch);
        recordContainer = findViewById(R.id.recordContainer);

        requestQueue = Volley.newRequestQueue(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editCity.getText().toString().trim();
                searchRecords(city);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    private void searchRecords(final String city) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            displayRecords(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchlocActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("city", city);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void displayRecords(JSONArray records) {
        recordContainer.removeAllViews();

        try {
            for (int i = 0; i < records.length(); i++) {
                JSONObject record = records.getJSONObject(i);

                String name = record.getString("name");
                String city = record.getString("city");
                String number = record.getString("number");
                String bloodGroup = record.getString("blood_group");

                TextView textView = new TextView(SearchlocActivity.this);
                textView.setText("Name: " + name + "\nCity: " + city + "\nNumber: " + number + "\nBlood Group: " + bloodGroup);
                textView.setPadding(0, 0, 0, 16);
                textView.setTextSize(20);

                // Add a dark line separator
                View separator = new View(SearchlocActivity.this);
                separator.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                separator.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));

                // Add space between records
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 16, 0, 0);
                textView.setLayoutParams(layoutParams);

                // Make the phone number text clickable
                textView.setClickable(true);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                String phoneNumberUri = "tel:" + number;
                SpannableString spannableString = new SpannableString(textView.getText());
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumberUri));
                        startActivity(dialIntent);
                    }
                };
                spannableString.setSpan(clickableSpan, textView.getText().toString().indexOf(number), textView.getText().toString().indexOf(number) + number.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString, TextView.BufferType.SPANNABLE);

                recordContainer.addView(textView);
                recordContainer.addView(separator);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
