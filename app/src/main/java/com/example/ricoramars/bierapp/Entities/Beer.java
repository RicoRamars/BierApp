package com.example.ricoramars.bierapp.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "beer_table")
public class Beer {

    @PrimaryKey (autoGenerate = true)
    private int id;

    private String beerName;
    private String beerType;
    private String description;
    private int rating;

    public Beer(String beerName, String beerType, String description, int rating) {
        this.beerName = beerName;
        this.beerType = beerType;
        this.description = description;
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getBeerName() {
        return beerName;
    }

    public String getBeerType() {
        return beerType;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }
}
