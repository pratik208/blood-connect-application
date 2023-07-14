package com.example.blooddonation.Activities;

import static com.example.blooddonation.Utils.Endpoints.register_url;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blooddonation.R;
import com.example.blooddonation.Utils.VolleySingleton;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameET , cityET , bloodgroupET , passwordET , mobileET ;
    private Button submitButton ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameET=findViewById(R.id.name);
        cityET=findViewById(R.id.city);
        bloodgroupET=findViewById(R.id.blood_group);
        passwordET=findViewById(R.id.password);
        mobileET=findViewById(R.id.number);
        submitButton=findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name , city ,  blood_group , password , mobile ;
                name=nameET.getText().toString();
                city=cityET.getText().toString();
                blood_group=bloodgroupET.getText().toString();
                password=passwordET.getText().toString();
                mobile=mobileET.getText().toString();
             //   showMessage(name+"\n"+city+"\n"+ blood_group +"\n"+password+"\n"+mobile);
                if(isvalid(name , city , blood_group , password , mobile))
                {
                    register(name , city , blood_group , password , mobile);
                }
            }
        });

    }

    private void register(final String name, final String city, final String blood_group, final String password,
                          final String mobile) {
        StringRequest stringRequest = new StringRequest(Method.POST , register_url, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){

                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));

                   RegisterActivity.this.finish();
                }else{
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                  //  startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Something went wrong:(", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("city", city);
                params.put("blood_group", blood_group);
                params.put("password", password);
                params.put("number", mobile);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private boolean isvalid( String name , String city , String blood_group , String password , String mobile
    )
    {

        List<String>  valid_blood_groups = new ArrayList<>() ;
        valid_blood_groups.add("A+") ;
        valid_blood_groups.add("B+") ;
        valid_blood_groups.add("AB+") ;
        valid_blood_groups.add("O+") ;
        valid_blood_groups.add("A-") ;
        valid_blood_groups.add("B-") ;
        valid_blood_groups.add("AB-") ;
        valid_blood_groups.add("O-") ;
        if(name.isEmpty())
        {
            showMessage("Name is Empty");
            return false ;
        }else if(city.isEmpty())
        {
            showMessage("City cannot be  Empty");
            return false ;
        }else if(mobile.isEmpty() || mobile.length()!=10)
        {
            showMessage("Invalid mobile number, number should be of 10 digits ");
            return false  ;
        }
        else if(!valid_blood_groups.contains(blood_group))
        {
            showMessage("Blood groups are invalid choose from "+valid_blood_groups);
            return false ;
        }else if(password.isEmpty() || password.length()<6)
        {
            showMessage("password lwngth should be more than 6 ");
            return false ;
        }

        return true ;
    }
    private void showMessage(String msg)
    {
        Toast.makeText(RegisterActivity.this,msg ,  Toast.LENGTH_SHORT).show();
    }
}