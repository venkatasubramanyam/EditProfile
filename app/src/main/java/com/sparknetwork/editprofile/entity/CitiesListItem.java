package com.sparknetwork.editprofile.entity;

import java.io.Serializable;

public class CitiesListItem implements Serializable {

    private String lat;
    private String lon;
    private String city;

    public CitiesListItem() {
    }

    public CitiesListItem(String lat, String lon, String city) {
        this.lat = lat;
        this.lon = lon;
        this.city = city;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
