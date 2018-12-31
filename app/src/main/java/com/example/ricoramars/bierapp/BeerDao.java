package com.example.ricoramars.bierapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BeerDao {

    @Insert
    void insert (Beer beer);

    @Update
    void update (Beer beer);

    @Delete
    void delete (Beer beer);

    @Query("DELETE FROM beer_table")
    void deleteAllBeers();

    @Query("SELECT * FROM beer_table ORDER BY rating DESC")
    LiveData<List<Beer>> getAllBeers();
}
