package com.example.crud;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView logoutBtn;
    SessionManager sessionManager;
    RecyclerView recyclerView;
    Button addtaskbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());
        logoutBtn = findViewById(R.id.logoutbtn);
        addtaskbtn = findViewById(R.id.addtaskbtn);

        //ADD TASK BTN CODE
        addtaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent);
                finish();
            }
        });

    //LOGOUT BUTTON CODE
        //DIALOG BOX
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you Sure you want to logout?");
        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        //LOGOUT BTN ONCLICK CODE
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.Logout();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //CALLING CRUD API
        String userId = sessionManager.pref.getString("USER_ID", "NULL");
        getCrudDataAndSetInRecyclerView(userId);
    }

    public  void getCrudDataAndSetInRecyclerView(String Uid){
        RetrofitClient retrofitClient1 = new RetrofitClient();
        ApiInterface apiInterface1 = retrofitClient1.retrofit.create(ApiInterface.class);

        Call<List<CrudModel>> crudCall = apiInterface1.getCrudData(Uid);
        crudCall.enqueue(new Callback<List<CrudModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CrudModel>> call, @NonNull Response<List<CrudModel>> response) {
                if(response.isSuccessful()){
                    List<CrudModel> data = response.body();
                    //RECYCLERVIEW CODE
                    recyclerView = findViewById(R.id.tasklist);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    CrudAdapter crudAdapter = new CrudAdapter(MainActivity.this, data);
                    recyclerView.setAdapter(crudAdapter);
                }else {
                    Log.e(TAG, response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<CrudModel>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}