package com.example.captemperature;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Temperature {
    private float value;
    private String dateTime;

    // Constructeur
    public Temperature(float value, long timestamp) {
        this.value = value;

        // Convertir le timestamp en millisecondes vers une date formatée
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        this.dateTime = sdf.format(new Date(timestamp));
    }

    public float getValue() {
        return value;
    }

    public String getDateTime() {
        return dateTime;
    }
}
