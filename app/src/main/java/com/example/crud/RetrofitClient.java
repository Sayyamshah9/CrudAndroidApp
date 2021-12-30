package com.example.crud;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.104:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
