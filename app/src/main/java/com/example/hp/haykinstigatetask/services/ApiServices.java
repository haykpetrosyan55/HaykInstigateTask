package com.example.hp.haykinstigatetask.services;

import com.example.hp.haykinstigatetask.models.ModelForUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("api/")
    Call<ModelForUser> randomUsers(@Query("results") Integer results);
}
