package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PaintingActivity extends AppCompatActivity {

    UserData user = new UserData(this);

    String loginToken;

    ImageView imagePaintingActivity;

    String paintingID;

    ImageView paintingImage;

    Button paintingUpVoteButton;
    Button paintingDownVoteButton;

    TextView paintingTitle;
    TextView paintingDescription;
    TextView paintingVotingScore;
    TextView paintingPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painting);

        Toolbar toolbar = findViewById(R.id.activityPaintingToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Painting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagePaintingActivity = (ImageView) findViewById(R.id.imagePaintingActivity);

        paintingImage = (ImageView) findViewById(R.id.imagePaintingActivity);

        paintingTitle = (TextView) findViewById(R.id.paintingTitle);
        paintingDescription = (TextView) findViewById(R.id.paintingDescription);
        paintingVotingScore = (TextView) findViewById(R.id.paintingVotingScore);
        paintingPrice = (TextView) findViewById(R.id.paintingPrice);

        paintingUpVoteButton = (Button) findViewById(R.id.paintingUpVote);
        paintingDownVoteButton = (Button) findViewById(R.id.paintingDownVote);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("painting_id") != null) {

            paintingID = bundle.getString("painting_id").toString();

            //                           THIS SECTION HAS NOT BEEN TESTED



            RequestQueue queue = Volley.newRequestQueue(this);
            String url = UserData.URL_DOMAIN + "/api/painting/" + paintingID;

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
                                    JSONObject paintingObject = myJSON.getJSONObject("painting");

                                    paintingTitle.setText(paintingObject.getString("title"));
                                    paintingDescription.setText(paintingObject.getString("description"));
                                    paintingPrice.setText("  Painting Price : $" + paintingObject.getString("price").toString());
                                    paintingVotingScore.setText("  Voting Score : " + paintingObject.getString("votes_average").toString());

                                    String url = UserData.URL_DOMAIN + "/storage/public/painting/" + paintingObject.getString("image");
                                    Picasso.get().load(url).into(paintingImage);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Something went wrong. Try Again.", Toast.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Something went wrong. Try Again.", Toast.LENGTH_SHORT).show();
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

        String url = "https://cdn.pixabay.com/photo/2020/07/14/21/02/nature-5405758_960_720.jpg";
        Picasso.get().load(url).into(imagePaintingActivity);


        paintingUpVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject myUser = new JSONObject(user.getUserData());
                    loginToken = myUser.getString("login_token");

                    RequestQueue paintingUpVoteQueue = Volley.newRequestQueue(PaintingActivity.this);
                    String url = UserData.URL_DOMAIN + "/api/paintingupvote/" + myUser.getString("id") + "/" + paintingID;

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
                                            JSONObject paintingObject = myJSON.getJSONObject("painting");
                                            paintingVotingScore.setText("  Voting Score : " + paintingObject.getString("votes_average").toString());
                                            Toast.makeText(getApplicationContext(), "Painting Upvoted." , Toast.LENGTH_SHORT).show();
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
                    paintingUpVoteQueue.add(stringRequest);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something went wrong. Try Again!!" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        paintingDownVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject myUser = new JSONObject(user.getUserData());
                    loginToken = myUser.getString("login_token");

                    RequestQueue paintingDownVoteQueue = Volley.newRequestQueue(PaintingActivity.this);
                    String url = UserData.URL_DOMAIN + "/api/paintingdownvote/" + myUser.getString("id") + "/" + paintingID;

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
                                            JSONObject paintingObject = myJSON.getJSONObject("painting");
                                            paintingVotingScore.setText("  Voting Score : " + paintingObject.getString("votes_average").toString());
                                            Toast.makeText(getApplicationContext(), "Painting Downvoted." , Toast.LENGTH_SHORT).show();
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
                    paintingDownVoteQueue.add(stringRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


}
