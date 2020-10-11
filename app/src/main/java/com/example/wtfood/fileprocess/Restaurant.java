package com.example.wtfood.fileprocess;

import java.util.Random;

public class Restaurant {
    private final String[] preDefinedStreets = {
            "A",
            "B"
    };

    private int rating;
    private String name;
    private boolean deliveryService;
    private Location location;
    private Type type;
    private int price;
    private String address;
    private String phone;

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public boolean isDeliveryService() {
        return deliveryService;
    }

    public Location getLocation() {
        return location;
    }

    public Type getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public Restaurant(String name) {
        Random random = new Random();
        this.rating = random.nextInt(5) + 1;
        this.name = name;
        this.deliveryService = random.nextBoolean();
        this.location = new Location(random.nextDouble() * 20 + 25, random.nextDouble() * 20 + 25);
        int type = random.nextInt(Type.values().length);
        this.type = Type.values()[type];
        this.price = random.nextInt(291) + 10;
        this.phone = "04";
        for (int i = 0; i < 8; i++) {
            this.phone += String.valueOf(random.nextInt(10));
        }
        this.address = String.valueOf(random.nextInt(99) + 1);
        this.address += " " + preDefinedStreets[random.nextInt(preDefinedStreets.length)] + " St.";
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeliveryService(boolean deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Restaurant(int rating, String name, boolean deliveryService, Location location, Type type, int price, String address, String phone) {
        this.rating = rating;
        this.name = name;
        this.deliveryService = deliveryService;
        this.location = location;
        this.type = type;
        this.price = price;
        this.address = address;
        this.phone = phone;
    }
}
