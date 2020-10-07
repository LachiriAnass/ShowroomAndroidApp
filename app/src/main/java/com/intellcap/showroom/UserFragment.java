package com.intellcap.showroom;


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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    UserData userData;
    ImageView imageView;
    TextView userFragmentName;
    TextView userFragmentEmail;

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
        //String url = "http://192.168.1.18/storage/public/profile/default_avatar.jpg";
        //Picasso.get().load(url).into(imageView);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = getView().findViewById(R.id.fragmentUserToolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("  Profile");

        userFragmentName = (TextView) getView().findViewById(R.id.userFragmentName);
        userFragmentEmail = (TextView) getView().findViewById(R.id.userFragmentEmail);
        imageView = (ImageView) getView().findViewById(R.id.profileImage);

        try {
            JSONObject myUser = new JSONObject(userData.getUserData());

            userFragmentName.setText(myUser.getString("name"));
            userFragmentEmail.setText(myUser.getString("email"));

            String url = "https://cdn.pixabay.com/photo/2020/07/14/21/02/nature-5405758_960_720.jpg"; // FOR TESTING
            //String url = UserData.URL_DOMAIN + "/storage/public/profile/" + myUser.getString("image");
            Picasso.get().load(url).into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
