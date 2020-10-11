package com.example.wtfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.wtfood.fileprocess.FileProcess;
import com.example.wtfood.fileprocess.Restaurant;
import com.example.wtfood.rbtree.RBTree;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RBTree priceTree;
    private RBTree raringTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        priceTree = new RBTree("price");
        raringTree = new RBTree("rating");

        try {
            List<Restaurant> restaurants = new FileProcess().fileRead(getAssets().open("list.json"));
            for (Restaurant r : restaurants) {
                priceTree.insert(r);
                raringTree.insert(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logoButton(View view){
        Intent intent = new Intent(this, InfoPage.class);
        startActivity(intent);
    }

    public void tutorialButton(View view){
        Intent intent = new Intent(this, TutorialPage.class);
        startActivity(intent);
    }
}