package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PaintingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paintings);


        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Integer> images = new ArrayList<>();



        titles.add("Gallery 1");
        titles.add("Gallery 2");
        titles.add("Gallery 3");

        images.add(R.drawable.index);
        images.add(R.drawable.sun);
        images.add(R.drawable.paintings);

        ListView paintings = findViewById(R.id.paints);
        PaintingsListAdapter adapter = new PaintingsListAdapter(titles,images,getApplicationContext());
        paintings.setAdapter(adapter);

    }



    public class PaintingsListAdapter extends BaseAdapter {
        private ArrayList<String> titles;
        //private ArrayList<Bitmap> images;
        private ArrayList<Integer> images;
        private LayoutInflater inflter;
        private Context context;

        public PaintingsListAdapter(ArrayList<String> titles,ArrayList<Integer> images,Context context){
            this.titles = titles;
            this.images = images;
            this.context = context;
            inflter = LayoutInflater.from(context);

        }


        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.paintings_row, null);
            ImageView img = view.findViewById(R.id.paintingImage);
            TextView galTitle = view.findViewById(R.id.paintingTitle);
            img.setImageResource(images.get(i));
            galTitle.setText(titles.get(i));
            return view;
        }
    }
}
