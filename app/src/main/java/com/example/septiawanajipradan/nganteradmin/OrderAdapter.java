package com.example.septiawanajipradan.nganteradmin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Septiawan Aji Pradan on 4/21/2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{
    private ArrayList<Order> arrayOrder;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView idOrder,namaPemesan,jamPesan,noTelp;
        public MyViewHolder(View view){
            super(view);

            idOrder = (TextView)view.findViewById(R.id.id_order);
            namaPemesan = (TextView)view.findViewById(R.id.nama_pemesan);
            jamPesan = (TextView)view.findViewById(R.id.jam_pesan);
            noTelp = (TextView)view.findViewById(R.id.no_telp);

        }
    }

    public OrderAdapter(ArrayList<Order> arrayOrder){
        this.arrayOrder= arrayOrder;
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
    }

    @Override
    public int getItemCount() {
        return arrayOrder.size();
    }
}
