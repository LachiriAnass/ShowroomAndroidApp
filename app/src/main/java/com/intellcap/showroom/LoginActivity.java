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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    UserData userData = new UserData(this);


    String userJSONObject = "{\"id\":1,\"name\":\"Admin\",\"image\":\"default_avatar.jpg\",\"email\":\"admin@gmail.com\",\"email_verified_at\":null,\"login_token\":\"Zc20PUmxe9rDDmiBfSr2rAnSqXm3KYKrmwdyr0HBKDfRUtBOu2LEjFQd3gwY\",\"votes_average\":0,\"created_at\":\"2020-08-05T21:59:47.000000Z\",\"updated_at\":\"2020-08-13T13:33:19.000000Z\"}";

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
            String url = UserData.URL_DOMAIN + "/api/login";

            // Request a string response from the provided URL.

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            String myResponse = response.toString();
                            try {
                                JSONObject myJSON = new JSONObject(myResponse);
                                if(myJSON.getString("status").toString().equals("good")){
                                    userData.saveUserData(myJSON.getJSONObject("user").toString());
                                    Intent i = new Intent(getApplicationContext(), StartActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Something went wrong. Try Again." , Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email.getText().toString());
                    params.put("password", password.getText().toString());

                    return params;
                }
            };

            // Add the request to the RequestQueue.
            loginQueue.add(stringRequest);

            userData.saveUserData(userJSONObject);
            Intent i = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(i);
            finish();

        }
    }


    public void returnMain(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

}
