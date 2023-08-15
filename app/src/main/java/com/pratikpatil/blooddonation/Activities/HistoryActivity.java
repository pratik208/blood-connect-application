package com.pratikpatil.blooddonation.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.pratikpatil.blooddonation.Adapters.RequestHistory;
import com.pratikpatil.blooddonation.DataModels.RequestDataModel;
import com.example.blooddonation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<RequestDataModel> requestDataModels;
    private RequestHistory requestHistory;
  //  Button buttton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);



        requestDataModels = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestHistory = new RequestHistory(requestDataModels, this);
        recyclerView.setAdapter(requestHistory);

        

        sendNumberToServer();
    }

    private void sendNumberToServer() {
        SendNumber sendNumberTask = new SendNumber();
        sendNumberTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    class SendNumber extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String urlStr = "https://tkiet-blood-donor.000webhostapp.com/history.php"; // Modify URL

            try {
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Get the static number from your LoginActivity's static variable numb
                String numberToSend = LoginActivity.numb; // Replace with the actual static number

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write("number=" + numberToSend); // Send the number as a parameter
                writer.flush();
                writer.close();
                outputStream.close();

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

                    requestDataModels.clear();

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String number = dataObj.getString("number");
                        String message = dataObj.getString("message");

                        RequestDataModel requestDataModel = new RequestDataModel(number, message);
                        requestDataModels.add(requestDataModel);
                    }

                    requestHistory.notifyDataSetChanged();
                } else {
                    String errorMessage = jsonObject.getString("message");
                    Toast.makeText(HistoryActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(HistoryActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }




}