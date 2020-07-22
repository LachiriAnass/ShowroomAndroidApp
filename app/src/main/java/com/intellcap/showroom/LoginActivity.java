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


public class LoginActivity extends AppCompatActivity {

    UserData userData = new UserData(this);

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.emailLogin);
        password = (EditText) findViewById(R.id.passwordLogin);

        if(userData.getUserData() != null  && !userData.getUserData().toString().equals("")){
            Intent i = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(i);
        }

    }


    public void doLogin(View view){
        if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Email and password are required!", Toast.LENGTH_SHORT).show();
        }
        else {


            RequestQueue loginQueue = Volley.newRequestQueue(LoginActivity.this);
            String url = UserData.URL_DOMAIN + "/posts";

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
                    Toast.makeText(getApplicationContext(), "Something went wrong. Try Again." , Toast.LENGTH_SHORT).show();
                }
            });

            // Add the request to the RequestQueue.
            loginQueue.add(stringRequest);

        }
    }


    public void returnMain(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

}
