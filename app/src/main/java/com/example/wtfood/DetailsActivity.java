package com.example.wtfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView detailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // get the information and show
        Intent intent = getIntent();
        String details = intent.getStringExtra("restaurant");

        detailText = (TextView) findViewById(R.id.detailText);
        detailText.setText(details);


        // set the ImageView
        ImageView image1 = (ImageView) findViewById(R.id.image1);
        int imageResource = 0;
        if (details.contains("Fast Food")) {
            imageResource = getResources().getIdentifier("@drawable/pubfood", null, this.getPackageName());
        }
        if (details.contains("Chinese")) {
            imageResource = getResources().getIdentifier("@drawable/chinese", null, this.getPackageName());
        }
        if (details.contains("Cafe")) {
            imageResource = getResources().getIdentifier("@drawable/coffee", null, this.getPackageName());
        }
        if (details.contains("Bakery")) {
            imageResource = getResources().getIdentifier("@drawable/brunchtea", null, this.getPackageName());
        }
        if (details.contains("Turkish")) {
            imageResource = getResources().getIdentifier("@drawable/turkey", null, this.getPackageName());
        }
        if (details.contains("Burgers")) {
            imageResource = getResources().getIdentifier("@drawable/burger", null, this.getPackageName());
        }
        if (details.contains("Italian")) {
            imageResource = getResources().getIdentifier("@drawable/italian", null, this.getPackageName());
        }
        if (details.contains("Mexico")) {
            imageResource = getResources().getIdentifier("@drawable/mexico", null, this.getPackageName());
        }
        if (details.contains("wine")) {
            imageResource = getResources().getIdentifier("@drawable/finedining", null, this.getPackageName());
        }
        if (details.contains("Japanese")) {
            imageResource = getResources().getIdentifier("@drawable/japanese", null, this.getPackageName());
        }
        image1.setImageResource(imageResource);

    }

}
