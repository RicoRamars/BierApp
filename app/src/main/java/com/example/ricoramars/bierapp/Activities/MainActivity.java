package com.example.ricoramars.bierapp.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ricoramars.bierapp.Entities.Beer;
import com.example.ricoramars.bierapp.Adapter.BeerAdapter;
import com.example.ricoramars.bierapp.Viewmodel.BeerViewModel;
import com.example.ricoramars.bierapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_BEER_REQUEST = 1;
    public static final int EDIT_BEER_REQUEST = 2;
    public static final int BEER_DICE_REQUEST = 3;

    private BeerViewModel beerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton buttonAddBeer = findViewById(R.id.button_add_beer);
         buttonAddBeer.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this, AddEditBeerActivity.class);
                 startActivityForResult(intent, ADD_BEER_REQUEST);
             }
         });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final BeerAdapter adapter = new BeerAdapter();
        recyclerView.setAdapter(adapter);

        beerViewModel = ViewModelProviders.of(this).get(BeerViewModel.class);
        beerViewModel.getAllBeers().observe(this, new Observer<List<Beer>>() {
            @Override
            public void onChanged(@Nullable List<Beer> beers) {
                //update the RecyclerView
                adapter.submitList(beers);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                beerViewModel.delete(adapter.getBeerAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Beer deleted from the list", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        //Set the onItemClickListener for the update activity
        adapter.setOnItemClickListener(new BeerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Beer beer) {
                Intent intent = new Intent(MainActivity.this, AddEditBeerActivity.class);
                intent.putExtra(AddEditBeerActivity.EXTRA_ID, beer.getId());
                intent.putExtra(AddEditBeerActivity.EXTRA_NAME, beer.getBeerName());
                intent.putExtra(AddEditBeerActivity.EXTRA_TYPE, beer.getBeerType());
                intent.putExtra(AddEditBeerActivity.EXTRA_DESCRIPTION, beer.getDescription());
                intent.putExtra(AddEditBeerActivity.EXTRA_RATING, beer.getRating());
                startActivityForResult(intent, EDIT_BEER_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BEER_REQUEST && resultCode == RESULT_OK) {
            String beerName = data.getStringExtra(AddEditBeerActivity.EXTRA_NAME);
            String beerType = data.getStringExtra(AddEditBeerActivity.EXTRA_TYPE);
            String description = data.getStringExtra(AddEditBeerActivity.EXTRA_DESCRIPTION);
            int rating = data.getIntExtra(AddEditBeerActivity.EXTRA_RATING, 1);

            Beer beer = new Beer(beerName, beerType, description, rating);
            beerViewModel.insert(beer);

            Toast.makeText(this, "Beer saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_BEER_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditBeerActivity.EXTRA_ID, -1);

            if (id == -1 ) {
                Toast.makeText(this, "Beer can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String beerName = data.getStringExtra(AddEditBeerActivity.EXTRA_NAME);
            String beerType = data.getStringExtra(AddEditBeerActivity.EXTRA_TYPE);
            String description = data.getStringExtra(AddEditBeerActivity.EXTRA_DESCRIPTION);
            int rating = data.getIntExtra(AddEditBeerActivity.EXTRA_RATING, 1);

            Beer beer = new Beer(beerName, beerType, description, rating);
            beer.setId(id);

            Toast.makeText(this, "Beer updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Beer not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_beers:
                    beerViewModel.deleteAllBeers()
                ;
                Toast.makeText(this, "list of beers cleared", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navigate_to_beer_game:
                Intent intent = new Intent(MainActivity.this, BeerDiceGame.class);
                startActivityForResult(intent, BEER_DICE_REQUEST);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
