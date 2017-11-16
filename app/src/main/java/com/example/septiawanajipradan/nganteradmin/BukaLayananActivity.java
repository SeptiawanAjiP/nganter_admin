package com.example.septiawanajipradan.nganteradmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.septiawanajipradan.nganteradmin.helper.Alamat;
import com.example.septiawanajipradan.nganteradmin.helper.AppContoller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Septiawan Aji Pradan on 7/14/2017.
 */

public class BukaLayananActivity extends AppCompatActivity {
    private RelativeLayout aktif;
    ProgressDialog progressDialog;
    String status;
    TextView keterangan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buka_layanan_activity);
        aktif = (RelativeLayout)findViewById(R.id.rl_buka_layanan);
        aktif.setVisibility(View.GONE);
        keterangan = (TextView)findViewById(R.id.keterangan);
        if(adaKoneksi()){
            cekBukaTutup();
        }else{
            Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void cekBukaTutup(){
        showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Alamat.ALAMT_SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("__buka_tutup",response);
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    status = jsonObject.getString("status");
                    if(status.equals("tutup")){
                        progressDialog.dismiss();
                        aktif.setVisibility(View.VISIBLE);
                        keterangan.setVisibility(View.VISIBLE);
                        aktif.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                upadateBukaTutup();
                                Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }else{
                        progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("kode","cek_buka_tutup");
                return map;
            }
        };
        AppContoller.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void showProgress() {
        progressDialog = null;// Initialize to null
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public void upadateBukaTutup(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Alamat.ALAMT_SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("__buka_tutup",response);
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    status = jsonObject.getString("status");
                    if(status.equals("tutup")){

                    }else{

                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("kode","buka_tutup");
                map.put("status","buka");
                return map;
            }
        };
        AppContoller.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
    public boolean adaKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
