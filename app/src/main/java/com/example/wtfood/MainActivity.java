package com.example.wtfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
            restaurants.addAll(new FileProcess().csvFileRead(getAssets().open("list.csv")));
            for (Restaurant r : restaurants) {
                priceTree.insert(r);
                raringTree.insert(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Here can add our data in the array list.
//        restaurants = new HashSet<>();
//        restaurants.add(new Restaurant("Hero burger"));
//        restaurants.add(new Restaurant("KFC"));
//        restaurants.add(new Restaurant("Subway"));
//        restaurants.add(new Restaurant("Tacobell"));
//        restaurants.add(new Restaurant("BurgerKing"));
//        restaurants.add(new Restaurant("Raku"));

        ImageButton go = (ImageButton) findViewById(R.id.goButton);
        go.setOnClickListener(l1);
    }

    public void locationButton(View v){
        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
        startActivity(intent);
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
            query = query.replaceAll("\\s+","");
            et.setText("");
            Set<Restaurant> restaurants = null;
            if (!query.equals("")) {
            MyTokenizer queryTokenizer = new MyTokenizer(query);
            Parser p = new Parser(queryTokenizer);
            p.parseAttribute();
            int count = 0;
                for (int i = 0; i < p.totalQuery.size(); i++) {
                    System.out.println("Hi");
                    if(p.totalQuery.get(i).getCompareAttribute().equals("*") || p.totalQuery.get(i).getSign().equals("*") || p.totalQuery.get(i).getValue().equals("*")){
                        Toast.makeText(getApplicationContext(),"Wrong type query!! \nThe instruction is at the top right concern. \nGo & Check it out!!", Toast.LENGTH_SHORT).show();
                        count++;
                        break;
                    }
                    else {
                        if (p.totalQuery.get(i).getCompareAttribute().equals("price")) {
                            if (restaurants == null) {
                                restaurants = priceTree.searchForNodes(p.totalQuery.get(i).getSign(), Integer.parseInt(p.totalQuery.get(i).getValue()));
                            } else {
                                restaurants.retainAll(priceTree.searchForNodes(p.totalQuery.get(i).getSign(), Integer.parseInt(p.totalQuery.get(i).getValue())));
                            }
                        }

                        if (p.totalQuery.get(i).getCompareAttribute().equals("rating")) {
                            if (restaurants == null) {
                                restaurants = raringTree.searchForNodes(p.totalQuery.get(i).getSign(), Integer.parseInt(p.totalQuery.get(i).getValue()));
                            } else {
                                restaurants.retainAll(raringTree.searchForNodes(p.totalQuery.get(i).getSign(), Integer.parseInt(p.totalQuery.get(i).getValue())));
                            }
                        }

                        if (p.totalQuery.get(i).getCompareAttribute().equals("delivery")) {
                            boolean delivery = p.totalQuery.get(i).getValue().equals("Y");
                            if (restaurants == null) {
                                restaurants = raringTree.getAllNodes();
                            }
                            Iterator<Restaurant> iterator = restaurants.iterator();

                            while (iterator.hasNext()) {
                                if (iterator.next().isDeliveryService() != delivery) {
                                    iterator.remove();
                                }
                            }

                        }
                    }

                }

                if(count == 0){
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("Restaurants", new Gson().toJson(restaurants));
                startActivity(intent);
                et.setText("");}
                else {
                    Toast.makeText(getApplicationContext(),"Wrong type query!! \nThe instruction is at the top right concern. \nGo & Check it out!!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(),"Enter something!! \nWe want to know what are you looking for!\nTop right have our query instruction!", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
