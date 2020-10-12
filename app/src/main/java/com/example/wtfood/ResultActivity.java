package com.example.wtfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.wtfood.fileprocess.Restaurant;
import com.example.wtfood.fileprocess.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    ListView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result = (ListView) findViewById(R.id.result_lv);
        String bookJson =  getIntent().getStringExtra("Restaurants");
        List<Restaurant> restaurants = new Gson().fromJson(bookJson, new TypeToken<List<Restaurant>>() {}.getType());
        System.out.println(restaurants.size());


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, restaurants);
        result.setAdapter(aa);

    }

}