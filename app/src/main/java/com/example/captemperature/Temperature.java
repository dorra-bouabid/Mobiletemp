package com.example.captemperature;

public class Temperature {
    private Long id;          // Identifiant unique
    private float value;      // Valeur de la température
    private String timestamp; // Horodatage de la température

    // Getter et Setter pour `id`
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter et Setter pour `value`
    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    // Getter et Setter pour `timestamp`
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
