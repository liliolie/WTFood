package com.example.wtfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wtfood.fileprocess.FileProcess;
import com.example.wtfood.model.Restaurant;
import com.example.wtfood.parser.MyTokenizer;
import com.example.wtfood.parser.Parser;
import com.example.wtfood.rbtree.RBTree;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ResultActivity extends AppCompatActivity {
    ListView result;
    List<Restaurant> restaurants;


    private RBTree priceTree;
    private RBTree raringTree;

    private ArrayAdapter aa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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

        // Set listener to button for search again.
        ImageButton search = (ImageButton) findViewById(R.id.research_search_button);
        search.setOnClickListener(l1);


        result = (ListView) findViewById(R.id.result_lv);
        String bookJson = getIntent().getStringExtra("Restaurants");
        Set<Restaurant> r = new Gson().fromJson(bookJson, new TypeToken<Set<Restaurant>>() {
        }.getType());
        restaurants = new ArrayList<>(r);
        restaurants.sort(Comparator.comparing(Restaurant::getDistance));
        Collections.reverse(restaurants);

        aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, restaurants);
        result.setAdapter(aa);


        //set the listener to the listView items [Lili]
        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("Restaurant", new Gson().toJson(restaurants.get(i)));
                startActivity(intent);
            }
        });


    }


    private View.OnClickListener l1 = new View.OnClickListener() {
        public void onClick(View v) {

            EditText et = (EditText) findViewById(R.id.ResultQuery);
            String query = et.getText().toString();
            // Remove white space.
            query = query.replaceAll("\\s+", "");
            et.setText("");
            Set<Restaurant> restaurantsSet = null;

            // If input isn't empty or space.
            if (!query.equals("")) {
                MyTokenizer queryTokenizer = new MyTokenizer(query);
                Parser p = new Parser(queryTokenizer);
                p.parseAttribute();

                // Count is checking whether received wrong queries or not.
                int count = 0;
                for (int i = 0; i < p.totalQuery.size(); i++) {
                    // If it's not valid. Toast and show instruction information.
                    if (p.totalQuery.get(i).getCompareAttribute().equals("*") || p.totalQuery.get(i).getSign().equals("*") || p.totalQuery.get(i).getValue().equals("*")) {
                        Toast.makeText(getApplicationContext(), "Wrong type query!! \nThe instruction is at the top right concern. \nGo & Check it out!!", Toast.LENGTH_SHORT).show();
                        count++;
                        continue;
                    } else {

                        // If it's valid. Search in the relative tree and add it to restaurants set.
                        if (p.totalQuery.get(i).getCompareAttribute().equals("price")) {
                            if (restaurantsSet == null) {
                                restaurantsSet = priceTree.searchForNodes(p.totalQuery.get(i).getSign(), Integer.parseInt(p.totalQuery.get(i).getValue()));
                            } else {
                                restaurantsSet.retainAll(priceTree.searchForNodes(p.totalQuery.get(i).getSign(), Integer.parseInt(p.totalQuery.get(i).getValue())));
                            }
                        }

                        if (p.totalQuery.get(i).getCompareAttribute().equals("rating")) {
                            if (restaurantsSet == null) {
                                restaurantsSet = raringTree.searchForNodes(p.totalQuery.get(i).getSign(), Integer.parseInt(p.totalQuery.get(i).getValue()));
                            } else {
                                restaurantsSet.retainAll(raringTree.searchForNodes(p.totalQuery.get(i).getSign(), Integer.parseInt(p.totalQuery.get(i).getValue())));
                            }
                        }

                        if (p.totalQuery.get(i).getCompareAttribute().equals("delivery")) {
                            boolean delivery = p.totalQuery.get(i).getValue().equals("y");
                            if (restaurantsSet == null) {
                                restaurantsSet = raringTree.getAllNodes();
                            }
                            Iterator<Restaurant> iterator = restaurantsSet.iterator();

                            while (iterator.hasNext()) {
                                if (iterator.next().isDeliveryService() != delivery) {
                                    iterator.remove();
                                }
                            }

                        }
                    }

                }

                // Count = 0 means that there's no wrong query.
                if (restaurantsSet != null) {
                    // Make the list empty.
                    if (count != 0) {
                        Toast.makeText(getApplicationContext(), "Some part of the query are invalid!! \nCheck out our query instruction at the top right corner.", Toast.LENGTH_LONG).show();
                    }
                    restaurants.clear();
                    // Add new restaurant which satisfied the requirement from set to the list.
                    for (Restaurant r : restaurantsSet) {
                        restaurants.add(r);
                    }
                    restaurants.sort(Comparator.comparing(Restaurant::getDistance));
                    Collections.reverse(restaurants);

                    // Notify the data have changed.
                    aa.notifyDataSetChanged();
                    result.setAdapter(aa);
                    et.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong type query!! \nThe instruction is at the top right concern. \nGo & Check it out!!", Toast.LENGTH_SHORT).show();
                }
            }
            // Toast if input is empty.
            else {
                Toast.makeText(getApplicationContext(), "Enter something!! \nWe want to know what are you looking for!\nTop right have our query instruction!", Toast.LENGTH_SHORT).show();
            }
        }
    };

}