package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    TextView searchActivityText;
    Bundle bundle;
    String searchText;

    String paintingID;
    String galleryID;

    Button searchGalleriesButton;
    Button searchPaintingsButton;

    ListView searchGalleriesListView;
    ListView searchPaintingsListView;

    TextView searchGalleriesText;
    TextView searchPaintingsText;

    CustomAdapter customAdapter = null;
    CustomAdapter customAdapter2 = null;

    CustomData[] customData = new CustomData[]{
            new CustomData("Gallery 1", "https://classbento.com.au/images/class/paint-and-sip-class-paint-a-sunset-brisbane-portrait-retina.jpg?1589956345%201600w"),
            new CustomData("Gallery 2", "https://azurepaintstudio.com/wp-content/uploads/2019/03/azure_paint_studio.png"),
            new CustomData("Gallery 3", "https://cdn.pixabay.com/photo/2020/05/14/19/49/cornwall-5171138_960_720.jpg"),
            new CustomData("Gallery 4", "https://cdn.pixabay.com/photo/2020/06/28/08/03/zoo-5348334_960_720.jpg"),
            new CustomData("Gallery 5", "https://cdn.pixabay.com/photo/2015/05/15/14/54/horizon-768759_960_720.jpg"),
            new CustomData("Gallery 6", "https://cdn.pixabay.com/photo/2020/06/11/13/56/forest-5286824_960_720.jpg")
    };  // For testing

    CustomData[] searchGalleriesCustomData;
    JSONArray searchGalleriesObjects;
    CustomData[] searchPaintingsCustomData;
    JSONArray searchPaintingsObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.activitySearchToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchActivityText = (TextView) findViewById(R.id.searchActivityText);

        searchGalleriesButton = (Button) findViewById(R.id.searchGalleriesButton);
        searchPaintingsButton = (Button) findViewById(R.id.searchPaintingsButton);

        searchGalleriesListView = (ListView) findViewById(R.id.searchGalleriesListView);
        searchPaintingsListView = (ListView) findViewById(R.id.searchPaintingsListView);

        searchGalleriesText = (TextView) findViewById(R.id.searchGalleriesText);
        searchPaintingsText = (TextView) findViewById(R.id.searchPaintingsText);



        bundle = getIntent().getExtras();

        if(bundle.getString("SEARCH_TEXT") != null){
            searchText = bundle.getString("SEARCH_TEXT").toString();
            searchActivityText.setText(searchText);

            RequestQueue searchQueue = Volley.newRequestQueue(this);
            String url = UserData.URL_DOMAIN + "/api/search";

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
                                    searchGalleriesObjects = myJSON.getJSONArray("galleries");
                                    searchGalleriesCustomData = new CustomData[searchGalleriesObjects.length()];
                                    for(int i = 0; i<=searchGalleriesObjects.length(); i++){
                                        searchGalleriesCustomData[i] = new CustomData(searchGalleriesObjects.getJSONObject(i).getString("title"), UserData.URL_DOMAIN + "/storage/public/gallery/"  + searchGalleriesObjects.getJSONObject(i).getString("image"));
                                    }

                                    searchPaintingsObjects = myJSON.getJSONArray("paintings");
                                    searchPaintingsCustomData = new CustomData[searchPaintingsObjects.length()];
                                    for(int i = 0; i<=searchPaintingsObjects.length(); i++){
                                        searchPaintingsCustomData[i] = new CustomData(searchPaintingsObjects.getJSONObject(i).getString("title"), UserData.URL_DOMAIN + "/storage/public/painting/"  + searchPaintingsObjects.getJSONObject(i).getString("image"));
                                    }

                                    connectGalleriesAdapter();
                                    connectPaintingsAdapter();
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
                    params.put("search_text", searchText);

                    return params;
                }
            };

            // Add the request to the RequestQueue.
            searchQueue.add(stringRequest);
        }

        searchGalleriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchGalleriesListView.setVisibility(View.VISIBLE);
                searchGalleriesText.setVisibility(View.VISIBLE);
                searchPaintingsListView.setVisibility(View.GONE);
                searchPaintingsText.setVisibility(View.GONE);
            }
        });

        searchPaintingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchGalleriesListView.setVisibility(View.GONE);
                searchGalleriesText.setVisibility(View.GONE);
                searchPaintingsListView.setVisibility(View.VISIBLE);
                searchPaintingsText.setVisibility(View.VISIBLE);
            }
        });

        connectGalleriesAdapter();
        connectPaintingsAdapter();
    }

    public void connectGalleriesAdapter(){
        customAdapter = new CustomAdapter(this, R.layout.custom_row, customData); // For Testing
        //customAdapter = new CustomAdapter(this, R.layout.custom_row, searchGalleriesCustomData);

        if(searchGalleriesListView != null){
            searchGalleriesListView.setAdapter(customAdapter);
        }

        searchGalleriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.v("PLACE", myPlacesArray[position]);
                Toast.makeText(getApplicationContext(), position + " " +customData[position].mText +" .", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), GalleryActivity.class);
                /*       THIS SECTION HAS NOT BEEN TESTED
                try {
                    galleryID = searchGalleriesObjects.getJSONObject(position).getString("id");
                }catch (Exception e){
                    e.printStackTrace();
                }
                i.putExtra("gallery_id", galleryID);*/
                i.putExtra("gallery_id", "1"); // FOR TESTING
                startActivity(i);
            }
        });
    }

    public void connectPaintingsAdapter(){
        customAdapter2 = new CustomAdapter(this, R.layout.custom_row, customData); // For Testing
        //customAdapter2 = new CustomAdapter(this, R.layout.custom_row, searchPaintingsCustomData);

        if(searchPaintingsListView != null){
            searchPaintingsListView.setAdapter(customAdapter2);
        }

        searchPaintingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.v("PLACE", myPlacesArray[position]);
                Toast.makeText(getApplicationContext(), position + " " +customData[position].mText +" .", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), PaintingActivity.class);
                /*       THIS SECTION HAS NOT BEEN TESTED
                try {
                    paintingID = searchPaintingsObjects.getJSONObject(position).getString("id");
                }catch (Exception e){
                    e.printStackTrace();
                }
                i.putExtra("painting_id", paintingID);*/
                i.putExtra("painting_id", "1"); // FOR TESTING
                startActivity(i);
            }
        });
    }
}