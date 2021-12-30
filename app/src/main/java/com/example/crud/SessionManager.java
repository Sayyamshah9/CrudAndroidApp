package com.example.crud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    public String PREF_NAME = "session";

    public SessionManager(Context context){
        this._context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createSession(Boolean loginStatus){

//        editor.putString("EMAIL_KEY",email);
        editor.putBoolean("STATUS_KEY", loginStatus);
        editor.apply();
    }

    public void isLoggedIn(){

        if(isLogin()){
            Intent i = new Intent(_context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public Boolean isLogin(){
        return pref.getBoolean("STATUS_KEY", false);
    }

    public void Logout(){
        editor.clear();
        editor.apply();

        Intent i = new Intent(_context, loginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }
}
