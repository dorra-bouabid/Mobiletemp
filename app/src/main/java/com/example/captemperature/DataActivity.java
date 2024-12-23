package com.example.captemperature;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TemperatureAdapter temperatureAdapter;
    private ArrayList<Temperature> temperatureList;  // Liste pour stocker les températures

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.les_donnes);

        // Initialiser la RecyclerView
        recyclerView = findViewById(R.id.temperature_log_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialiser la liste de températures
        temperatureList = new ArrayList<>();

        // Récupérer les données de l'intent
        if (getIntent().hasExtra("temperature")) {
            float currentTemperature = getIntent().getFloatExtra("temperature", 0.0f);
            long timestamp = System.currentTimeMillis();  // Obtenir le temps actuel en millisecondes// Heure actuelle
            temperatureList.add(new Temperature(currentTemperature, timestamp));
        }

        // Configurer l'adapter pour afficher les données dans la RecyclerView
        temperatureAdapter = new TemperatureAdapter(temperatureList);
        recyclerView.setAdapter(temperatureAdapter);
    }
}
