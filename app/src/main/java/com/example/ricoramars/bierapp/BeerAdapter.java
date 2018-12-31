package com.example.ricoramars.bierapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerHolder> {
    private List<Beer> beers = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public BeerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.beer_item, viewGroup, false);
        return new BeerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerHolder beerHolder, int i) {
        Beer currentBeer = beers.get(i);
        beerHolder.textViewBeerName.setText(currentBeer.getBeerName());
        beerHolder.textViewBeerType.setText(currentBeer.getBeerType());
        beerHolder.textViewBeerDesc.setText(currentBeer.getDescription());
        beerHolder.textViewRating.setText(String.valueOf(currentBeer.getRating()));
    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    public void setBeers(List<Beer> beers) {
        this.beers = beers;
        notifyDataSetChanged();
    }

    public Beer getBeerAt(int position) {
        return beers.get(position);
    }

    class BeerHolder extends RecyclerView.ViewHolder {
        private TextView textViewBeerName;
        private TextView textViewBeerType;
        private TextView textViewBeerDesc;
        private TextView textViewRating;

        public BeerHolder(@NonNull View itemView) {
            super(itemView);
            textViewBeerName = itemView.findViewById(R.id.text_view_beername);
            textViewBeerType = itemView.findViewById(R.id.text_view_beertype);
            textViewBeerDesc = itemView.findViewById(R.id.text_view_description);
            textViewRating = itemView.findViewById(R.id.text_view_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(beers.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Beer beer);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }
}
