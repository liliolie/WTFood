package com.example.wtfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

//Set the splash screen activity. Charlie Xu
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Go to the main activity and terminate current activity. Charlie Xu
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
