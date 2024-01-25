package com.example.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;
public class PaymentGateway extends AppCompatActivity {

    EditText amnt, desc;
    Button pay;
    String unique, uniqueref;
    String upi, payeename;
    Float amt;
    String orgupi;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String passupi = Organisationdetails.passupi;

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

                    ;
                    paymentgateway();
                    Log.e("amnt", "" + amt);
                } catch (AppNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    void paymentgateway() throws AppNotFoundException {
        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                .setPayeeVpa(orgupi)
                .setPayeeName(payeename)
                .setTransactionId(unique)
                .setTransactionRefId(uniqueref)
                .setDescription(desc.getText().toString())
                .setAmount(String.valueOf(amt));
        Log.e("payeevpa", "" + orgupi);
        Log.e("Payee Name", "" + payeename);
        Log.e("Transaction ID", "" + unique);
        Log.e("Transaction REF ID", "" + uniqueref);
        Log.e("Description", "" + desc.getText());
        Log.e("AMmount", "" + String.valueOf(amt));
        EasyUpiPayment easyUpiPayment = builder.build();
        easyUpiPayment.startPayment();


        easyUpiPayment.setPaymentStatusListener(new PaymentStatusListener() {
            @Override
            public void onTransactionCompleted(@NonNull TransactionDetails transactionDetails) {
                Log.e("TransactionDetails", transactionDetails.toString());
                Enum state = transactionDetails.getTransactionStatus();
                Toast.makeText(PaymentGateway.this, "Transaction Successfull", Toast.LENGTH_SHORT).show();
                Log.e("state", "" + state);

            }

            @Override
            public void onTransactionCancelled() {

            }
        });
    }


    public void init() {
        amnt = findViewById(R.id.amount);
        desc = findViewById(R.id.description);
        pay = findViewById(R.id.pay);
        unique = UUID.randomUUID().toString();
        Intent intent = getIntent();
        orgupi = intent.getStringExtra("UPI");
        Log.e("upi", "" + orgupi);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        payeename = auth.getCurrentUser().getEmail();
        uniqueref = UUID.randomUUID().toString();
        Log.e("display", "" + payeename);


    }
}