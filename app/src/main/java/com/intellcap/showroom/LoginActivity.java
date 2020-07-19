package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    protected static boolean loginSuccesful = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernameLogin);
        password = findViewById(R.id.passwordLogin);



    }


    public void doLogin(View view){
        if(username.toString().isEmpty() || password.toString().isEmpty()) {
            Toast.makeText(this, "Both username and password are required!", Toast.LENGTH_SHORT);
        }
        else {
            Intent intent = new Intent(getApplicationContext(),StartActivity.class);
            startActivity(intent);
            loginSuccesful = true;
            finish();
        }
    }
}
