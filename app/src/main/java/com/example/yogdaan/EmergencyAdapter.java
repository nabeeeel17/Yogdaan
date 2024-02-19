package com.example.yogdaan;

import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.ViewHolder> {
    Context context;
    ArrayList<UserModel> arrayList;
    String encode , email;
    Bitmap bitmap;
    FirebaseFirestore firestore;
    Uri newuri;
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
        email = arrayList.get(position).getEmail();
        holder.donorname.setText("Donor Name : "+arrayList.get(position).getUsername());
        holder.donorphoneno.setText("Donor Blood Group : "+arrayList.get(position).getUserphoneno());
        holder.bloodg.setText("Blood Group : " +arrayList.get(position).getUserbloodtype());
        seturi(holder , position);


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
    public void seturi(ViewHolder viewHolder , int position) {
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("Users Details").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //Log.e("tagggggg" , ""+task.getResult());
                String base64 = task.getResult().getString("uri");
                //Log.e("taggg" , ""+uri);
                byte[] decodedstr = Base64.decode(base64 , Base64.DEFAULT);
                Log.e("dec" , ""+decodedstr);
                bitmap = byteArrayToBitmap(decodedstr);
                Log.e("bitmapp" , ""+bitmap);
                viewHolder.userimage.setImageBitmap(bitmap);



            }
        });


    }
    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView donorname , donorphoneno , bloodg;
        Button call;
         ImageView userimage;
        String email, encode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            donorname = itemView.findViewById(R.id.usernameotem);
            donorphoneno = itemView.findViewById(R.id.userphonenoitem);
            bloodg = itemView.findViewById(R.id.userbgitem);
            call = itemView.findViewById(R.id.calluser);
            userimage = itemView.findViewById(R.id.userimage);
        }
    }
}
