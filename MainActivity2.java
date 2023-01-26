package com.example.euglohapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    private Button mButton4;
    private Button mButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent gameActivityIntent = new Intent(MainActivity2.this, NewsActivity.class);
                startActivity(gameActivityIntent);

            }
        });
        mButton4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent gameActivityIntent = new Intent(MainActivity2.this, CourseActivity.class);
                startActivity(gameActivityIntent);

            }
        });
    }



}