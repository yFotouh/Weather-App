package com.task.parenttechnicaltask.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.task.parenttechnicaltask.AppClass;


public class ConnectionUtil {
    public static NetworkInfo getNetworkInfo() {
        ConnectivityManager connMgr = (ConnectivityManager) AppClass.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connMgr.getActiveNetworkInfo();
    }

    public static boolean isOnline() {
        NetworkInfo networkInfo = ConnectionUtil.getNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isGpsWorking() {

        final LocationManager manager = (LocationManager) AppClass.getInstance().getSystemService(Context.LOCATION_SERVICE);

        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
