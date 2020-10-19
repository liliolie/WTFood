package com.example.wtfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wtfood.fileprocess.FileProcess;
import com.example.wtfood.fileprocess.Restaurant;

import com.example.wtfood.parser.MyTokenizer;
import com.example.wtfood.parser.Parser;
import com.example.wtfood.rbtree.RBTree;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RBTree priceTree;
    private RBTree raringTree;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    //Override the Back button of Android system, making pressing the Back button close the
    // navigation drawer instead of quiting the application
    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Make the items inside the navigation drawer clickable.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                break;
            case R.id.nav_logout:
                Toast.makeText(getApplicationContext(), "Logged out.", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        //Hide or show items


        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton menu = findViewById(R.id.menuButton);
        menu.setOnClickListener(v -> {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.openDrawer(GravityCompat.START);
            } else {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

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
                        Toast.makeText(getApplicationContext(),"Invalid query! \nTCheck out our query instruction at the top right corner.", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(),"Invalid query! \nTCheck out our query instruction at the top right corner.", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(),"Empty query! \nCheck out our query instruction at the top right corner.", Toast.LENGTH_LONG).show();
            }
        }
    };
}
