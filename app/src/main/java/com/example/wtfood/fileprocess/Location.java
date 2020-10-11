package com.example.wtfood.fileprocess;


public class Location {
    private double latitude;
    private double longitude;

    Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Location.class) {
            Location location = (Location) obj;
            return this.latitude == location.latitude && this.longitude == location.longitude;
        }

        return false;
    }
}
