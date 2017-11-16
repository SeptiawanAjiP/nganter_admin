package com.example.septiawanajipradan.nganteradmin;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.septiawanajipradan.nganteradmin.helper.Alamat;
import com.example.septiawanajipradan.nganteradmin.helper.AppContoller;
import com.example.septiawanajipradan.nganteradmin.helper.SessionManager;
import com.example.septiawanajipradan.nganteradmin.today.TodayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Septiawan Aji Pradan on 7/14/2017.
 */

public class OrderActivity extends AppCompatActivity{
    private TextView tanggaltv;
    private RecyclerView order;
    private TodayAdapter todayAdapter;
    private ArrayList<Order> arrayOrder;
    String status;
    int totalBefore,totalAfter;
    private SessionManager sessionManager;
    Handler mHandler;
    Runnable runnable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_fragment);
        tanggaltv = (TextView)findViewById(R.id._tanggal);
        order = (RecyclerView)findViewById(R.id.recycler_order);
        mHandler = new Handler();

        tanggaltv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNotification();
            }
        });

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.setFirst();
        if(sessionManager.getFirst()==null){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
        arrayOrder = new ArrayList<>();
        totalBefore=0;
        monitoring();
    }


    public void getData(){
        arrayOrder.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Alamat.ALAMT_SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Order order;
                try{
                    Log.d("__order",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("respon");
                    Log.d("__order_2",jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        Log.d("__order_3",jsonArray.get(i).toString());
                        JSONObject j = jsonArray.getJSONObject(i);
                        order = new Order(j.getString("id_order"),
                                j.getString("username"),
                                j.getString("pesanan"),
                                j.getString("jam_antar"),
                                j.getString("no_wa"),
                                j.getString("lokasi_antar"),
                                j.getString("create_at_time"),
                                j.getString("status"),
                                j.getString("info"));
                        arrayOrder.add(order);
//                        lastId = Integer.parseInt(order.getIdOrder());
                    }

                    if(totalBefore!=jsonArray.length()){
                        addNotification();
                    }
                    totalBefore = jsonArray.length();

                    orderList(arrayOrder);
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
                map.put("kode","admin_get_order");
//                map.put("last_id",Integer.toString(last));
                return map;
            }
        };
        AppContoller.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void orderList(ArrayList<Order> arrayOrder){
        Log.d("__order_4",arrayOrder.toString());
        todayAdapter = new TodayAdapter(arrayOrder,OrderActivity.this);
        todayAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        order.setLayoutManager(layoutManager);
        order.setItemAnimator(new DefaultItemAnimator());
        order.setAdapter(todayAdapter);

    }

    private void addNotification() {
        android.support.v4.app.NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Pesanan Baru")
//                        .setSound(Uri.parse("android.resource://"
//                                + getApplicationContext().getPackageName() + "/" + R.raw.bell.mp3))
                        .setContentText("Ada pesanan baru, segera cek");

        Intent notificationIntent = new Intent(this, OrderActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
//        builder.setDefaults(Notification.DEFAULT_SOUND);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.bell);
        mp.start();
        manager.notify(0, builder.build());
    }

    public void monitoring(){

        runnable = new Runnable() {
            @Override
            public void run() {
                if(adaKoneksi()){
                    getData();
                }else{
                    Toast.makeText(OrderActivity.this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                }


                mHandler.postDelayed(this, 10000);
            }
        };
        mHandler.post(runnable);
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
                        Toast.makeText(OrderActivity.this, "Layanan telah ditutup", Toast.LENGTH_SHORT).show();
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
                map.put("status","tutup");
                return map;
            }
        };
        AppContoller.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_utama, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout){
            if(adaKoneksi()){
                upadateBukaTutup();
                Intent intent = new Intent(OrderActivity.this,SplashScreen.class);
                startActivity(intent);
                sessionManager.delete();
                finish();
            }else{
                Toast.makeText(OrderActivity.this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
            }

        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public boolean adaKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


}

