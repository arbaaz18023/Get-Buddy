package com.example.myapplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EventList {

    private static EventList newslist=null;
    private Set<String> hash_Set = new HashSet<String>();
    private int index;
    private ArrayList<EventData> NewsArrayList;

    public boolean check_if_exist(String id){
        if(hash_Set.contains(id)) return  true;
        return false;
    }

    public void Add_to_set(String id){
        hash_Set.add(id);
    }

    public void Delete_from_set(String id){
        hash_Set.remove(id);
    }

    private EventList(){
        NewsArrayList = new ArrayList<>();
    }

    public void add_data(EventData nd){
        NewsArrayList.add(nd);
    }

    public EventData get_data(int pos){
        return NewsArrayList.get(pos);
    }

    public int get_list_size(){
        return NewsArrayList.size();
    }

    public ArrayList<EventData> get_list(){
        return NewsArrayList;
    }


    public static EventList getInstance(){
        if(newslist==null){
            newslist = new EventList();
            newslist.index = 0;
        }

        return newslist;
    }


}


