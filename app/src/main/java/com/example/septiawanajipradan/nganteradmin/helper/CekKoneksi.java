package com.example.septiawanajipradan.nganteradmin.helper;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by aji on 9/23/2017.
 */

public class CekKoneksi {

    public CekKoneksi(Context context){

    }
    public static boolean CekKoneksi(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    };
}
