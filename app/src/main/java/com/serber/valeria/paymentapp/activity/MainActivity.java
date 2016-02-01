package com.serber.valeria.paymentapp.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.serber.valeria.paymentapp.Configuration;
import com.serber.valeria.paymentapp.PaymentAppApplication;
import com.serber.valeria.paymentapp.R;
import com.serber.valeria.paymentapp.adapter.PaymentMethodAdapter;
import com.serber.valeria.paymentapp.model.PaymentMethod;
import com.serber.valeria.paymentapp.model.PaymentType;
import com.serber.valeria.paymentapp.network.NetworkErrors;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView mPaymentMethodView;
    ProgressBar mLoadingView;
    SwipeRefreshLayout  mRefreshView;
    PaymentMethodAdapter mPaymentAdapter;
    FrameLayout mErrorView;
    TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLayout();
        setListeners();
        startLoading();
    }

    private void setUpLayout() {
        setContentView(R.layout.activity_main);
        mLoadingView = (ProgressBar) findViewById(R.id.activity_main_loading);
        mRefreshView = (SwipeRefreshLayout) findViewById(R.id.activity_main_refresh_view);
        mErrorView = (FrameLayout) findViewById(R.id.error_view);
        mErrorMessage = (TextView) findViewById(R.id.error_view_message);
        mErrorView.setVisibility(View.GONE);
        mPaymentMethodView = (RecyclerView) findViewById(R.id.activity_main_payment_view);
        mPaymentAdapter = new PaymentMethodAdapter(this);
        mPaymentMethodView.setAdapter(mPaymentAdapter);
        mPaymentMethodView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListeners() {
        mRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshView.setRefreshing(true);
                loadData();
            }
        });
    }

    private void startLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        loadData();
    }

    private void finishLoading() {
        mLoadingView.setVisibility(View.GONE);
        mPaymentMethodView.setVisibility(View.VISIBLE);
        mRefreshView.setRefreshing(false);
    }

    private void clearPreviousResults() {
        mPaymentMethodView.setVisibility(View.GONE);
        mPaymentAdapter.clear();
        mErrorView.setVisibility(View.GONE);
    }

    private void loadErrorView(String message) {
        mRefreshView.setRefreshing(false);
        mLoadingView.setVisibility(View.GONE);
        mPaymentMethodView.setVisibility(View.GONE);
        mErrorMessage.setText(message);
        mErrorView.setVisibility(View.VISIBLE);
    }

    private void loadData() {
        clearPreviousResults();

        Call<List<PaymentMethod>> call = PaymentAppApplication.sPaymentMethodService
                .getPaymentMethods(Configuration.PUBLIC_KEY);
        call.enqueue(new Callback<List<PaymentMethod>>() {
            @Override
            public void onResponse(Response<List<PaymentMethod>> response) {
                if (response.errorBody() != null) {
                    int code = response.code();
                    String error;
                    if (code == NetworkErrors.HTTP_BAD_REQUEST.getErrorCode()) {
                        error = NetworkErrors.HTTP_BAD_REQUEST.getErrorType();
                    } else if (code == NetworkErrors.HTTP_UNAUTHORIZED.getErrorCode()) {
                        error = NetworkErrors.HTTP_UNAUTHORIZED.getErrorType();
                    } else if (code == NetworkErrors.HTTP_NOT_FOUND.getErrorCode()) {
                        error = NetworkErrors.HTTP_NOT_FOUND.getErrorType();
                    } else {
                        error = NetworkErrors.UNKNOWN_ERROR.getErrorType();
                    }
                    loadErrorView(error);
                } else {
                    filterResponse(response.body());
                }
            }


            @Override
            public void onFailure(Throwable t) {
                loadErrorView(getString(R.string.network_error));
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
        finishLoading();
    }
}
