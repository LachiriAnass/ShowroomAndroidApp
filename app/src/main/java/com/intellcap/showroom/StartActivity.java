package com.intellcap.showroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class StartActivity extends AppCompatActivity {

    UserData userData = new UserData(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if(userData.getUserData() == null ||  userData.getUserData().toString().equals("")){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_fragment_container,new ExploreFragment()).commit();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment selectedFragment = null;

                switch (menuItem.getItemId()){
                    case R.id.search:
                        selectedFragment = new SearchFragment();
                        break;
                    case R.id.explore:
                        selectedFragment = new ExploreFragment();
                        break;
                    case R.id.user:
                        selectedFragment = new UserFragment();
                        break;
                    //this may cause a problem
                   /* default:
                        selectedFragment = new HomeFragment();
                        break;*/
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.bottom_fragment_container,selectedFragment).commit();
                return true;
            }
        });



    }

}
