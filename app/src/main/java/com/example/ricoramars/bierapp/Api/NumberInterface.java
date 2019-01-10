package com.example.ricoramars.bierapp.Api;

import com.example.ricoramars.bierapp.Entities.NumberFactItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NumberInterface {

    @GET("/{number}?json")
    Call<NumberFactItem> getRandomNumber(@Path("number" ) int number);
}
