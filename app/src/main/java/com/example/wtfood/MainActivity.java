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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.wtfood.fileprocess.FileProcess;
import com.example.wtfood.model.Restaurant;
import com.example.wtfood.parser.MyTokenizer;
import com.example.wtfood.parser.Parser;
import com.example.wtfood.rbtree.RBTree;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RBTree priceTree;
    private RBTree ratingTree;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        fAuth = FirebaseAuth.getInstance();
        updateUI(fAuth.getCurrentUser());

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(v -> {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);
            } else {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        priceTree = new RBTree("price");
        ratingTree = new RBTree("rating");

        try {
            List<Restaurant> restaurants = new FileProcess().jsonFileRead(getAssets().open("list.json"));
            restaurants.addAll(new FileProcess().csvFileRead(getAssets().open("list.csv")));
            for (Restaurant r : restaurants) {
                priceTree.insert(r);
                ratingTree.insert(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageButton go = (ImageButton) findViewById(R.id.goButton);
        go.setOnClickListener(search);

    }

    public void updateUI(FirebaseUser user) {
        Menu drawerMenu = navigationView.getMenu();
        if (user != null) {
            drawerMenu.findItem(R.id.nav_login).setVisible(false);
            drawerMenu.findItem(R.id.nav_profile).setVisible(true);
            drawerMenu.findItem(R.id.nav_logout).setVisible(true);
        } else {
            drawerMenu.findItem(R.id.nav_login).setVisible(true);
            drawerMenu.findItem(R.id.nav_profile).setVisible(false);
            drawerMenu.findItem(R.id.nav_logout).setVisible(false);
        }
    }

    //Override the Back button of Android system, making pressing the Back button close the
    // navigation drawer instead of quiting the application
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Make the items inside the navigation drawer clickable.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Logged out.", Toast.LENGTH_SHORT).show();
                Intent newIntent = getIntent();
                finish();
                startActivity(newIntent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logoButton(View view) {
        Intent intent = new Intent(this, InfoPage.class);
        startActivity(intent);
    }

    public void tutorialButton(View view) {
        Intent intent = new Intent(this, TutorialPage.class);
        startActivity(intent);
    }

//    public void locationUpdate(View view) {
//        Intent intent = new Intent(this, LocationActivity.class);
//        startActivity(intent);
//    }

    /**
     * The on click listener for go button on the main menu.
     * If the query are correct, it will pass the data to the new intent(Result Activity).
     * If the query are incorrect, it will show the error messages.
     */
    private View.OnClickListener search = new View.OnClickListener() {
        public void onClick(View v) {

            EditText et = (EditText) findViewById(R.id.query);
            String query = et.getText().toString();
            // Remove white space.
            query = query.replaceAll("\\s+", "");
            et.setText("");
            Set<Restaurant> restaurants = null;

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
                        count++;
                        continue;
                    }
                    // If it's valid. Search in the relative tree and add it to restaurants set.
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
                                restaurants = ratingTree.searchForNodes(p.totalQuery.get(i).getSign(), Integer.parseInt(p.totalQuery.get(i).getValue()));
                            } else {
                                restaurants.retainAll(ratingTree.searchForNodes(p.totalQuery.get(i).getSign(), Integer.parseInt(p.totalQuery.get(i).getValue())));
                            }
                        }

                        if (p.totalQuery.get(i).getCompareAttribute().equals("delivery")) {
                            boolean delivery = p.totalQuery.get(i).getValue().equals("y");
                            if (restaurants == null) {
                                restaurants = ratingTree.getAllNodes();
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
                // Count = 0 means that there's no wrong query.

                if (restaurants != null) {
                    // Passing restaurant data to the new intent.
                    if (count != 0) {
                        Toast.makeText(getApplicationContext(), "Some part of the query are invalid!! \nCheck out our query instruction at the top right corner.", Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("Restaurants", new Gson().toJson(restaurants));
                    startActivity(intent);
                    et.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid query! \nCheck out our query instruction at the top right corner.", Toast.LENGTH_LONG).show();
                }
            }
            // Toast if input is empty.
            else {
                Toast.makeText(getApplicationContext(), "Empty query! \nCheck out our query instruction at the top right corner.", Toast.LENGTH_LONG).show();
            }
        }
    };
}
