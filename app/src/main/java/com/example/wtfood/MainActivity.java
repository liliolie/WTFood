package com.example.wtfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.wtfood.fileprocess.FileProcess;
import com.example.wtfood.fileprocess.Restaurant;
import com.example.wtfood.fileprocess.Type;
import com.example.wtfood.rbtree.Node;
import com.example.wtfood.rbtree.RBTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RBTree priceTree;
    private RBTree raringTree;
    private ArrayList<String> restaurant_info;



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

        // Here can add our data in the array list.
        restaurant_info = new ArrayList<>();
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(createRestaurant("Hero burger"));
        restaurants.add(createRestaurant("KFC"));
        restaurants.add(createRestaurant("Subway"));
        restaurants.add(createRestaurant("Tacobell"));
        restaurants.add(createRestaurant("BurgerKing"));
        restaurants.add(createRestaurant("Raku"));

        for (int i = 0; i < restaurants.size(); i++) {
            restaurant_info.add(display(restaurants.get(i)));
        }
        ImageButton go = (ImageButton) findViewById(R.id.goButton);
        go.setOnClickListener(l1);
    }


    public void logoButton(View view) {
        Intent intent = new Intent(this, InfoPage.class);
        startActivity(intent);
    }

    public void tutorialButton(View view) {
        Intent intent = new Intent(this, TutorialPage.class);
        startActivity(intent);
    }

    private View.OnClickListener l1 = new View.OnClickListener() {
        public void onClick(View v) {
            // Passing the array to List_Activity.
            Intent intent = new Intent(getApplicationContext(), List_Activity.class);
            intent.putExtra("R", restaurant_info);
            startActivity(intent);
        }
    };


    public String display(Restaurant r) {
        String Address = r.getAddress();
        String name = r.getName();
        int rating = r.getRating();
        int price = r.getPrice();
        String phone = r.getPhone();
        String delivery = "";
        String type = "";
        if (r.isDeliveryService()) {
            delivery = "Deliverable.";
        } else {
            delivery = "Unable to for delivery.";
        }

        if (r.getType().equals(Type.chinesefood)) {
            type = "Chinese Food!";
        }
        if (r.getType().equals(Type.fastfood)) {
            type = "Fast Food!";
        }

        return name + " \n " + Address + " | " + phone + " \n " + "Rating: " + Integer.toString(rating) + " \n " + "Price: " +
                Integer.toString(price) + " \n " + delivery + " \n " + "FoodType: " + type;

    }

    private Restaurant createRestaurant(String name) {
        Restaurant r = new Restaurant(name);
        return r;
    }
}
