package com.example.crud;

import static android.content.ContentValues.TAG;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

    ImageView logoutBtn;
    SessionManager sessionManager;
    RecyclerView recyclerView;
    Button addtaskbtn, refreshbtn;
    TextView textViewUserName, emptyytxtView;
    //VARIABLES FOR DIALOG BOX
    EditText editTextTitle, editTextSubTitle, editTextDescription, editTextDueDate;
    String stringTitle, stringSubTitle, stringDescription, stringDueDate;
    BroadcastReceiver bReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();

        //CHECKING INTERNET CONNECTION CODE
        bReceiver = new ConnectionReciver();
        registerReceiver(bReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        sessionManager = new SessionManager(getApplicationContext());
        logoutBtn = findViewById(R.id.logoutbtn);
        addtaskbtn = findViewById(R.id.addtaskbtn);
        refreshbtn = findViewById(R.id.refreshbtn);
        recyclerView = findViewById(R.id.tasklist);
        textViewUserName = findViewById(R.id.uName);
        emptyytxtView = findViewById(R.id.emptytxt);


        //GETTING ID'S FROM SHARED PREFERENCES
        String userId = sessionManager.pref.getString("USER_ID", "NULL");
        String usernameFromSP = sessionManager.pref.getString("User_Name", "NULL");
        textViewUserName.setText(usernameFromSP);

        //REFRESH BTN CODE
        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Refresh...", Toast.LENGTH_SHORT).show();
                getCrudDataAndSetInRecyclerView(userId);
            }
        });

        //ADD TASK BTN CODE
        addtaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent);
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
        getCrudDataAndSetInRecyclerView(userId);
    }

    public  void getCrudDataAndSetInRecyclerView(String Uid){
        RetrofitClient retrofitClient1 = new RetrofitClient();
        ApiInterface apiInterface1 = retrofitClient1.retrofit.create(ApiInterface.class);

        String jsonToken = sessionManager.pref.getString("JWT_TOKEN", "NULL");

        Call<List<CrudModel>> crudCall = apiInterface1.getCrudData(jsonToken, Uid);
        crudCall.enqueue(new Callback<List<CrudModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CrudModel>> call, @NonNull Response<List<CrudModel>> response) {
                if(response.isSuccessful()){
                   List<CrudModel> data = response.body();
                   if(data.isEmpty()){
                       recyclerView.setVisibility(View.GONE);
                       emptyytxtView.setVisibility(View.VISIBLE);
                   }else {
                       //RECYCLERVIEW CODE
                       recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                       CrudAdapter crudAdapter = new CrudAdapter(MainActivity.this,MainActivity.this, data);
                       recyclerView.setAdapter(crudAdapter);
                   }
                }else {
                    Log.e(TAG, response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<CrudModel>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void deleteCrudData(CrudModel dCM) {
        EditDeleteDataClass editDeleteDataClass = new EditDeleteDataClass(MainActivity.this);
        editDeleteDataClass.deleteTask(dCM.get_id().toString());
    }
    @Override
    public void editCrudData(CrudModel eCM) {
        openDailog(eCM);
    }

    private void openDailog(CrudModel eCM) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Update Task");
        alertDialog.setMessage("Update the Data");

        View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        alertDialog.setView(dialogView);
        alertDialog.setCancelable(false);

        editTextTitle = dialogView.findViewById(R.id.dialogTitle);
        editTextSubTitle = dialogView.findViewById(R.id.dialogSubTitle);
        editTextDescription = dialogView.findViewById(R.id.dialogDescription);
        editTextDueDate = dialogView.findViewById(R.id.dialogDueDate);

        editTextTitle.setText(eCM.getTitle());
        editTextSubTitle.setText(eCM.getSubtitle());
        editTextDescription.setText(eCM.getDescription());
        editTextDueDate.setText(eCM.getDuedate());

        //SETTING ONCLICK LISTENER TO DUE DATE
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Due Date");
        final MaterialDatePicker materialDatePicker = builder.build();
        editTextDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
//                stringDueDate = materialDatePicker.getHeaderText();
                editTextDueDate.setText(materialDatePicker.getHeaderText());
            }
        });

        alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                stringTitle = editTextTitle.getText().toString();
                stringSubTitle = editTextSubTitle.getText().toString();
                stringDescription = editTextDescription.getText().toString();
                stringDueDate = editTextDueDate.getText().toString();

                if(stringTitle.length() != 0 && stringSubTitle.length() != 0 && stringDescription.length() != 0 && stringDueDate.length() != 0){
                    //SENDING DATA TO API BODY
                    EditDeleteDataClass editDeleteDataClass = new EditDeleteDataClass(MainActivity.this);
                    editDeleteDataClass.editTask(eCM.get_id(), stringTitle, stringSubTitle, stringDescription, stringDueDate);
                }else {
                    Toast.makeText(MainActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}