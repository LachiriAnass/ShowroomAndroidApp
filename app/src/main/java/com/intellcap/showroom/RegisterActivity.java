package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RegisterActivity extends AppCompatActivity {

    UserData userData = new UserData(this);


    EditText name;
    EditText email;
    EditText password;
    EditText confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.nameRegister);
        email = (EditText) findViewById(R.id.emailRegister);
        password = (EditText) findViewById(R.id.passwordRegister);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordRegister);

        if(userData.getUserData() != null  && !userData.getUserData().toString().equals("")){
            Intent i = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(i);
        }
    }

    public void doRegister(View view){
        if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "All inputs are required!", Toast.LENGTH_SHORT).show();
        }else if(!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(this, "Password inputs should be the same. Try again.", Toast.LENGTH_SHORT).show();
        }else {


            RequestQueue queue = Volley.newRequestQueue(this);
            String url = UserData.URL_DOMAIN + "/posts/3";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            String myResponse = response.toString();
                            userData.saveUserData(myResponse);
                            Intent i = new Intent(getApplicationContext(), StartActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Something went wrong. Try Again.", Toast.LENGTH_SHORT).show();
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
    }

    public void returnMain(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}
