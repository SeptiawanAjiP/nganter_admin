package com.example.septiawanajipradan.nganteradmin.history;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.septiawanajipradan.nganteradmin.Order;
import com.example.septiawanajipradan.nganteradmin.R;
import com.example.septiawanajipradan.nganteradmin.helper.Alamat;
import com.example.septiawanajipradan.nganteradmin.helper.AppContoller;
import com.example.septiawanajipradan.nganteradmin.today.TodayAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aji on 11/14/2017.
 */

public class HistoryFragment extends Fragment {

    private TextView setTanggal,tanggalTv,noOrder;
    private View view;
    private int tahun,bulan,tanggal;
    private ArrayList<Order> arrayList;
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private AVLoadingIndicatorView avi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history_fragment,container,false);
        setTanggal = (TextView)view.findViewById(R.id.set_tanggal);
        tanggalTv = (TextView)view.findViewById(R.id.tanggal);
        noOrder = (TextView)view.findViewById(R.id.no_order);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_history);
        avi = (AVLoadingIndicatorView)view.findViewById(R.id.avi);

        setTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avi.show();

                final Calendar calendar = Calendar.getInstance();
                tahun = calendar.get(Calendar.YEAR);
                bulan = calendar.get(Calendar.MONTH);
                tanggal = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tanggalTv.setText(i+"-"+(i1+1)+"-"+i2);
                        tanggalTv.setVisibility(View.VISIBLE);
                        getData(i+"-"+(i1+1)+"-"+i2);
                    }
                },tahun,bulan,tanggal);
                datePickerDialog.show();
            }
        });
        return view;
    }

    public void getData(final String tanggal){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Alamat.ALAMT_SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Log.d("riwayat",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("respon");
                    arrayList = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        Order order = new Order();
                        JSONObject object = jsonArray.getJSONObject(i);
                        order.setIdOrder(object.getString("id_order"));
                        order.setNamaPemesan(object.getString("username"));
                        order.setStatus(object.getString("status"));
                        order.setPesanan(object.getString("pesanan"));
                        order.setWaktuPesan(object.getString("create_at_time"));
                        order.setKategori(object.getString("kategori"));
                        order.setRating(object.getString("rating"));
                        order.setSaran(object.getString("saran"));
                        order.setLokasiAntar(object.getString("lokasi_antar"));
                        order.setInfo(object.getString("info"));
                        order.setJamAntar(object.getString("jam_antar"));
                        order.setNoTelp(object.getString("no_telp"));
                        arrayList.add(order);
                    }
                    setAdapter(arrayList);
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
                map.put("kode","admin_get_history");
                map.put("tanggal",tanggal);
                return map;
            }
        };
        AppContoller.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    public void setAdapter(ArrayList<Order> orders){
        Log.d("riwayat_size",""+orders.size());
        avi.hide();
        if (orders.size()==0){
            noOrder.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            noOrder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            historyAdapter = new HistoryAdapter(orders,getActivity());
            historyAdapter.notifyDataSetChanged();
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(historyAdapter);
        }

    }
}
