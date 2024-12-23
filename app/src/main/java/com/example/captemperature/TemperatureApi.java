package com.example.captemperature;

import com.example.captemperature.TemperatureRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TemperatureApi {
    @POST("/saveTemperature")
    Call<Void> saveTemperature(@Body TemperatureRequest temperatureRequest);
}
