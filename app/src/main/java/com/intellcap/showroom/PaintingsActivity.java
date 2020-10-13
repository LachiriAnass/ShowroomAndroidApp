package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaintingsActivity extends AppCompatActivity {

    ListView paintingsListView = null;
    CustomAdapter customAdapter = null;

    String paintingID;

    CustomData[] customData = new CustomData[]{
            new CustomData("Painting 3", "https://cdn.pixabay.com/photo/2020/05/14/19/49/cornwall-5171138_960_720.jpg"),
            new CustomData("Painting 4", "https://cdn.pixabay.com/photo/2020/06/28/08/03/zoo-5348334_960_720.jpg"),
            new CustomData("Painting 2", "https://azurepaintstudio.com/wp-content/uploads/2019/03/azure_paint_studio.png"),
            new CustomData("Painting 3", "https://cdn.pixabay.com/photo/2020/05/14/19/49/cornwall-5171138_960_720.jpg"),
            new CustomData("Painting 4", "https://cdn.pixabay.com/photo/2020/06/28/08/03/zoo-5348334_960_720.jpg"),
            new CustomData("Painting 1", "https://classbento.com.au/images/class/paint-and-sip-class-paint-a-sunset-brisbane-portrait-retina.jpg?1589956345%201600w"),
            new CustomData("Painting 5", "https://cdn.pixabay.com/photo/2015/05/15/14/54/horizon-768759_960_720.jpg"),
            new CustomData("Painting 3", "https://cdn.pixabay.com/photo/2020/05/14/19/49/cornwall-5171138_960_720.jpg"),
            new CustomData("Painting 6", "https://cdn.pixabay.com/photo/2020/06/11/13/56/forest-5286824_960_720.jpg")
    };

    CustomData[] paintingsCustomData;
    JSONArray paintingsObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paintings);

        Toolbar toolbar = findViewById(R.id.activityPaintingsToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Paintings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("gallery_id") != null) {

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = UserData.URL_DOMAIN + "/api/gallery/" + bundle.getString("gallery_id").toString();;

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
                                    paintingsObjects = galleryObject.getJSONArray("paintings");
                                    paintingsCustomData = new CustomData[paintingsObjects.length()];
                                    for(int i = 0; i<=paintingsObjects.length(); i++){
                                        paintingsCustomData[i] = new CustomData(paintingsObjects.getJSONObject(i).getString("title"), UserData.URL_DOMAIN + "/storage/public/painting/"  + paintingsObjects.getJSONObject(i).getString("image"));
                                    }

                                }else{
                                    Toast.makeText(getApplicationContext(), "Something went wrong. Try Again.", Toast.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e){
                                Toast.makeText(getApplicationContext(), "Something went wrong. Try Again.", Toast.LENGTH_SHORT).show();
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

        paintingsListView = findViewById(R.id.paintingsListView);

        customAdapter = new CustomAdapter(this, R.layout.custom_row, customData); // FOR TESTING
        //customAdapter = new CustomAdapter(this, R.layout.custom_row, paintingsCustomData);

        if(paintingsListView != null){
            paintingsListView.setAdapter(customAdapter);
        }

        paintingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.v("PLACE", myPlacesArray[position]);
                Toast.makeText(getApplicationContext() , position + " " +customData[position].mText +" .", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), PaintingActivity.class);
                /*       THIS SECTION HAS NOT BEEN TESTED
                try {
                    paintingID = paintingsObjects.getJSONObject(position).getString("id");
                }catch (Exception e){
                    e.printStackTrace();
                }
                i.putExtra("painting_id", paintingID);  */
                i.putExtra("painting_id", "1"); // FOR TESTING
                startActivity(i);
            }

        });

    }
}