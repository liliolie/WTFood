package com.example.wtfood.model;


public class Location {
    private double latitude;
    private double longitude;

    /**
     * constructor of Location
     * @param latitude the latitude of Location
     * @param longitude the longitude of Location
     */
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * get the latitude of the Location
     * @return the latitude of the Location
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * get the longitude of the Location
     * @return the longitude of the Location
     */
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
