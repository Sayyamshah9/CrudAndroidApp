package com.example.crud;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class NetworkUtil {

    Context context;
    public NetworkUtil(Context context) {
        this.context = context;
    }

    public static String getConnectivityStatusString(Context context){
        String status = null;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                status = "WI-FI ENABLED";
                return status;
            }else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                status = "Mobile Data Enabled";
                return status;
            }
        }else {
            status = "No Internet Connection Available";
            return status;
        }
        return status;
    }
}
