package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryActivity extends AppCompatActivity {

    UserData user = new UserData(this);

    String loginToken;

    String galleryID;

    ImageView galleryImage;

    Button galleryUpVoteButton;
    Button galleryDownVoteButton;
    Button seePaintingsButton;

    TextView galleryTitle;
    TextView galleryDescription;
    TextView votingScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = findViewById(R.id.activityGalleryToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        galleryImage = (ImageView) findViewById(R.id.galleryImage);

        galleryTitle = (TextView) findViewById(R.id.galleryTitle);
        galleryDescription = (TextView) findViewById(R.id.galleryDescription);
        votingScore = (TextView) findViewById(R.id.votingScore);

        galleryUpVoteButton = (Button) findViewById(R.id.galleryUpVote);
        galleryDownVoteButton = (Button) findViewById(R.id.galleryDownVote);
        seePaintingsButton = (Button) findViewById(R.id.seePaintings);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("gallery_id") != null) {

            galleryID = bundle.getString("gallery_id").toString();

            //                           THIS SECTION HAS NOT BEEN TESTED



            RequestQueue queue = Volley.newRequestQueue(this);
            String url = UserData.URL_DOMAIN + "/api/gallery/" + bundle.getString("gallery_id").toString();

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            String myResponse = response.toString();
                            try {
                                JSONObject myJSON = new JSONObject(myResponse);
                                if(myJSON.getString("status").toString().equals("good")){
                                    JSONObject galleryObject = myJSON.getJSONObject("gallery");

                                    galleryTitle.setText(galleryObject.getString("title"));
                                    galleryDescription.setText(galleryObject.getString("description"));
                                    votingScore.setText("  Voting Score : " + galleryObject.getString("votes_average").toString());

                                    String url = UserData.URL_DOMAIN + "/storage/public/gallery/" + galleryObject.getString("image");
                                    Picasso.get().load(url).into(galleryImage);
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
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



        seePaintingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!galleryID.isEmpty()) {
                    Intent i = new Intent(getApplicationContext(), PaintingsActivity.class);
                    i.putExtra("gallery_id", galleryID);
                    startActivity(i);
                }
            }
        });

        galleryUpVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject myUser = new JSONObject(user.getUserData());
                    loginToken = myUser.getString("login_token");

                    RequestQueue galleryUpVoteQueue = Volley.newRequestQueue(GalleryActivity.this);
                    String url = UserData.URL_DOMAIN + "/api/galleryupvote/" + myUser.getString("id") + "/" + galleryID;

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
                                            JSONObject galleryObject = myJSON.getJSONObject("gallery");
                                            votingScore.setText("  Voting Score : " + galleryObject.getString("votes_average").toString());
                                            Toast.makeText(getApplicationContext(), "Gallery Upvoted." , Toast.LENGTH_SHORT).show();
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

                            return params;
                        }
                    };

                    // Add the request to the RequestQueue.
                    galleryUpVoteQueue.add(stringRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        galleryDownVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject myUser = new JSONObject(user.getUserData());
                    loginToken = myUser.getString("login_token");

                    RequestQueue galleryDownVoteQueue = Volley.newRequestQueue(GalleryActivity.this);
                    String url = UserData.URL_DOMAIN + "/api/gallerydownvote/" + myUser.getString("id") + "/" + galleryID;

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
                                            JSONObject galleryObject = myJSON.getJSONObject("gallery");
                                            votingScore.setText("  Voting Score : " + galleryObject.getString("votes_average").toString());
                                            Toast.makeText(getApplicationContext(), "Gallery Downvoted." , Toast.LENGTH_SHORT).show();
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

                            return params;
                        }
                    };

                    // Add the request to the RequestQueue.
                    galleryDownVoteQueue.add(stringRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }




}
