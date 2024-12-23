package com.example.captemperature;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TemperatureFragment extends Fragment implements SensorEventListener {

    private SQLiteHelper sqliteHelper;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private TextView TemperatureTextView;
    private Button addTemperatureButton;

    private ArrayList<Temperature> temperatureList;  // Liste pour stocker les températures

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialisation de la vue
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);

        // Initialiser SQLiteHelper
        sqliteHelper = new SQLiteHelper(getContext());

        // Initialiser le gestionnaire de capteur et le capteur de température
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        // Initialiser les vues
        TemperatureTextView = view.findViewById(R.id.TemperatureTextView);
        addTemperatureButton = view.findViewById(R.id.add_temperature_button);

        // Initialiser la liste de températures
        temperatureList = new ArrayList<>();

        // Ajouter le bouton pour naviguer et ajouter la température
        addTemperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TemperatureTextView != null && TemperatureTextView.getText() != null) {
                    try {
                        // Lire la température depuis le TextView
                        float currentTemperature = Float.parseFloat(
                                TemperatureTextView.getText().toString().replace("°C", "")
                        );

                        // Ajouter la température avec timestamp dans SQLite
                        long timestamp = System.currentTimeMillis();
                        sqliteHelper.insertTemperature(currentTemperature);

                        // Ajouter dans la liste locale
                        temperatureList.add(new Temperature(currentTemperature, timestamp));

                        // Naviguer vers DataActivity avec l'intent
                        Intent intent = new Intent(getActivity(), DataActivity.class);
                        intent.putExtra("temperature", currentTemperature);
                        intent.putExtra("timestamp", timestamp);
                        startActivity(intent);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Erreur : Valeur incorrecte", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float temperature = event.values[0];  // La température lue depuis le capteur

            // Afficher la température dans le TextView
            String temperatureText = String.format("%.1f°C", temperature);
            TemperatureTextView.setText(temperatureText);

            // Sauvegarder dans la base de données SQLite
            sqliteHelper.insertTemperature(temperature);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Gérer les changements d'exactitude, si nécessaire
    }

    @Override
    public void onResume() {
        super.onResume();
        if (temperatureSensor != null) {
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (temperatureSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }
}
