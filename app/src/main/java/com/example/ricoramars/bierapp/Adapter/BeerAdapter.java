package com.example.ricoramars.bierapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ricoramars.bierapp.Entities.Beer;
import com.example.ricoramars.bierapp.R;

public class BeerAdapter extends ListAdapter <Beer, BeerAdapter.BeerHolder> {
    private OnItemClickListener listener;

    public BeerAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Beer> DIFF_CALLBACK = new DiffUtil.ItemCallback<Beer>() {
        @Override
        public boolean areItemsTheSame(@NonNull Beer oldItem, @NonNull Beer newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Beer oldItem, @NonNull Beer newItem) {
            return oldItem.getBeerName().equals(newItem.getBeerName()) &&
                    oldItem.getBeerType().equals(newItem.getBeerType()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getRating() == newItem.getRating();
        }
    };

    @NonNull
    @Override
    public BeerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.beer_item, viewGroup, false);
        return new BeerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerHolder beerHolder, int i) {
        Beer currentBeer = getItem(i);
        beerHolder.textViewBeerName.setText(currentBeer.getBeerName());
        beerHolder.textViewBeerType.setText(currentBeer.getBeerType());
        beerHolder.textViewBeerDesc.setText(currentBeer.getDescription());
        beerHolder.textViewRating.setText(String.valueOf(currentBeer.getRating()));
    }

    public Beer getBeerAt(int position) {
        return getItem(position);
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
                        listener.onItemClick(getItem(position));
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
