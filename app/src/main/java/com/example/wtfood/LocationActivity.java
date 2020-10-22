package com.example.wtfood;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.wtfood.model.Restaurant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Set;


public class LocationActivity extends AppCompatActivity {


    LocationManager locationManager;
    LocationListener locationListener;
    TextView latText;
    TextView lonText;
    TextView distanceText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_s);


        String bookJson = getIntent().getStringExtra("Restaurants");
        Set<Restaurant> restaurant = new Gson().fromJson(bookJson, new TypeToken<Set<Restaurant>>() {
        }.getType());

        latText = (TextView) findViewById(R.id.locationText);
        lonText = (TextView) findViewById(R.id.latText);
        distanceText = (TextView) findViewById(R.id.distanceT);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        locationListener = new LocationListener() {


            //detect the change of the location
            //set the text to the current location
            //show the distance from current location
            @Override
            public void onLocationChanged(Location location) {
                latText.setText(location.getLatitude() + "");
                lonText.setText(location.getLongitude() + "");
                distanceText.setText(getDistance(location.getLatitude(), location.getLongitude(), 100, 200) + "");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            //if the location services are lost, it will bring up the setting panel
            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        // set up the permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
        }
    }

    public void getLocation(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);
    }

    //calculate distance between two locations
    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        try {
            Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results[0];
    }


}