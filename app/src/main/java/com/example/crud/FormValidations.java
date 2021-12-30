package com.example.crud;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class FormValidations{

    //-------------------------------------------REGRISTRATION PAGE VALIDATIONS-------------------------------------------
    //USERNAME VALIDATION
    Boolean name(@NonNull String name, TextView errorView, TextView txtview){

        if(name.length() == 0){
            errorView.setText("Invalid Username*");
            errorView.setVisibility(View.VISIBLE);
        }

        txtview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorView.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        return true;
    }

    //EMAIL VALIDATION
    Boolean email(@NonNull String email, TextView errorView, TextView txtview){

        if(email.length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorView.setText("Invalid Email*");
            errorView.setVisibility(View.VISIBLE);
            return false;
        }
        txtview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorView.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        return true;
    }

    //PASSWORD VALIDATION
    Boolean password(@NonNull String password, TextView errorView, TextView txtview){

        if(password.length() == 0) {
            errorView.setText("Invalid Password*");
            errorView.setVisibility(View.VISIBLE);
            return false;
        }
        txtview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorView.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        return true;
    }

    //CONFIRMPASSWORD VALIDATION
    Boolean confirmPass(@NonNull String confirmPass, String password, TextView errorView, TextView txtview){

        if(confirmPass.length() == 0 || !confirmPass.equals(password)) {
            errorView.setText("Password Didn't Match*");
            errorView.setVisibility(View.VISIBLE);
            return false;
        }
        txtview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorView.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        return true;
    }

    //-------------------------------------------LOGIN PAGE VALIDATION-------------------------------------------
    //EMAIL VALIDATION
//    Boolean loginEmail
}
