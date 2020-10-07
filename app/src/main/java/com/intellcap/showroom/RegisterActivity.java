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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

            RequestQueue registerQueue = Volley.newRequestQueue(RegisterActivity.this);
            String url = UserData.URL_DOMAIN + "/api/register";

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
                    params.put("name", name.getText().toString());
                    params.put("email", email.getText().toString());
                    params.put("password", password.getText().toString());

                    return params;
                }
            };

            // Add the request to the RequestQueue.
            registerQueue.add(stringRequest);


        }
    }

    public void returnMain(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}
