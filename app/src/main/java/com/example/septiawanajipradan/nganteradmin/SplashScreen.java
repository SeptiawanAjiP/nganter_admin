package com.example.septiawanajipradan.nganteradmin;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;

public class SplashScreen extends AppCompatActivity {

    HashMap<String,String> hm ;
    private ArrayList<String> tipeKuliner;
    private ArrayList<String> kawasan;
    private RequestQueue queue;

    private View mTarget;

    private SessionManager sessionManager;
    private HashMap<String,String> tempatMakanHashmapFavorit;

//    private SessionManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        queue = Volley.newRequestQueue(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());
        startAnimation();
    }


    private void startAnimation() {
//        rope =  YoYo.with(Techniques.FadeInUp)
//                .duration(1500)
//                .interpolate(new AccelerateDecelerateInterpolator())
//                .playOn(mTarget);

        Thread splash = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                    if(sessionManager.getFirst()!=null){
                        Intent intent = new Intent(getApplicationContext(),BukaLayananActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        splash.start();
    }

    public boolean adaKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
