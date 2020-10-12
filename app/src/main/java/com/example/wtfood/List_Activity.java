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

import java.util.ArrayDeque;
import java.util.ArrayList;

public class List_Activity extends AppCompatActivity {
    public ListView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_);

        result = (ListView) findViewById(R.id.result_lv);
        ArrayList<String> arr= new ArrayList<>();
        ArrayList<Restaurant> AR= new ArrayList<>();
        AR.add(createRestaurant("Hero burger"));
        AR.add(createRestaurant("KFC"));
        AR.add(createRestaurant("Subway"));
        AR.add(createRestaurant("Tacobell"));
        AR.add(createRestaurant("BurgerKing"));
        AR.add(createRestaurant("Raku"));

        for (int i = 0; i < AR.size(); i ++){
            arr.add(display(AR.get(i)));
        }

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arr);
        result.setAdapter(aa);
    }

    public String display(Restaurant r){
        String Address = r.getAddress();
        String name = r.getName();
        int rating = r.getRating();
        int price = r.getPrice();
        String phone = r.getPhone();
        String delivery = "";
        String type = "";
        if(r.isDeliveryService()){
            delivery = "Deliverable.";
        }
        else {
            delivery = "Unable to for delivery.";
        }

        if(r.getType().equals(Type.chinesefood))
        {
            type = "Chinese Food!";
        }
        if(r.getType().equals(Type.fastfood))
        {
            type = "Fast Food!";
        }

        return name + " \n " + Address + " | " + phone + " \n " + "Rating: " + Integer.toString(rating) + " \n " + "Price: "+
                Integer.toString(price) + " \n " + delivery + " \n " + "FoodType: " + type;

    }

    private Restaurant createRestaurant(String name){
       Restaurant r = new Restaurant(name);
       return r;
    }
}