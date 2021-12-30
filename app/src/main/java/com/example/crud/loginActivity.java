package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginActivity extends AppCompatActivity {

    TextView result, LoginEmailError, LoginPassError, register;
    EditText LoginEmail, LoginPassword;
    Button loginbtn;
    String sLoginEmail, sLoginPassword;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        sessionManager.isLoggedIn();

//        Toast.makeText(loginActivity.this, "Status: " + sessionManager.isLogin() , Toast.LENGTH_SHORT).show();

        //FETCHING VIEWS
        LoginEmail = findViewById(R.id.LoginEmail);
        LoginPassword = findViewById(R.id.LoginPassword);
        LoginEmailError = findViewById(R.id.LoginEmailError);
        LoginPassError = findViewById(R.id.LoginPasswordError);
        loginbtn = findViewById(R.id.loginbtn);
        result = findViewById(R.id.result);
        register = findViewById(R.id.regtxtbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GETTING VALUES ENTERED BY USER
                sLoginEmail = LoginEmail.getText().toString().trim();
                sLoginPassword = LoginPassword.getText().toString().trim();

                FormValidations isValid1 = new FormValidations();
                if(isValid1.email(sLoginEmail, LoginEmailError, LoginEmail) || isValid1.password(sLoginPassword, LoginPassError, LoginPassword)){
                    //SENDING DATA TO API
                    postLoginData(sLoginEmail, sLoginPassword);
                }
            }
        });

        //CLICK LISTNER FOR DIRECTING TO REGRISTRATION PAGE
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, RegristrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void postLoginData(String Email, String Password) {
        RetrofitClient retrofitClient1 = new RetrofitClient();
        ApiInterface apiInterface1 = retrofitClient1.retrofit.create(ApiInterface.class);

        UserModel Model1 = new UserModel(Email, Password);
        Call<UserModel> call1 = apiInterface1.loginUser(Model1);


        call1.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    String responseFromApi = response.body().getMsg().toString();
                    if(responseFromApi.equals("Logged In")){
                        sessionManager.createSession(true);
                        Intent i = new Intent(loginActivity.this, MainActivity.class);
                        startActivity(i);
                        return;
                    }
                    Toast.makeText(loginActivity.this, responseFromApi, Toast.LENGTH_SHORT).show();
                }else {
                    result.setText(response.message());
                }
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });

    }
}