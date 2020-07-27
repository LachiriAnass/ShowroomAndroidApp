package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class APITestingActivity extends AppCompatActivity {

    TextView api_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_testing);
        api_text_view = findViewById(R.id.api_text_view);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("API_RESONSE") != null) {
            api_text_view.setText(bundle.getString("API_RESONSE").toString());
        }

    }
}