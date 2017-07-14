package com.example.septiawanajipradan.nganteradmin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
    private OrderAdapter orderAdapter;
    private ArrayList<Order> arrayOrder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
        tanggaltv = (TextView)findViewById(R.id._tanggal);
        order = (RecyclerView)findViewById(R.id.recycler_order);
        arrayOrder = new ArrayList<>();
        getData();
    }

    public void getData(){
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
                                j.getString("create_at_time"));
                        arrayOrder.add(order);
                    }
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
                return map;
            }
        };
        AppContoller.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void orderList(ArrayList<Order> arrayOrder){
        Log.d("__order_4",arrayOrder.toString());
        orderAdapter = new OrderAdapter(arrayOrder);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        order.setLayoutManager(layoutManager);
        order.setItemAnimator(new DefaultItemAnimator());
        order.setAdapter(orderAdapter);
    }
}
