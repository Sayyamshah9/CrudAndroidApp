package com.example.crud;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    //post request for regristration
    @POST("/userspage/register")
    Call<UserModel> CreateNewUser(@Body UserModel userModel);

    //post request for login user
    @POST("/userspage/login")
    Call<UserModel> loginUser(@Body UserModel userModel);

    //get request for getting crud data by userid
    @GET("/crudpage/{id}")
    Call<List<CrudModel>> getCrudData(@Path("_id") String id);
}
