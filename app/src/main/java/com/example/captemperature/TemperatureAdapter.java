package com.example.captemperature;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TemperatureAdapter extends RecyclerView.Adapter<TemperatureAdapter.TemperatureViewHolder> {

    private List<Temperature> temperatureList;

    // Constructeur
    public TemperatureAdapter(List<Temperature> temperatureList) {
        this.temperatureList = temperatureList;
    }

    @NonNull
    @Override
    public TemperatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_temperature, parent, false); // Item layout
        return new TemperatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemperatureViewHolder holder, int position) {
        Temperature temperature = temperatureList.get(position);

        // Afficher la température et la date
        holder.temperatureTextView.setText(String.format("%.1f°C", temperature.getValue()));
        holder.dateTimeTextView.setText(temperature.getDateTime());
    }


    @Override
    public int getItemCount() {
        return temperatureList.size();
    }

    public static class TemperatureViewHolder extends RecyclerView.ViewHolder {
        TextView temperatureTextView, dateTimeTextView;

        public TemperatureViewHolder(@NonNull View itemView) {
            super(itemView);
            temperatureTextView = itemView.findViewById(R.id.TemperatureTextView);
            dateTimeTextView = itemView.findViewById(R.id.date_time_value);
        }
    }
}
