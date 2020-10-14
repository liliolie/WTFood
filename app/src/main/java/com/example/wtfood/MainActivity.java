package com.example.wtfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.wtfood.fileprocess.FileProcess;
import com.example.wtfood.fileprocess.Restaurant;
import com.example.wtfood.fileprocess.Type;
import com.example.wtfood.parser.MyTokenizer;
import com.example.wtfood.parser.Parser;
import com.example.wtfood.parser.Query;
import com.example.wtfood.rbtree.RBTree;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private RBTree priceTree;
    private RBTree raringTree;
    private Set<Restaurant> restaurants = null;


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
            List<Restaurant> restaurants = new FileProcess().jsonFileRead(getAssets().open("list.json"));
            for (Restaurant r : restaurants) {
                priceTree.insert(r);
                raringTree.insert(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Here can add our data in the array list.
        restaurants = new HashSet<>();
        restaurants.add(new Restaurant("Hero burger"));
        restaurants.add(new Restaurant("KFC"));
        restaurants.add(new Restaurant("Subway"));
        restaurants.add(new Restaurant("Tacobell"));
        restaurants.add(new Restaurant("BurgerKing"));
        restaurants.add(new Restaurant("Raku"));


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

            // an example to doing querying

            EditText et = (EditText) findViewById(R.id.query);
            String query = et.getText().toString();


//            if (et != null) {
//                MyTokenizer t = new MyTokenizer(query);
//                Query q = new Parser(t).parseAttribute();
//                for (int i = 0; i < Parser.totalQuery.size(); i++) {
//                    if (Parser.totalQuery.get(i).getCompareAttribute().equals("Price")) {
//                        System.out.println("Pricing " + Parser.totalQuery.get(i).getSign());
//                        System.out.println("Pricing " + Integer.parseInt(Parser.totalQuery.get(i).getValue()));
//                        restaurants = priceTree.searchForNodes(Parser.totalQuery.get(i).getSign(), Integer.parseInt(Parser.totalQuery.get(i).getValue()));
//                    }
//
//                    System.out.println("Rating " + Parser.totalQuery.get(i).getSign());
//                    System.out.println("Rating " + Integer.parseInt(Parser.totalQuery.get(i).getValue()));
//                    if (Parser.totalQuery.get(i).getCompareAttribute().equals("Rating")) {
//                        if (restaurants == null) {
//                            restaurants = raringTree.searchForNodes(Parser.totalQuery.get(i).getSign(), Integer.parseInt(Parser.totalQuery.get(i).getValue()));
//                        } else {
//                            restaurants.retainAll(raringTree.searchForNodes(Parser.totalQuery.get(i).getSign(), Integer.parseInt(Parser.totalQuery.get(i).getValue())));
//                        }
//                    }
//
//                }
//            }
//            System.out.println("Hi " + Parser.totalQuery.size());
//            System.out.println("R " + restaurants.size());
//            et.setText("");


            /**
             Set<Restaurant> restaurants = null;
             // if a query on price
             restaurants = priceTree.searchForNodes("<", 100);
             // if a query on rating
             if (restaurants == null) {
             restaurants = raringTree.searchForNodes(">", 3);
             } else {
             restaurants.retainAll(raringTree.searchForNodes(">", 3));
             }
             // if a query on deliver (just an example)
             Iterator<Restaurant> iterator = restaurants.iterator();
             while (iterator.hasNext()) {
             Restaurant r = iterator.next();
             if (!r.isDeliveryService()) {
             iterator.remove();
             }
             }
             **/

            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("Restaurants", new Gson().toJson(restaurants));
            startActivity(intent);
        }
    };

}
