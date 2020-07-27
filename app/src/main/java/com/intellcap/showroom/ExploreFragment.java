package com.intellcap.showroom;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {

    private static final String URL_DOMAIN = "URL_DOMAIN";
    UserData userData;
    String urlDomain;
    TextView myTextView;

    Button latestButton;
    Button mostRatedButton;

    TextView latestText;
    TextView mostRatedText;


    ListView latestGalleriesList = null;
    ListView mostRatedGalleriesList = null;
    CustomAdapter customAdapter = null;
    CustomAdapter customAdapter2 = null;

    CustomData[] customData = new CustomData[]{
            new CustomData("Gallery 1", "https://classbento.com.au/images/class/paint-and-sip-class-paint-a-sunset-brisbane-portrait-retina.jpg?1589956345%201600w"),
            new CustomData("Gallery 2", "https://azurepaintstudio.com/wp-content/uploads/2019/03/azure_paint_studio.png"),
            new CustomData("Gallery 3", "https://cdn.pixabay.com/photo/2020/05/14/19/49/cornwall-5171138_960_720.jpg"),
            new CustomData("Gallery 4", "https://cdn.pixabay.com/photo/2020/06/28/08/03/zoo-5348334_960_720.jpg"),
            new CustomData("Gallery 5", "https://cdn.pixabay.com/photo/2015/05/15/14/54/horizon-768759_960_720.jpg"),
            new CustomData("Gallery 6", "https://cdn.pixabay.com/photo/2020/06/11/13/56/forest-5286824_960_720.jpg")
    };


    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        userData = new UserData(container.getContext());




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //myTextView = (TextView) getView().findViewById(R.id.exploreText);
        //String myString = userData.getUserData();
        //myTextView.setText(myString);

        Toolbar toolbar = getView().findViewById(R.id.fragmentExploreToolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("  Explore");

        latestText = getView().findViewById(R.id.latestText);
        latestGalleriesList = getView().findViewById(R.id.exploreLatestGalleries);

        customAdapter = new CustomAdapter(getActivity(), R.layout.custom_row, customData);

        if(latestGalleriesList != null){
            latestGalleriesList.setAdapter(customAdapter);
        }

        latestGalleriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.v("PLACE", myPlacesArray[position]);
                Toast.makeText(getContext(), position + " " +customData[position].mText +" .", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), GalleryActivity.class);
                startActivity(i);
            }
        });



        mostRatedText = getView().findViewById(R.id.mostRatedText);
        mostRatedGalleriesList = getView().findViewById(R.id.exploreMostRatedGalleries);
        customAdapter2 = new CustomAdapter(getActivity(), R.layout.custom_row, customData);

        if(mostRatedGalleriesList != null){
            mostRatedGalleriesList.setAdapter(customAdapter2);
        }

        mostRatedGalleriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.v("PLACE", myPlacesArray[position]);
                Toast.makeText(getContext(), position + " " +customData[position].mText +" .", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), GalleryActivity.class);
                startActivity(i);
            }
        });



        latestButton = getView().findViewById(R.id.latestButton);
        latestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                latestGalleriesList.setVisibility(View.VISIBLE);
                latestText.setVisibility(View.VISIBLE);
                mostRatedGalleriesList.setVisibility(View.GONE);
                mostRatedText.setVisibility(View.GONE);
            }
        });

        mostRatedButton = getView().findViewById(R.id.mostRatedButton);
        mostRatedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                latestGalleriesList.setVisibility(View.GONE);
                latestText.setVisibility(View.GONE);
                mostRatedGalleriesList.setVisibility(View.VISIBLE);
                mostRatedText.setVisibility(View.VISIBLE);
            }
        });


    }
}
