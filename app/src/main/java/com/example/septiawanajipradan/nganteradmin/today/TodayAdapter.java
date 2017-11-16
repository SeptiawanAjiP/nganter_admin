package com.example.septiawanajipradan.nganteradmin.today;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.septiawanajipradan.nganteradmin.Order;
import com.example.septiawanajipradan.nganteradmin.R;

import java.util.ArrayList;

/**
 * Created by Septiawan Aji Pradan on 4/21/2017.
 */

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.MyViewHolder>{
    private ArrayList<Order> arrayOrder;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView idOrder,namaPemesan,jamPesan,noTelp,sedang;
        public CardView cardView;
        public RelativeLayout rlBg;
        public MyViewHolder(View view){
            super(view);

            idOrder = (TextView)view.findViewById(R.id.id_order);
            namaPemesan = (TextView)view.findViewById(R.id.nama_pemesan);
            jamPesan = (TextView)view.findViewById(R.id.jam_pesan);
            noTelp = (TextView)view.findViewById(R.id.no_telp);
            cardView = (CardView) view.findViewById(R.id.card);
            sedang = (TextView)view.findViewById(R.id.sedang_diproses);
            rlBg = (RelativeLayout)view.findViewById(R.id.rl_bg);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailPesananDialog cdd= new DetailPesananDialog(activity,arrayOrder.get(getAdapterPosition()),DetailPesananDialog.TODAY);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
//                    cdd.setDialog(new DetailPesananDialog.clearArray() {
//                        @Override
//                        public void finish(String hapus) {
//                            arrayOrder.clear();
//                        }
//                    });
                }
            });
        }
    }

    public TodayAdapter(ArrayList<Order> arrayOrder, Activity activity){
        this.arrayOrder= arrayOrder;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_order,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = arrayOrder.get(position);

        holder.idOrder.setText(order.getIdOrder());
        holder.namaPemesan.setText("Pemesan : "+order.getNamaPemesan());
        holder.jamPesan.setText("Jam Pesan : "+order.getWaktuPesan());
        holder.noTelp.setText(order.getNoTelp());
        if(arrayOrder.get(position).getStatus().equals("ambil")){
            holder.rlBg.setBackgroundColor(Color.parseColor("#ffb74c"));
            holder.sedang.setVisibility(View.VISIBLE);
            holder.sedang.setText("sedang diproses");
        }else if(arrayOrder.get(position).getStatus().equals("tolak")){
            holder.rlBg.setBackgroundColor(Color.parseColor("#ff5959"));
            holder.sedang.setVisibility(View.VISIBLE);
            holder.sedang.setText("ditolak");
        }else if(arrayOrder.get(position).getStatus().equals("antri")){
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }else if(arrayOrder.get(position).getStatus().equals("selesai")){
            holder.rlBg.setBackgroundColor(Color.parseColor("#06dfb1"));
            holder.sedang.setVisibility(View.VISIBLE);
            holder.sedang.setText("selesai");
        }

    }

    @Override
    public int getItemCount() {
        return arrayOrder.size();
    }

}
