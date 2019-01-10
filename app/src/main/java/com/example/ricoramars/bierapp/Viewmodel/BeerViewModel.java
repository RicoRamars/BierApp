package com.example.ricoramars.bierapp.Viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ricoramars.bierapp.Entities.Beer;
import com.example.ricoramars.bierapp.Repositories.BeerRepository;

import java.util.List;

public class BeerViewModel extends AndroidViewModel {
    private BeerRepository repository;
    private LiveData<List<Beer>> allBeers;

    public BeerViewModel(@NonNull Application application) {
        super(application);
        repository = new BeerRepository(application);
        allBeers = repository.getAllBeers();
    }

    public void insert(Beer beer){
        repository.insert(beer);
    }

    public void update(Beer beer){
        repository.update(beer);
    }

    public void delete(Beer beer){
        repository.delete(beer);
    }

    public void deleteAllBeers(){
        repository.deleteAllBeers();
    }

    public LiveData<List<Beer>> getAllBeers() {
        return allBeers;
    }
}
