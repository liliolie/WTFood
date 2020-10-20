package com.example.wtfood.fileprocess;

import com.example.wtfood.model.Location;
import com.example.wtfood.model.Restaurant;
import com.example.wtfood.model.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileProcess {

    public List<Restaurant> jsonFileRead(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder json = new StringBuilder();
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            json.append(str);
        }
        bufferedReader.close();
        Gson gson = new Gson();
        List<Restaurant> restaurants = gson.fromJson(json.toString(), new TypeToken<List<Restaurant>>() {}.getType());
        return restaurants;
    }

    public List<Restaurant> csvFileRead(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str;

        List<Restaurant> restaurants = new ArrayList<>();

        while ((str = bufferedReader.readLine()) != null) {
            String name = str.split("\"")[1];
            String[] strs = str.split("\"")[2].split(",");

            if (strs.length != 9) {
                continue;
            }

            String address = strs[1];
            String phone = strs[2];
            double latitude = Double.parseDouble(strs[3]);
            double longitude = Double.parseDouble(strs[4]);
            int price = Integer.parseInt(strs[5]);
            int rating = Integer.parseInt(strs[6]);
            Type type = Type.valueOf(strs[7]);
            boolean deliveryService = Boolean.parseBoolean(strs[8]);
            Restaurant restaurant = new Restaurant(rating, name, deliveryService, new Location(latitude, longitude), type, price, address, phone);
            restaurants.add(restaurant);
        }

        return restaurants;
    }

    // the create method below are just showing, not creating files here
    public void JSONFileCreate() throws IOException {

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        int totalNumber = 500;

        File csv = new File("assets/Food_Establishment_Inspection_Scores.csv");
        File json = new File("assets/list.json");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(csv));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(json));

        // method1: for clearer file
//        bufferedWriter.write("{\"restaurants\":[\n");

        bufferedReader.readLine();

        for (int i = 0; i < totalNumber; i++) {
            String str = bufferedReader.readLine();

            String name;
            if (str.charAt(0) == '\"') {
                name = str.split("\"")[1].split(" #")[0];
            } else {
                name = str.split(",")[0].split(" #")[0];
            }

            String[] arr;
            do {
                arr = bufferedReader.readLine().split(",");
            } while (!arr[arr.length - 1].equals("Routine Inspection"));

            // method2: for clearer coding
            Restaurant restaurant = new Restaurant(name);
            restaurants.add(restaurant);


            // method1: for clearer file
//            JSONObject jsonObject = JSONObject.fromObject(restaurant);

//            bufferedWriter.write(jsonObject.toString());
//
//            if (i == totalNumber - 1) {
//                bufferedWriter.write("\n");
//            } else {
//                bufferedWriter.write(",\n");
//            }

        }

        // method1: for clearer file
//        bufferedWriter.write("]}");

        // method2: for clearer coding
        JSONArray jsonArray = JSONArray.fromObject(restaurants);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("restaurants", jsonArray);

        bufferedWriter.write(jsonArray.toString());

        bufferedWriter.close();
        bufferedReader.close();
    }

    public void csvFileCreate() throws IOException {

        int totalNumber = 1000;

        File csv = new File("assets/Food_Establishment_Inspection_Scores.csv");
        File result = new File("assets/list.csv");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(csv));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(result));

        bufferedReader.readLine();

        for (int i = 0; i < totalNumber; i++) {
            if (i < 500) {
                continue;
            }

            String str = bufferedReader.readLine();

            String name;
            if (str.charAt(0) == '\"') {
                name = str.split("\"")[1].split(" #")[0];
            } else {
                name = str.split(",")[0].split(" #")[0];
            }

            String[] arr;
            do {
                arr = bufferedReader.readLine().split(",");
            } while (!arr[arr.length - 1].equals("Routine Inspection"));

            // method2: for clearer coding
            Restaurant restaurant = new Restaurant(name);

            bufferedWriter.write("\"" + restaurant.getName() + "\"" + "," + restaurant.getAddress() + "," + restaurant.getPhone()
                    + "," + restaurant.getLocation().toString() + "," + restaurant.getPrice() + "," + restaurant.getRating()
                    + "," + restaurant.getType() + "\n"
            );
        }

        bufferedReader.close();
        bufferedWriter.close();
    }


}
