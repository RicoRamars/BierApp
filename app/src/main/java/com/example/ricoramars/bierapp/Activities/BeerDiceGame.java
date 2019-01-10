package com.example.ricoramars.bierapp.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricoramars.bierapp.Api.NumberApiService;
import com.example.ricoramars.bierapp.Api.NumberInterface;
import com.example.ricoramars.bierapp.Entities.NumberFactItem;
import com.example.ricoramars.bierapp.R;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeerDiceGame extends AppCompatActivity {
    //Local variables
    private ImageView   imageViewDice;
    private TextView    factView;
    private int         number, numberNew;
    private Random      random;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_dice_game);

        factView = findViewById(R.id.fact_data_text);
        imageViewDice = findViewById(R.id.imageViewDice);
        random = new Random();
        number = 1;

        fab = findViewById(R.id.fab_higher);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessHigher();
            }
        });

        fab = findViewById(R.id.fab_lower);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessLower();
            }
        });
    }

    private void guessHigher() {
        numberNew = random.nextInt(6)+1;
        if (numberNew == number) {
            guessHigher();
        } else if (numberNew > number) {
            Toast.makeText(BeerDiceGame.this, "Give a way one sip", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BeerDiceGame.this, "Take one sip!", Toast.LENGTH_SHORT).show();
        }
        number = numberNew;
        rollDice();
    }

    private void guessLower() {
        numberNew = random.nextInt(6)+1;
        if (numberNew == number) {
            guessHigher();
        } else if (numberNew < number) {
            Toast.makeText(BeerDiceGame.this, "Give a way one sip", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BeerDiceGame.this, "Take one sip!", Toast.LENGTH_SHORT).show();
        }
        number = numberNew;
        rollDice();
    }

    private void rollDice() {

        switch (numberNew) {
            case 1:
                imageViewDice.setImageResource(R.drawable.d1);
                break;
            case 2:
                imageViewDice.setImageResource(R.drawable.d2);
                break;
            case 3:
                imageViewDice.setImageResource(R.drawable.d3);
                break;
            case 4:
                imageViewDice.setImageResource(R.drawable.d4);
                break;
            case 5:
                imageViewDice.setImageResource(R.drawable.d5);
                break;
            case 6:
                imageViewDice.setImageResource(R.drawable.d6);
                break;
        }
        requestData();
    }
    public void setFactTextView(String quoteMessage) {

        factView.setText(quoteMessage);

    }

    private void requestData(){
        NumberInterface service = NumberApiService.getClient().create(NumberInterface.class);
        Call<NumberFactItem> call = service.getRandomNumber(numberNew);
        call.enqueue(new Callback<NumberFactItem>() {
            @Override
            public void onResponse(Call<NumberFactItem> call, Response<NumberFactItem> response) {
                NumberFactItem numberFactItem = response.body();
                setFactTextView(numberFactItem.getText());
            }
            @Override
            public void onFailure(Call<NumberFactItem> call, Throwable t) {
                Log.e("requestData Failed",t.toString());
            }
        });
    }
}
