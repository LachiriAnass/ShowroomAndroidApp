package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class PaintingsActivity extends AppCompatActivity {

    ListView paintingsListView = null;
    CustomAdapter customAdapter = null;

    CustomData[] customData = new CustomData[]{
            new CustomData("Painting 1", "https://classbento.com.au/images/class/paint-and-sip-class-paint-a-sunset-brisbane-portrait-retina.jpg?1589956345%201600w"),
            new CustomData("Painting 2", "https://azurepaintstudio.com/wp-content/uploads/2019/03/azure_paint_studio.png"),
            new CustomData("Painting 3", "https://cdn.pixabay.com/photo/2020/05/14/19/49/cornwall-5171138_960_720.jpg"),
            new CustomData("Painting 4", "https://cdn.pixabay.com/photo/2020/06/28/08/03/zoo-5348334_960_720.jpg"),
            new CustomData("Painting 5", "https://cdn.pixabay.com/photo/2015/05/15/14/54/horizon-768759_960_720.jpg"),
            new CustomData("Painting 6", "https://cdn.pixabay.com/photo/2020/06/11/13/56/forest-5286824_960_720.jpg")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paintings);

        Toolbar toolbar = findViewById(R.id.activityPaintingsToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Paintings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paintingsListView = findViewById(R.id.paintingsListView);

        customAdapter = new CustomAdapter(this, R.layout.custom_row, customData);

        if(paintingsListView != null){
            paintingsListView.setAdapter(customAdapter);
        }

        paintingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.v("PLACE", myPlacesArray[position]);
                Toast.makeText(getApplicationContext() , position + " " +customData[position].mText +" .", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), PaintingActivity.class);
                startActivity(i);
            }

        });

    }
}