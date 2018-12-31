package com.example.ricoramars.bierapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditBeerActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.ricoramars.bierapp.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.example.ricoramars.bierapp.EXTRA_NAME";
    public static final String EXTRA_TYPE =
            "com.example.ricoramars.bierapp.EXTRA_TYPE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.ricoramars.bierapp.EXTRA_DESCRIPTION";
    public static final String EXTRA_RATING =
            "com.example.ricoramars.bierapp.EXTRA_RATING";


    private EditText editTextBeerName;
    private EditText editTextBeerType;
    private EditText editTextDescription;
    private NumberPicker numberPickerRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);

        editTextBeerName = findViewById(R.id.edit_text_beername);
        editTextBeerType = findViewById(R.id.edit_text_beertype);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerRating = findViewById(R.id.number_picker_rating);

        numberPickerRating.setMinValue(1);
        numberPickerRating.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit the beer");
            editTextBeerName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextBeerType.setText(intent.getStringExtra(EXTRA_TYPE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerRating.setValue(intent.getIntExtra(EXTRA_RATING, 1));
        } else {
            setTitle("Add new beer");
        }
    }

    //Sending the data to the main activity
    private void saveBeer(){
        String beerName = editTextBeerName.getText().toString();
        String beerType = editTextBeerType.getText().toString();
        String description = editTextDescription.getText().toString();
        int rating = numberPickerRating.getValue();

        if (beerName.trim().isEmpty() || beerType.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a beer name, type and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, beerName);
        data.putExtra(EXTRA_TYPE, beerType);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_RATING, rating);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_beer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_beer:
                saveBeer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
