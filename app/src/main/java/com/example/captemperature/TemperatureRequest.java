package com.example.captemperature;

public class TemperatureRequest {
    private float value;  // Temperature value to send to the backend

    // Constructor
    public TemperatureRequest(float value) {
        this.value = value;
    }

    // Getter
    public float getValue() {
        return value;
    }

    // Setter
    public void setValue(float value) {
        this.value = value;
    }
}
