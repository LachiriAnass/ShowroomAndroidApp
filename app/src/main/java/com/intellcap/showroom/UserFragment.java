package com.intellcap.showroom;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    private ArrayList<String> titles = new ArrayList<>();
    //private ArrayList<Bitmap> images;
    private ArrayList<Integer> images = new ArrayList<>();


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        Button editprofileButton = view.findViewById(R.id.editProfile);
        editprofileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditProfileActivity.class);
                startActivity(intent);
            }
        });

        Button seeGalleries = view.findViewById(R.id.seeGaleries);
        seeGalleries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),GalleriesActivity.class);
                startActivity(intent);
            }
        });




        return view;
    }

}
