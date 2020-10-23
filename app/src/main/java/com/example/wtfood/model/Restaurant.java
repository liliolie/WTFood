package com.example.wtfood.model;

import java.io.Serializable;
import java.util.Random;

/**
 * model for a restaurant
 * Junliang Liu
 */
public class Restaurant implements Serializable {
    private final String[] preDefinedStreets = {
            "Ainslie Avenue",
            "Ainslie Place",
            "Akuna Street",
            "Alinga Street",
            "Allara Street",
            "Allsop Street",
            "Baldessin Crescent",
            "Ballumbir Street",
            "Barry Drive",
            "Bible Lane",
            "Binara Street",
            "Bunda Street",
            "Childers Street",
            "Canberra Walk",
            "Commonwealth Avenue",
            "Constitution Avenue",
            "Cooyong Street",
            "Cooyong Street",
            "Coranderrk Street",
            "Darwin Place",
            "East Row",
            "Edinburgh Avenue",
            "Ellery Crescent",
            "Farrell Place",
            "Garema Place",
            "Genge Street",
            "Gordon Street",
            "Hales Street",
            "Hillside Lane",
            "Hobart Place",
            "Hutton Street",
            "Kendall Lane",
            "Kingsley Street",
            "Knowles Place",
            "Latin American Plaza",
            "London Circuit",
            "Marcus Clarke Street",
            "Mccoy Circuit",
            "Moore Street",
            "Mort Street",
            "Mulwala Lane",
            "Murden Street",
            "Murulla Lane",
            "Nangari Street",
            "Narellan Place",
            "Narellan Street",
            "Northbourne Avenue",
            "Odgers Lane",
            "Parkes Way",
            "Petrie Plaza",
            "Petrie Street",
            "Phillip Law Street",
            "Rabaul Lane",
            "Repertory Lane",
            "Rimmer Street",
            "Riverside Lane",
            "Rudd Street",
            "Scotts Crossing",
            "The Mews",
            "Theatre Lane",
            "Tocumwal Lane",
            "University Avenue",
            "Verity Lane",
            "Vernon Circle",
            "West Row",
            "Wilden Street",
            "William Clemens Street",
            "William Herbert Place"
    };

    private final String[] Suburbs = {
            "Bruce","Braddon","Tuggeranong","Comma",
            "Gungahlin","Coree","Woden","Barton",
            "Yarralumla","Majura","Cook","Latham",
            "Kaleen","Holt","Page","Melba","Evatt",
            "Spence","Scullin","Ainslie","Campell",
            "Downer","Reid"
    };

    private int rating;
    private String name;
    private boolean deliveryService;
    private Location location;
    private Type type;
    private int price;
    private String address;
    private String phone;
    private double distance = 0.0;
    
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

    /**
     * a constructor to construct a random Restaurant with given name
     * @param name the name of a random Restaurant
     */
    public Restaurant(String name) {
        Random random = new Random();
        this.rating = random.nextInt(5) + 1;
        this.name = name;
        this.deliveryService = random.nextBoolean();
        this.location = new Location(random.nextDouble() * (-35.134039 + 35.439450) + (-35.439450), random.nextDouble() * (149.252717 - 148.951315) + 148.951315);
        int type = random.nextInt(Type.values().length);
        this.type = Type.values()[type];
        this.price = random.nextInt(291) + 10;
        this.phone = "04";
        for (int i = 0; i < 8; i++) {
            this.phone += String.valueOf(random.nextInt(10));
        }
        this.address = String.valueOf(random.nextInt(999) + 1);
        this.address += "/" + random.nextInt(11) + " " + preDefinedStreets[random.nextInt(preDefinedStreets.length)] + " " + Suburbs[random.nextInt(Suburbs.length)] + " ACT";
    }

    /**
     * getters and setters for Restaurant
     */

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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * the Object is equals to this Restaurant
     * @param obj another Object
     * @return true if the class of Object is Restaurant and their name, location and address are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (getClass() == obj.getClass()) {
            Restaurant restaurant = (Restaurant) obj;
            return restaurant.name.equals(this.name) && restaurant.location.equals(this.location)
                    && restaurant.address.equals(this.address);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode() & address.hashCode() + (int) (location.getLatitude() + location.getLongitude());
    }

    @Override
    public String toString() {
        String delivery;
        String type = "";
        if (isDeliveryService()) {
            delivery = "Deliverable.";
        } else {
            delivery = "Unable to deliver.";
        }

        if (getType().equals(Type.chineseFood)) {
            type = "Chinese Food!";
        }
        if (getType().equals(Type.japaneseFood)) {
            type = "Japanese Food!";
        }
        if (getType().equals(Type.indianFood)) {
            type = "Indian Food!";
        }
        if (getType().equals(Type.malaysianFood)) {
            type = "Malaysian Food!";
        }
        if (getType().equals(Type.thaiFood)) {
            type = "Thai Food!";
        }
        if (getType().equals(Type.italianFood)) {
            type = "Italian Food!";
        }
        if (getType().equals(Type.frenchFood)) {
            type = "French Food!";
        }
        if (getType().equals(Type.americanFood)) {
            type = "American Food!";
        }
        if (getType().equals(Type.koreanFood)) {
            type = "Korean Food!";
        }
        if (getType().equals(Type.mexicanFood)) {
            type = "Mexican Food!";
        }
        if (getType().equals(Type.turkishFood)) {
            type = "Turkish Food!";
        }

        String distance = "";
        if (this.distance != 0.0) {
            distance += (int) this.distance + " m";
        }

        return name + " \n " + address + " \n " + phone + " \n " + "Rating: " + rating + " \n " + "Price: " +
                price + " AUD/person" + " \n " + delivery + " \n " + "Food Type: " + type + "\n " + distance;
    }

}
