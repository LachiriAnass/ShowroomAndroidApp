package com.intellcap.showroom;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    UserData userData;

    private ArrayList<String> titles = new ArrayList<>();
    //private ArrayList<Bitmap> images;
    private ArrayList<Integer> images = new ArrayList<>();


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        userData = new UserData(container.getContext());

        Button editprofileButton = view.findViewById(R.id.editProfile);
        editprofileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditProfileActivity.class);
                startActivity(intent);
            }
        });

        Button logOut = view.findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userData.deleteUserData();
                Toast.makeText(container.getContext(), "Logging Out ...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        //ImageView imageView = container.findViewById(R.id.profileImage);
        //String url = "http://192.168.1.18/storage/public/profile/omar_al_mokhtar12_1595355245.jpg";
        //Picasso.get().load(url).into(imageView);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView imageView = (ImageView) getView().findViewById(R.id.profileImage);
        String url = "https://classbento.com.au/images/class/paint-and-sip-class-paint-a-sunset-brisbane-portrait-retina.jpg?1589956345%201600w";
        Picasso.get().load(url).into(imageView);
    }

}
