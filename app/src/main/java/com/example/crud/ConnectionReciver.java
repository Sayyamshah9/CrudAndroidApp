package com.example.crud;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

public class ConnectionReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);

        //DEFINING DIALOG BOX
        NetworkUtil checkConnection = new NetworkUtil(context);
        AlertDialog.Builder checkInternetDialog = new AlertDialog.Builder(context);
        checkInternetDialog.setCancelable(false);
        checkInternetDialog.setTitle("No Internet Connection!");
        checkInternetDialog.setMessage("Connect to Internet and Restart App");

        if(status.isEmpty() || status.equals("No Internet Connection Available") || status.equals("No Internet Connection")){
            AlertDialog ad = checkInternetDialog.create();
            ad.show();
        }else {
            AlertDialog ad1 = checkInternetDialog.create();
            ad1.cancel();
        }
    }
}
