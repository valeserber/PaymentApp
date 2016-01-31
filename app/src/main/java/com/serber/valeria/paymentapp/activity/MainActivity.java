package com.serber.valeria.paymentapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.serber.valeria.paymentapp.R;
import com.serber.valeria.paymentapp.adapter.PaymentMethodAdapter;
import com.serber.valeria.paymentapp.model.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mPaymentMethodView;
    PaymentMethodAdapter mPaymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPaymentMethodView = (RecyclerView) findViewById(R.id.activity_main_payment_view);

        List<PaymentMethod> list = new ArrayList<>();

        mPaymentAdapter = new PaymentMethodAdapter(this, list);
//        mPaymentAdapter = new PaymentMethodAdapter(this);
        mPaymentMethodView.setAdapter(mPaymentAdapter);
        mPaymentMethodView.setLayoutManager(new LinearLayoutManager(this));
    }
}
