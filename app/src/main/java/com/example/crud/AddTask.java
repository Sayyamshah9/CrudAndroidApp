package com.example.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTask extends AppCompatActivity {

    ImageView backbtn;
    Button addtask;
    EditText title, subtitle, description, ddate;
    String stitle, sstitle, sdescrip, sddate;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //GETTING VIEWS
        backbtn = findViewById(R.id.backbtn);
        addtask = findViewById(R.id.addtask);
        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.stitle);
        description = findViewById(R.id.descrip);
        ddate = findViewById(R.id.ddate);

        //MATERIAL DESIGN DATE PICKER
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Due Date");
        final MaterialDatePicker materialDatePicker = builder.build();
        //OPENING DIALOG OF CALENDER
        ddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });
        //GETTING DATE AND STORING IN VARIABLE
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                sddate = materialDatePicker.getHeaderText();
                ddate.setText(sddate);
            }
        });

        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stitle = title.getText().toString();
                sstitle = subtitle.getText().toString();
                sdescrip = description.getText().toString();

                //GETTING USERID FROM SHARED PREFERENCES
                sessionManager = new SessionManager(getApplicationContext());
                String uid = sessionManager.pref.getString("USER_ID", "NULL");
                //SENDING DATA TO API (POST REQ)
                newTask(stitle, sstitle, sdescrip, sddate, uid);
            }
        });

        //BACKBTN CODE
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void newTask(String stitle, String sstitle, String sdescrip, String sddate, String uid){

        Toast.makeText(AddTask.this, uid, Toast.LENGTH_SHORT).show();
        RetrofitClient crudRetrofitClient = new RetrofitClient();
        ApiInterface crudApiInterface = crudRetrofitClient.retrofit.create(ApiInterface.class);

        CrudModel crudModel = new CrudModel(stitle, sstitle, sdescrip, sddate);
        Call<CrudModel> crudCall = crudApiInterface.createNewTask(uid ,crudModel);

        crudCall.enqueue(new Callback<CrudModel>() {
            @Override
            public void onResponse(@NonNull Call<CrudModel> call, @NonNull Response<CrudModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AddTask.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddTask.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(AddTask.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<CrudModel> call, @NonNull Throwable t) {
                Toast.makeText(AddTask.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}