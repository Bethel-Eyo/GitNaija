package com.eyo.bethel.gitnaija.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Utils {
    public static boolean isDeviceOnline(Context context) {
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            boolean isConnected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return isConnected;
        } catch (Exception e) {
            Log.v("Utils:  Connectivity", e.toString());
            return false;
        }
    }

    public static String computeWeakHash(String string){
        return String.format(string, "%08x%08x", string.hashCode(), string.length());
    }
}
