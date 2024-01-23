package com.example.yogdaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
ArrayList<CharityModel> arrayList;

Context context;

    public Adapter(ArrayList<CharityModel> arrayList, Context context ) {
        this.arrayList= arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = (View) LayoutInflater.from(context).inflate(R.layout.charityitem_list, parent, false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     holder.name.setText("Name : "+arrayList.get(position).getName());
     holder.address.setText("Address : " +arrayList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, address;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.orgnameitem);
            address = itemView.findViewById(R.id.orgaddress);

        }
    }
}
