package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    UserData userData = new UserData(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(userData.getUserData() != null && !userData.getUserData().toString().equals("")){
            Intent i = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(i);
            finish();
        }

    }

    public void login(View view){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void register(View view){
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
        finish();
    }

}
