package com.example.yogdaan;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


    public class MoneyAdapter extends RecyclerView.Adapter<com.example.yogdaan.MoneyAdapter.MyViewHolder> {

        ArrayList<MoneyModel> moneylist;
        Context context;
        public MoneyAdapter(ArrayList<MoneyModel> moneylist, Context context) {
            this.moneylist = moneylist;
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = (View) LayoutInflater.from(context).inflate(R.layout.moneyitemlist , parent , false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.name.setText("Name : "+moneylist.get(position).getName());
            holder.address.setText("Address : " +moneylist.get(position).getAddress());
            holder.upi.setText("UPI ID :" +moneylist.get(position).getUpi());
            holder.paytoorg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.intent = new Intent(view.getContext(), PaymentGateway.class);
                    holder.intent.putExtra("UPI", moneylist.get(position).getUpi());
                    Log.e("upi" , ""+holder.upi.getText());
                    view.getContext().startActivity(holder.intent);
                }
            });
        }



        @Override
        public int getItemCount() {
            return moneylist.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView name, address ,upi;
            Button paytoorg;
            Intent intent;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.orgnameitem);
                address = itemView.findViewById(R.id.orgaddress);
                upi = itemView.findViewById(R.id.upi);
                paytoorg = itemView.findViewById(R.id.paytorg);
            }
        }
}

