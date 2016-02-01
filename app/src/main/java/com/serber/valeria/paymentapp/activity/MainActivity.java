package com.serber.valeria.paymentapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.serber.valeria.paymentapp.Configuration;
import com.serber.valeria.paymentapp.PaymentAppApplication;
import com.serber.valeria.paymentapp.R;
import com.serber.valeria.paymentapp.adapter.PaymentMethodAdapter;
import com.serber.valeria.paymentapp.model.PaymentMethod;
import com.serber.valeria.paymentapp.model.PaymentType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView mPaymentMethodView;
    PaymentMethodAdapter mPaymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPaymentMethodView = (RecyclerView) findViewById(R.id.activity_main_payment_view);
        mPaymentAdapter = new PaymentMethodAdapter(this);
        mPaymentMethodView.setAdapter(mPaymentAdapter);
        mPaymentMethodView.setLayoutManager(new LinearLayoutManager(this));
        loadData();
    }

    private void loadData() {
        Call<List<PaymentMethod>> call = PaymentAppApplication.sPaymentMethodService
                .getPaymentMethods(Configuration.PUBLIC_KEY);
        call.enqueue(new Callback<List<PaymentMethod>>() {
            @Override
            public void onResponse(Response<List<PaymentMethod>> response) {
                filterResponse(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("lala", t.getMessage());
            }
        });
    }

    private void filterResponse(List<PaymentMethod> list) {
        List<PaymentMethod> creditCardList = new ArrayList<>();
        for (PaymentMethod method: list) {
            if (method.getPaymentTypeId().equals(PaymentType.CREDIT_CARD.getTypeId())) {
                creditCardList.add(method);
            }
        }
        mPaymentAdapter.addResults(creditCardList);
    }
}
