package com.example.blooddonation.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.blooddonation.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class SearchActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getSimpleName();

    private TextView dataTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dataTextView = findViewById(R.id.dataTextView);

        // Retrieve data from the server
        new RetrieveData().execute();
    }
    private class RetrieveData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String urlStr = "https://tkiet-blood-donor.000webhostapp.com/get_requests.php"; // Replace with your actual PHP file URL

            try {
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();

                    return response.toString();
                } else {
                    return "Error: " + responseCode;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = jsonObject.getString("status");

                if (status.equals("success")) {
                    JSONArray dataArray = jsonObject.getJSONArray("data");

                    StringBuilder dataBuilder = new StringBuilder();
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String number = dataObj.getString("number");
                        String message = dataObj.getString("message");

                        dataBuilder.append("Number: ").append(number).append("\n");
                        dataBuilder.append("Message: ").append(message).append("\n\n");
                    }

                    // Display the retrieved data in the TextView
                    dataTextView.setText(dataBuilder.toString());
                } else {
                    String errorMessage = jsonObject.getString("message");
                    Toast.makeText(SearchActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(SearchActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}