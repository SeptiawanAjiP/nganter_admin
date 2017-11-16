package com.example.septiawanajipradan.nganteradmin.history;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.septiawanajipradan.nganteradmin.Order;
import com.example.septiawanajipradan.nganteradmin.R;
import com.example.septiawanajipradan.nganteradmin.today.DetailPesananDialog;
import com.example.septiawanajipradan.nganteradmin.today.TodayAdapter;

import java.util.ArrayList;

/**
 * Created by aji on 11/14/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder>{
private ArrayList<Order> arrayOrder;
private Activity activity;

public class MyViewHolder extends RecyclerView.ViewHolder{
    private TextView pemesan,jenis,waktu,ditolak;
    private RatingBar rating;
    public MyViewHolder(View view){
        super(view);

        pemesan = (TextView)view.findViewById(R.id.pemesan);
        jenis = (TextView)view.findViewById(R.id.jenis_layanan);
        waktu = (TextView)view.findViewById(R.id.waktu_pesan);
        rating = (RatingBar)view.findViewById(R.id.ratingBar);
        ditolak = (TextView)view.findViewById(R.id.tolak_history);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailPesananDialog cdd= new DetailPesananDialog(activity,arrayOrder.get(getAdapterPosition()),DetailPesananDialog.HISTORY);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });
    }
}

    public HistoryAdapter(ArrayList<Order> arrayOrder, Activity activity){
        this.arrayOrder= arrayOrder;
        this.activity = activity;
    }

    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_adapter,null);
        return new HistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.MyViewHolder holder, int position) {
        Order order = arrayOrder.get(position);

        holder.pemesan.setText(order.getNamaPemesan());
        holder.waktu.setText(order.getWaktuPesan());
        holder.rating.setRating(Float.parseFloat(order.getRating()));
        if(order.getKategori().equals("beli_makanan")){
            holder.jenis.setText("Beli makan");
        }else if(order.getKategori().equals("antar_barang")){
            holder.jenis.setText("Antar barang");
        }else if(order.getKategori().equals("beli_barang")){
            holder.jenis.setText("Beli barang");
        }
        if(order.getStatus().equals("tolak")){
            holder.ditolak.setVisibility(View.VISIBLE);
            holder.rating.setVisibility(View.GONE);
        }else if(order.getStatus().equals("selesai")){
            holder.ditolak.setVisibility(View.GONE);
            holder.rating.setVisibility(View.VISIBLE);
            holder.rating.setRating(Float.parseFloat(order.getRating()));
        }

    }

    @Override
    public int getItemCount() {
        return arrayOrder.size();
    }

}
