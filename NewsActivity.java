package com.example.euglohapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {
    private TextView mGreetingText;
    private TextView mGreetingText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mGreetingText = (TextView) findViewById(R.id.t1);
        mGreetingText2 = (TextView) findViewById(R.id.t2);
        mGreetingText.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent gameActivityIntent = new Intent(NewsActivity.this, NEWS1.class);
                startActivity(gameActivityIntent);

            }
        });
        mGreetingText2.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent gameActivityIntent = new Intent(NewsActivity.this, NEWS2.class);
                startActivity(gameActivityIntent);

            }
        });
    }
}