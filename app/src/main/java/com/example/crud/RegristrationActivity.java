package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegristrationActivity extends AppCompatActivity {

    public TextView logintxtbtn, result, nameError, emailError, passwordError, confirmPassError;
    EditText username, email, password, confirm;
    Button regbtn;
    String susername, semail, spassword, sconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regristration);

        //FETCHING VIEWS
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        confirm = findViewById(R.id.confirmpass);
        regbtn = findViewById(R.id.signupbtn);
        result = findViewById(R.id.result);
        //FETCHING ERROR TEXTVIEWS
        nameError = findViewById(R.id.nameError);
        emailError = findViewById(R.id.emailError);
        passwordError = findViewById(R.id.passwordError);
        confirmPassError = findViewById(R.id.confirmPassError);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GETTING VALUES ENTERED BY USER
                susername = username.getText().toString();
                semail = email.getText().toString();
                spassword = password.getText().toString();
                sconfirm = confirm.getText().toString();

                //CREATING OBJECT OF VALIDATION CLASS
                FormValidations isValid = new FormValidations();
                
                if(isValid.name(susername, nameError, username) && isValid.email(semail, emailError, email) && isValid.password(spassword, passwordError, password) && isValid.confirmPass(sconfirm, spassword, confirmPassError, confirm) ){
//                    Toast.makeText(RegristrationActivity.this, "Sending Data to API", Toast.LENGTH_SHORT).show();
                    //SENDING DATA TO API POST REQUEST METHOD
                    postdata(susername,semail,spassword,sconfirm);
                }
            }
        });

        //SETTING ONCLICK TO REDIRECT TO LOGIN PAGE
        logintxtbtn = findViewById(R.id.logintxtbtn);
        logintxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //  METHOD TO SEND DATA TO API AND RECIVE RESPONSE
    private void postdata(String susername, String semail, String spassword, String sconfirm) {

        RetrofitClient retrofitClient = new RetrofitClient();
        ApiInterface apiInterface = retrofitClient.retrofit.create(ApiInterface.class);

        UserModel Model = new UserModel(susername, semail, spassword, sconfirm);

        Call<UserModel> call = apiInterface.CreateNewUser(Model);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                username.setText("");
                email.setText("");
                password.setText("");
                confirm.setText("");

                if(response.isSuccessful()){
                    Toast.makeText(RegristrationActivity.this, "Registred Successfully, Login with your Credentials" , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegristrationActivity.this,loginActivity.class);
                    startActivity(i);
                }else {
                    String messageFromApi = response.message();
                    result.setText(messageFromApi);
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });
    }
}