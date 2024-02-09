package com.example.yogdaan;

import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.ViewHolder> {
    Context context;
    ArrayList<UserModel> arrayList;
    private static final int REQUEST_PHONE_CALL = 1;

    Intent callintent;

    public EmergencyAdapter(Context context, ArrayList<UserModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public EmergencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(context).inflate(R.layout.emergency_item , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmergencyAdapter.ViewHolder holder, int position) {
        holder.donorname.setText("Donor Name : "+arrayList.get(position).getUsername());
        holder.donorphoneno.setText("Donor Blood Group : "+arrayList.get(position).getUserphoneno());
        holder.bloodg.setText("Blood Group : " +arrayList.get(position).getUserbloodtype());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String no = "+91"+arrayList.get(position).getUserphoneno();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel:"+no));
                context.startActivity(intent);
            }
        });



    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView donorname , donorphoneno , bloodg;
        Button call;
        String donationtype;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            donorname = itemView.findViewById(R.id.usernameotem);
            donorphoneno = itemView.findViewById(R.id.userphonenoitem);
            bloodg = itemView.findViewById(R.id.userbgitem);
            call = itemView.findViewById(R.id.calluser);
        }
    }
}
