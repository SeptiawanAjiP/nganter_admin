package com.example.septiawanajipradan.nganteradmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Septiawan Aji Pradan on 7/16/2017.
 */

public class DetailPesananDialog extends Dialog {
    Activity activity;
    Order order;
    EditText detail;
    Button hubungi,eksekusi;
    int def;
    ProgressDialog progressDialog;

    String pesanan;
    clearArray clear;

    public DetailPesananDialog(Activity activity,Order order){
        super(activity);
        this.activity = activity;
        this.order = order;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pesanan);
        detail = (EditText)findViewById(R.id.detail);
        hubungi = (Button)findViewById(R.id.btn_hubungi);
        eksekusi = (Button)findViewById(R.id.btn_eksekusi);
        detail.setEnabled(false);
        if(order.getStatus().equals("tolak")){
            pesanan = "Nama Pemesan : "+order.getNamaPemesan()+
                    "\n"+
                    "No Telp : "+order.getNoTelp()+
                    "\n"+
                    "Pesanan : "+order.getPesanan()+
                    "\n"+
                    "Jam Antar : "+order.getJamAntar()+
                    "\n"+
                    "Lokasi Antar : "+order.getLokasiAntar()+
                    "\n"+"\n"+
                    "Alasan ditolak : "+ order.getInfo();
            detail.setText(pesanan);
        }else{
            pesanan = "Nama Pemesan : "+order.getNamaPemesan()+
                    "\n"+
                    "No Telp : "+order.getNoTelp()+
                    "\n"+
                    "Pesanan : "+order.getPesanan()+
                    "\n"+
                    "Jam Antar : "+order.getJamAntar()+
                    "\n"+
                    "Lokasi Antar : "+order.getLokasiAntar();
            detail.setText(pesanan);
        }


        hubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order.getStatus().equals("ambil")){
                    openWhatsApp(order.getNoTelp());
                }else if (order.getStatus().equals("antri")){
                    openWhatsApp(order.getNoTelp());
                }else{
                    Toast.makeText(activity, "Pesanan Sudah dibatalkan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        eksekusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order.getStatus().equals("antri")){
                    final String[] pilihan = {"Terima","Tolak"};
                    def = 0;
                    AlertDialog dialog = new AlertDialog.Builder(activity)
                            .setTitle("Pilih Proses")
                            .setSingleChoiceItems(pilihan, 0,  new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    def = which;
                                }
                            })
                            .setPositiveButton("Pilih", new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(def==0){
                                        if (adaKoneksi()){
                                            shareMedia(order.getIdOrder());
                                        }else{
                                            Toast.makeText(activity, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                                        }

                                    }else if(def==1){
                                        DialogAlasanDitolak dialogAlasanDitolak = new DialogAlasanDitolak(activity,order.getIdOrder());
                                        dialogAlasanDitolak.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialogAlasanDitolak.show();
                                        dismiss();
                                    }
                                }
                            }).create();
                    dialog.show();
                }else if(order.getStatus().equals("ambil")){
                    final String[] pilihan = {"Order selesai","Order Batal"};
                    def = 0;
                    AlertDialog dialog = new AlertDialog.Builder(activity)
                            .setTitle("Pilih Proses")
                            .setSingleChoiceItems(pilihan, 0,  new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    def = which;
                                }
                            })
                            .setPositiveButton("Pilih", new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(def==0){
                                        if (adaKoneksi()){
                                            orderSelesai(order.getIdOrder());
                                        }else{
                                            Toast.makeText(activity, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                                        }

                                    }else if(def==1){
                                        DialogAlasanDitolak dialogAlasanDitolak = new DialogAlasanDitolak(activity,order.getIdOrder());
                                        dialogAlasanDitolak.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        dialogAlasanDitolak.show();
                                        dismiss();
                                    }
                                }
                            }).create();
                    dialog.show();
                }else if(order.getStatus().equals("tolak")){
                    Toast.makeText(activity, "Pesanan telah ditolak", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void terimaPesanan(final String idOrder){
        Log.d("__JU",idOrder);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Alamat.ALAMT_SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject  jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")){
                        dismiss();
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
                map.put("kode","admin_ambil_pesanan");
                map.put("id_order",idOrder);
                return map;
            }
        };
        AppContoller.getInstance(activity.getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void orderSelesai(final String idOrder){
        Log.d("__JU",idOrder);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Alamat.ALAMT_SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject  jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("1")){
                        dismiss();
                        Toast.makeText(activity, "Order Selesai", Toast.LENGTH_SHORT).show();
                        Log.d("ajari0",response.toString());
                    }

                }catch (Exception e){
                    Log.d("ajari",e.toString());
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
                map.put("kode","job_selesai");
                map.put("id_order",idOrder);
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

    public interface clearArray{
        void finish(String hapus);
    }

    public void setDialog(DetailPesananDialog.clearArray dialogResult){
        clear = dialogResult;
    }

    public void shareMedia(String idOrder){
        List<Intent> targetShareIntents = new ArrayList<Intent>();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> resInfos = pm.queryIntentActivities(shareIntent, 0);
        if (!resInfos.isEmpty()) {
            System.out.println("Have package");
            for (ResolveInfo resInfo : resInfos) {
                String packageName = resInfo.activityInfo.packageName;
                Log.i("Package Name", packageName);

                if (packageName.contains("com.twitter.android")
                        || packageName.contains("com.facebook.katana")
                        || packageName.contains("com.whatsapp") || packageName.contains("com.google.android.apps.plus")
                        || packageName.contains("com.google.android.talk") || packageName.contains("com.slack")
                        || packageName.contains("com.google.android.gm") || packageName.contains("com.facebook.orca")
                        || packageName.contains("com.yahoo.mobile") || packageName.contains("com.skype.raider")
                        || packageName.contains("com.android.mms")|| packageName.contains("com.linkedin.android")
                        || packageName.contains("com.google.android.apps.messaging")) {
                    Intent intent = new Intent();

                    intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                    intent.putExtra("AppName", resInfo.loadLabel(pm).toString());
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, pesanan);
                    intent.setPackage(packageName);
                    targetShareIntents.add(intent);
                }
            }
            if (!targetShareIntents.isEmpty()) {
                Collections.sort(targetShareIntents, new Comparator<Intent>() {
                    @Override
                    public int compare(Intent o1, Intent o2) {
                        return o1.getStringExtra("AppName").compareTo(o2.getStringExtra("AppName"));
                    }
                });
                Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                activity.startActivity(chooserIntent);
                terimaPesanan(idOrder);
            } else {
                Toast.makeText(activity, "No app to share.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openWhatsApp(String waNumber) {
        if(waNumber.startsWith("0")){
            waNumber = waNumber.replace("0","62");
        }else{
            if(waNumber.startsWith("+62")){
                waNumber = waNumber.replace("+62","62");
            }
        }
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(waNumber) + "@s.whatsapp.net");//phone number without "+" prefix
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(sendIntent);
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(activity, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            activity.startActivity(goToMarket);
        }
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = activity.getApplicationContext().getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public boolean adaKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


}
