package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    UserData userData;
    ImageView imageView;
    EditText editProfileNameInput;
    EditText editProfileEmailInput;

    Button editProfileSubmit;
    String loginToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.activityEditProfileToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editProfileNameInput = (EditText) findViewById(R.id.editProfileNameInput);
        editProfileEmailInput = (EditText) findViewById(R.id.editProfileEmailInput);
        editProfileSubmit = (Button) findViewById(R.id.editProfileSubmit);


        userData = new UserData(this);
        imageView = (ImageView) findViewById(R.id.imageEditProfile);

        try {
            JSONObject myUser = new JSONObject(userData.getUserData());

            editProfileNameInput.setText(myUser.getString("name"));
            editProfileEmailInput.setText(myUser.getString("email"));

            String url = "https://cdn.pixabay.com/photo/2020/05/14/19/49/cornwall-5171138_960_720.jpg"; // FOR TESTING
            //String url = UserData.URL_DOMAIN + "/storage/public/profile/" + myUser.getString("image");
            Picasso.get().load(url).into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }


        editProfileSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject myUser = new JSONObject(userData.getUserData());
                    loginToken = myUser.getString("login_token");

                    RequestQueue paintingDownVoteQueue = Volley.newRequestQueue(EditProfileActivity.this);
                    String url = UserData.URL_DOMAIN + "/api/editprofile/" + myUser.getString("id");

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
                                            JSONObject userObject = myJSON.getJSONObject("user");
                                            userData.saveUserData(userObject.toString());
                                            Toast.makeText(getApplicationContext(), "Profile Updated." , Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Something went wrong. Try Again." , Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Something went wrong. Try Again." , Toast.LENGTH_SHORT).show();
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
                            //params.put("login_token", "kjhdkjhd");  //FOR TESTING
                            params.put("login_token", loginToken);
                            params.put("name", editProfileNameInput.getText().toString());
                            params.put("email", editProfileEmailInput.getText().toString());

                            return params;
                        }
                    };

                    // Add the request to the RequestQueue.
                    paintingDownVoteQueue.add(stringRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
