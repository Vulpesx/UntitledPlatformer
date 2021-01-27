package me.potaytoprograms.api.event;

import java.util.HashMap;

public class Event {

    private String id;
    private HashMap<String, Object> data = new HashMap<>();

    public Event(String id){
        this.id = id;
    }

    public void addData(String key, Object data){
        this.data.put(key, data);
    }

    public HashMap<String, Object> getData(){
        return data;
    }

    public String getId(){
        return id;
    }
}
