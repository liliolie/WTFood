package com.example.wtfood.fileprocess;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileProcess {

    public void fileRead() throws IOException {

        File json = new File("assets/list.json");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(json));

        String str;
        String string = "";

        while ((str = bufferedReader.readLine()) != null) {
            string += str;
        }

        bufferedReader.close();

        JSONObject jsonObject = JSONObject.fromObject(string);
        System.out.println(jsonObject);

        JSONArray jsonArray = jsonObject.getJSONArray("restaurants");

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            Boolean deliveryService = jsonObject1.getBoolean("deliveryService");
            String address = jsonObject1.getString("address");
            String phone = jsonObject1.getString("phone");
            int price = jsonObject1.getInt("price");
            System.out.println(jsonObject1);
        }
    }

    public void fileCreate() throws IOException {

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        int totalNumber = 1000;

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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("restaurants", jsonArray);

        bufferedWriter.write(jsonObject.toString());

        bufferedWriter.close();
        bufferedReader.close();
    }
}