package com.example.wtfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.wtfood.fileprocess.Location;
import com.example.wtfood.fileprocess.Restaurant;
import com.example.wtfood.fileprocess.Type;

import java.sql.SQLOutput;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class List_Activity extends AppCompatActivity {
    ListView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_);

        result = (ListView) findViewById(R.id.result_lv);
        ArrayList<String> R = getIntent().getStringArrayListExtra("R");


        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_list_item_1, R);
        result.setAdapter(aa);

    }



}