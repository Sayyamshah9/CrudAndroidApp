package com.example.crud;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://apicrudapp.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
