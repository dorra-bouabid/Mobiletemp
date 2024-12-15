package com.example.captemperature;

import com.example.captemperature.TemperatureRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TemperatureApi {

    // Change the method to accept the TemperatureRequest class
    @POST("/temperature/save")
    Call<Void> saveTemperature(@Body TemperatureRequest temperatureRequest);  // Correct type here
}
