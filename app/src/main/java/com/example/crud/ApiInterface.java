package com.example.crud;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
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
    Call<List<CrudModel>> getCrudData(@Header("auth_token") String tkon, @Path("id") String id);

    //creating new task post request
    @POST("/crudpage/{id}")
    Call<CrudModel> createNewTask(@Path("id") String id, @Body CrudModel crudModel);

    //DELETE REQUEST TO DELETE TASK
    @DELETE("crudpage/{id}")
    Call<CrudModel> deleteTask(@Path("id") String id);

    //UPDATE REQUEST
    @PATCH("/crudpage/{id}")
    Call<CrudModel> updateTask(@Path("id") String id, @Body CrudModel crudModel);
}
