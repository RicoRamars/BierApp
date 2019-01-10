package com.example.ricoramars.bierapp.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.ricoramars.bierapp.Entities.Beer;
import com.example.ricoramars.bierapp.Dao.BeerDao;


@Database(entities = Beer.class, version =  1)
public abstract class BeerDatabase extends RoomDatabase {

    private static BeerDatabase instance;

    public abstract BeerDao beerDao();

    public static synchronized BeerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BeerDatabase.class, "beer_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    //Populating the roomdatabase at oncreate
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BeerDao beerDao;
        private PopulateDbAsyncTask(BeerDatabase db) {
            beerDao = db.beerDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            beerDao.insert(new Beer("beerName 1", "beerType 1","description 1", 5));
            beerDao.insert(new Beer("beerName 2", "beerType 2","description 2", 2));
            beerDao.insert(new Beer("beerName 3", "beerType 3","description 3", 8));

            return null;
        }
    }
}
