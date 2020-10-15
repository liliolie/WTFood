package com.example.wtfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // get the information and show [Lili]
        Intent intent = getIntent();
        String details = intent.getStringExtra("restaurant");

        String rname = intent.getStringExtra("rname");
        EditText text = findViewById(R.id.rnameV);
//        text.setText(rname);

        // set the ImageView
        ImageView image1 = (ImageView) findViewById(R.id.image1);
        int imageResource = 0;
        if (details.contains("Fast Food")){
            imageResource = getResources().getIdentifier("@drawable/pubfood", null, this.getPackageName());
        }if (details.contains("Chinese")) {
            imageResource = getResources().getIdentifier("@drawable/japanese", null, this.getPackageName());
        }if (details.contains("Cafe")) {
            imageResource = getResources().getIdentifier("@drawable/coffee", null, this.getPackageName());
        }if (details.contains("Bakery")) {
            imageResource = getResources().getIdentifier("@drawable/brunchtea", null, this.getPackageName());
        }if (details.contains("Turkish")) {
            imageResource = getResources().getIdentifier("@drawable/turkey", null, this.getPackageName());
        }if (details.contains("Fine Dinning")) {
            imageResource = getResources().getIdentifier("@drawable/finedining", null, this.getPackageName());
        }if (details.contains("French")) {
            imageResource = getResources().getIdentifier("@drawable/brunch", null, this.getPackageName());
        }
        image1.setImageResource(imageResource);

    }
}