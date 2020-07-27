package com.intellcap.showroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    TextView searchActivityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.activitySearchToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchActivityText = findViewById(R.id.searchActivityText);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("SEARCH_TEXT") != null){
            searchActivityText.setText(bundle.getString("SEARCH_TEXT").toString());
        }

    }
}