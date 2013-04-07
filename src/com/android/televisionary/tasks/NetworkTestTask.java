package com.android.televisionary.tasks;

import java.io.ObjectInputStream.GetField;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkTestTask{

	public boolean isNetworkAvailable(){
        boolean available = false;

        ConnectivityManager connMgr = null;
 
        /** Getting active network interface  to get the network's status */
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
 
        if(networkInfo !=null && networkInfo.isAvailable())
            available = true;
 
        /** Returning the status of the network */
        return available;
    }
}
