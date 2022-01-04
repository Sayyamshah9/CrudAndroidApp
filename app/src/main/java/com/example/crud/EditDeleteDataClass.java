package com.example.crud;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDeleteDataClass {

    Context context;
    List<CrudModel> crudMode;

    public EditDeleteDataClass(Context context) {
        this.context = context;
    }

    RetrofitClient retrofitClient = new RetrofitClient();
    ApiInterface apiInterface = retrofitClient.retrofit.create(ApiInterface.class);

    public void deleteTask(String userID){
        Call<CrudModel> callDelete = apiInterface.deleteTask(userID);
        callDelete.enqueue(new Callback<CrudModel>() {
            @Override
            public void onResponse(@NonNull Call<CrudModel> call, @NonNull Response<CrudModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<CrudModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void editTask()
    public void editTask(String UID, String title ,String subtitle,String description,String duedate){
        CrudModel Cm = new CrudModel(title, subtitle, description, duedate);
        Call<CrudModel> callUpdate = apiInterface.updateTask(UID, Cm);
        callUpdate.enqueue(new Callback<CrudModel>() {
            @Override
            public void onResponse(@NonNull Call<CrudModel> call, @NonNull Response<CrudModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<CrudModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
