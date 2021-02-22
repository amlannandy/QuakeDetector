package com.aknindustries.quakedetector;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeViewHolder> {

    private final ArrayList<Earthquake> earthquakes;
    private final ListItemClicked listItemClicked;

    EarthquakeAdapter(ArrayList<Earthquake> earthquakes, ListItemClicked listItemClicked) {
        this.earthquakes = earthquakes;
        this.listItemClicked = listItemClicked;
    }

    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        EarthquakeViewHolder earthquakeViewHolder = new EarthquakeViewHolder(view);
        view.setOnClickListener(v -> this.listItemClicked.onClick(this.earthquakes.get(earthquakeViewHolder.getAdapterPosition()).getUrl()));
        return earthquakeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {

        // Set color
        String color = this.earthquakes.get(position).getColor();
        GradientDrawable background = (GradientDrawable) holder.magnitude.getBackground();
        background.setColor(Color.parseColor(color));

        // Format and set location
        String[] locationDetails;
        String upperLocation, lowerLocation;
        try {
            locationDetails = this.earthquakes.get(position).getLocation().split(" of ");
            upperLocation = locationDetails[0];
            lowerLocation = locationDetails[1];
        } catch (Exception e) {
            locationDetails = this.earthquakes.get(position).getLocation().split(" - ");
            try {
                upperLocation = locationDetails[0];
                lowerLocation = locationDetails[1];
            } catch (Exception exception) {
                upperLocation = "";
                lowerLocation = locationDetails[0];
            }
        }
        holder.upperLocation.setText(upperLocation);
        holder.lowerLocation.setText(lowerLocation);

        // Format date
        Date date = new Date(this.earthquakes.get(position).getTimestamp());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM DD, yyyy");
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH: MM");
        String upperDate = simpleDateFormat.format(date);
        String lowerDate = simpleTimeFormat.format(date);
        holder.upperDate.setText(upperDate);
        holder.lowerDate.setText(lowerDate);

        // Set magnitude
        String magnitude = Double.toString(this.earthquakes.get(position).getMagnitude());
        holder.magnitude.setText(magnitude);

    }

    @Override
    public int getItemCount() {
        return this.earthquakes.size();
    }
}

class EarthquakeViewHolder extends RecyclerView.ViewHolder {

    final TextView magnitude;
    final TextView upperLocation;
    final TextView lowerLocation;
    final TextView upperDate;
    final TextView lowerDate;

    public EarthquakeViewHolder(@NonNull View itemView) {
        super(itemView);
        this.magnitude = itemView.findViewById(R.id.magnitude);
        this.upperLocation = itemView.findViewById(R.id.upperLocation);
        this.lowerLocation = itemView.findViewById(R.id.lowerLocation);
        this.upperDate = itemView.findViewById(R.id.upperDate);
        this.lowerDate = itemView.findViewById(R.id.lowerDate);
    }

}

interface ListItemClicked {

    void onClick(String url);

}