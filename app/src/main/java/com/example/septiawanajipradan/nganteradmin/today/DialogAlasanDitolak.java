package com.example.septiawanajipradan.nganteradmin.today;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.septiawanajipradan.nganteradmin.helper.Alamat;
import com.example.septiawanajipradan.nganteradmin.helper.AppContoller;
import com.example.septiawanajipradan.nganteradmin.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aji on 7/27/2017.
 */

public class DialogAlasanDitolak extends Dialog {
    private Activity activity;
    private EditText alasan;
    private Button tolak;

    ProgressDialog progressDialog;
    private String idOrder;
    public DialogAlasanDitolak(Activity activity,String idOrder){
        super(activity);
        this.activity = activity;
        this.idOrder = idOrder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ditolak);
        alasan = (EditText)findViewById(R.id.alasan);
        tolak = (Button)findViewById(R.id.tolak);

        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adaKoneksi()){
                    tolakPesanan(idOrder,alasan.getText().toString());
                }else{
                    Toast.makeText(activity, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void tolakPesanan(final String idOrder,final String info){
        showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Alamat.ALAMT_SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")){
                        Toast.makeText(activity, "Pesanan telah dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                    progressDialog.dismiss();
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
                map.put("kode","admin_tolak_pesanan");
                map.put("id_order",idOrder);
                map.put("info",info);
                return map;
            }
        };
        AppContoller.getInstance(activity.getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void showProgress() {
        progressDialog = null;// Initialize to null
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public boolean adaKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
