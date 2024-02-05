package com.example.yogdaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.MyViewHolder> {

    ArrayList<DonorModel> arrayList;
    Context context;


    public DonationAdapter(ArrayList<DonorModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DonationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(context).inflate(R.layout.donrequestitemlist , parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationAdapter.MyViewHolder holder, int position) {
        holder.donorname.setText("Donor Name : "+arrayList.get(position).getDonorname());
        holder.donorphoneno.setText("Donor Phone No : "+arrayList.get(position).getDonorphoneno());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public  class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView donorname , donorphoneno;
        Button view ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            donorname=itemView.findViewById(R.id.donornameitem);
            donorphoneno=itemView.findViewById(R.id.donorphonenoitem);
            view = itemView.findViewById(R.id.View);
        }
    }
}