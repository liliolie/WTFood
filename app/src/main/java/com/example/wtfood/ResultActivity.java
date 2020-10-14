package com.example.wtfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.wtfood.fileprocess.Restaurant;
import com.example.wtfood.fileprocess.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResultActivity extends AppCompatActivity {
    ListView result;
    List<Restaurant> restaurants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result = (ListView) findViewById(R.id.result_lv);
        String bookJson =  getIntent().getStringExtra("Restaurants");
        Set<Restaurant> r = new Gson().fromJson(bookJson, new TypeToken<Set<Restaurant>>() {}.getType());
        restaurants = new ArrayList<>(r);
        System.out.println(restaurants.size());


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, restaurants);
        result.setAdapter(aa);


        //set the listener to the listView items [Lili]
        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
//                intent.putExtra("rname",  restaurants.get(0).toString());
                intent.putExtra("restaurant", restaurants.get(i).toString());

                startActivity(intent);
            }
        });

    }

}