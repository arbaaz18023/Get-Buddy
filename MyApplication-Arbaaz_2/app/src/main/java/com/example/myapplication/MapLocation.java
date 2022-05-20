package com.example.myapplication;

public class MapLocation {

    private static MapLocation userObj = null;
    private double lat;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    private double lng;

    private MapLocation(){

    }

    public static MapLocation getInstance(){
        if(userObj==null){
            userObj = new MapLocation();
        }
        return userObj;
    }


}

