package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PaintingActivity extends AppCompatActivity {

    ImageView imagePaintingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painting);

        Toolbar toolbar = findViewById(R.id.activityPaintingToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Painting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagePaintingActivity = (ImageView) findViewById(R.id.imagePaintingActivity);

        String url = "https://cdn.pixabay.com/photo/2020/07/14/21/02/nature-5405758_960_720.jpg";
        Picasso.get().load(url).into(imagePaintingActivity);
    }


}
