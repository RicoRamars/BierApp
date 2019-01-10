package com.example.ricoramars.bierapp.Repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.ricoramars.bierapp.Dao.BeerDao;
import com.example.ricoramars.bierapp.Database.BeerDatabase;
import com.example.ricoramars.bierapp.Entities.Beer;

import java.util.List;

public class BeerRepository {
    private BeerDao beerDao;
    private LiveData<List<Beer>> allBeers;

    public BeerRepository(Application application) {
        BeerDatabase database = BeerDatabase.getInstance(application);
        beerDao = database.beerDao();
        allBeers = beerDao.getAllBeers();
    }

    public void insert (Beer beer){
        new InsertBeerAsyncTask(beerDao).execute(beer);
    }

    public void update (Beer beer){
        new UpdateBeerAsyncTask(beerDao).execute(beer);
    }

    public void delete (Beer beer){
        new DeleteBeerAsyncTask(beerDao).execute(beer);
    }

    public void deleteAllBeers (){
        new DeleteAllBeersAsyncTask(beerDao).execute();
    }

    public LiveData<List<Beer>> getAllBeers() {
        return allBeers;
    }

    private static class InsertBeerAsyncTask extends AsyncTask<Beer, Void, Void> {
        private BeerDao beerDao;

        private InsertBeerAsyncTask(BeerDao beerDao){
            this.beerDao = beerDao;
        }

        @Override
        protected Void doInBackground(Beer... beers) {
            beerDao.insert(beers[0]);
            return null;
        }
    }

    private static class UpdateBeerAsyncTask extends AsyncTask<Beer, Void, Void> {
        private BeerDao beerDao;

        private UpdateBeerAsyncTask (BeerDao beerDao){
            this.beerDao = beerDao;
        }

        @Override
        protected Void doInBackground(Beer... beers) {
            beerDao.update(beers[0]);
            return null;
        }
    }

    private static class DeleteBeerAsyncTask extends AsyncTask<Beer, Void, Void> {
        private BeerDao beerDao;

        private DeleteBeerAsyncTask(BeerDao beerDao){
            this.beerDao = beerDao;
        }

        @Override
        protected Void doInBackground(Beer... beers) {
            beerDao.delete(beers[0]);
            return null;
        }
    }

    private static class DeleteAllBeersAsyncTask extends AsyncTask<Void, Void, Void> {
        private BeerDao beerDao;

        private DeleteAllBeersAsyncTask(BeerDao beerDao){
            this.beerDao = beerDao;
        }

        @Override
        protected Void doInBackground(Void... voids ) {
            beerDao.deleteAllBeers();
            return null;
        }
    }
}
