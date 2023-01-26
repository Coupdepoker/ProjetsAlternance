package com.example.euglohapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button btn5 = (Button) findViewById(R.id.button5);
        Button btn6 = (Button) findViewById(R.id.button6);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail("Base De Données");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail("Algèbre");
            }
        });
    }

    protected void sendEmail(String matiere) {
        Log.i("Send email", "");

        String[] TO = {"wolff@lri.fr"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Demande d'Inscription");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Je souhaite m'inscrire en "+matiere);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CourseActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}