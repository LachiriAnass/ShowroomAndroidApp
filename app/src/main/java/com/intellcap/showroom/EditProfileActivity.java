package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.activityEditProfileToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ImageView imageView = (ImageView) findViewById(R.id.imageEditProfile);
        String url = "http://192.168.10.10/storage/public/gallery/default.jpg";
        Picasso.get().load(url).into(imageView);


    }
}
