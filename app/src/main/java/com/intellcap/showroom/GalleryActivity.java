package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    ListView galleryPaintingsList = null;
    CustomAdapter customAdapter = null;

    CustomData[] customData = new CustomData[]{
            new CustomData("Painting 1", "https://classbento.com.au/images/class/paint-and-sip-class-paint-a-sunset-brisbane-portrait-retina.jpg?1589956345%201600w"),
            new CustomData("Painting 2", "https://i.pinimg.com/236x/c2/6e/18/c26e181e38ac72e43666c16269e201e9--paint-bar-big-eyes.jpg"),
            new CustomData("Painting 3", "https://azurepaintstudio.com/wp-content/uploads/2019/03/azure_paint_studio.png"),
            new CustomData("Painting 4", "https://i.pinimg.com/236x/c2/6e/18/c26e181e38ac72e43666c16269e201e9--paint-bar-big-eyes.jpg"),
            new CustomData("Painting 5", "https://classbento.com.au/images/class/paint-and-sip-class-paint-a-sunset-brisbane-portrait-retina.jpg?1589956345%201600w"),
            new CustomData("Painting 6", "https://i.pinimg.com/236x/c2/6e/18/c26e181e38ac72e43666c16269e201e9--paint-bar-big-eyes.jpg"),
            new CustomData("Painting 6", "https://azurepaintstudio.com/wp-content/uploads/2019/03/Namaste-300x239.jpg"),
            new CustomData("Painting 6", "https://azurepaintstudio.com/wp-content/uploads/2019/03/azure_paint_studio.png")
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        galleryPaintingsList = (ListView) findViewById(R.id.galleryPaintingsList);

        customAdapter = new CustomAdapter(this, R.layout.custom_row, customData);

        if(galleryPaintingsList != null){
            galleryPaintingsList.setAdapter(customAdapter);
        }

        galleryPaintingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.v("PLACE", myPlacesArray[position]);
                Toast.makeText(getApplicationContext() ,Integer.toString(position) + " " +customData[position].mText +" .", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), PaintingActivity.class);
                startActivity(i);
            }

        });

    }


}
