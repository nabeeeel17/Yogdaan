package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;

public class PaymentGateway extends AppCompatActivity {

    EditText amnt , desc;
    Button pay;
    String unique;
    Float amt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);
        init();



        pay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    amt = Float.parseFloat(amnt.getText().toString())
;                    paymentgateway();
Log.e("amnt" , ""+amt);
                } catch (AppNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

     void paymentgateway() throws AppNotFoundException {
         EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                 .setPayeeVpa("farzana.samir22@oksbi")
                 .setPayeeName("Nabeel Shaikh")
                 .setPayeeMerchantCode("12345")
                 .setTransactionId(unique)
                 .setTransactionRefId(unique)
                 .setDescription(desc.getText().toString())
                 .setAmount(String.valueOf(amt));

         EasyUpiPayment easyUpiPayment = builder.build();
         easyUpiPayment.startPayment();


         easyUpiPayment.setPaymentStatusListener(new PaymentStatusListener() {
             @Override
             public void onTransactionCompleted(@NonNull TransactionDetails transactionDetails) {
                 Log.e("TransactionDetails", transactionDetails.toString());
                Enum state = transactionDetails.getTransactionStatus();
                Log.e("state" , ""+state);

             }

             @Override
             public void onTransactionCancelled() {

             }
         });
     }


    public void init (){
       amnt = findViewById(R.id.amount);
       desc = findViewById(R.id.description);
       pay = findViewById(R.id.pay);
unique = UUID.randomUUID().toString();

    }
}