package com.intellcap.showroom;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {


    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Integer> images = new ArrayList<>();



        titles.add("Gallery 1");
        titles.add("Gallery 2");
        titles.add("Gallery 3");

        images.add(R.drawable.index);
        images.add(R.drawable.sun);
        images.add(R.drawable.paintings);

        ListView galleries = view.findViewById(R.id.exploreGalleriesListView);
        ExploreListAdapter adapter = new ExploreListAdapter(titles,images,getActivity());
        galleries.setAdapter(adapter);



        return view;
    }


    public void showGallery(){}

    public  class ExploreListAdapter extends BaseAdapter {

        private ArrayList<String> titles;
        //private ArrayList<Bitmap> images;
        private ArrayList<Integer> images;
        private LayoutInflater inflter;
        private Context context;

        public ExploreListAdapter(ArrayList<String> titles,ArrayList<Integer> images,Context context){
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
            view = inflter.inflate(R.layout.explore_row, null);
            ImageView img = view.findViewById(R.id.galleryImage);
            TextView galTitle = view.findViewById(R.id.galleryTitle);
            //img.setImageBitmap(images.get(i));
            Button seePaintings = view.findViewById(R.id.seePaintingsButton);
            seePaintings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showGallery();
                }
            });
            img.setImageResource(images.get(i));
            galTitle.setText(titles.get(i));
            return view;
        }
    }


}
