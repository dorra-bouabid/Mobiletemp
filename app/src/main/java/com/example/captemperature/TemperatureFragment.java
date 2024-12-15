package com.example.captemperature;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TemperatureFragment extends Fragment implements SensorEventListener {

    private TemperatureApi temperatureApi;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private TextView TemperatureTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);

        // Initialize the sensor manager and temperature sensor
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        // Initialize the TextView for displaying the temperature
        TemperatureTextView = view.findViewById(R.id.TemperatureTextView);

        // Initialize Retrofit
        Retrofit retrofit = RetrofitClient.getRetrofitInstance("http://10.0.2.2:5002/"); // Use 10.0.2.2 for emulator
        temperatureApi = retrofit.create(TemperatureApi.class);

        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float temperature = event.values[0];  // The temperature value from the sensor

            // Format and display the temperature value
            String temperatureText = String.format("%.1f°C", temperature);
            TemperatureTextView.setText(temperatureText);

            // Create the request object to send to the backend
            TemperatureRequest temperatureRequest = new TemperatureRequest(temperature);

            // Send the temperature to the backend
            saveTemperatureToBackend(temperatureRequest);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // You can handle sensor accuracy changes if necessary
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register the listener for the temperature sensor
        if (temperatureSensor != null) {
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener when the fragment is paused to save battery
        if (temperatureSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    private void saveTemperatureToBackend(TemperatureRequest temperatureRequest) {
        Call<Void> call = temperatureApi.saveTemperature(temperatureRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Temperature saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Failed to save temperature", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
