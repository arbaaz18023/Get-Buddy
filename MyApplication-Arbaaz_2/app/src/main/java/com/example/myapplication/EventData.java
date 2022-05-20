package com.example.myapplication;

import java.io.Serializable;

public class EventData implements Serializable {
    private String Name;
   private String Location;
   private int Total_slots;
    private String Zone;
    private String event_id;
    private String chat_id;

    public EventData(String name1, String location1, String zone1, int total_slots, String chat_id,String event_id) {
        this.Location =location1;
        this.Name = name1;
        this.Total_slots = total_slots;
        this.Zone = zone1;
        this.event_id = event_id;
        this.chat_id = chat_id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }


    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }



    public String getId() {
        return event_id;
    }

    public void setIndex(String id) {
        this.event_id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getTotal_slots() {
        return Total_slots;
    }

    public void setTotal_slots(int total_slots) {
        Total_slots = total_slots;
    }
}
