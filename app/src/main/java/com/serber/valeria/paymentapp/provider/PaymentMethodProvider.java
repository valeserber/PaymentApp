package com.serber.valeria.paymentapp.provider;

import android.content.Context;

import com.serber.valeria.paymentapp.Configuration;
import com.serber.valeria.paymentapp.PaymentAppApplication;
import com.serber.valeria.paymentapp.R;
import com.serber.valeria.paymentapp.activity.MainActivityView;
import com.serber.valeria.paymentapp.model.PaymentMethod;
import com.serber.valeria.paymentapp.network.NetworkErrors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodProvider {

    private MainActivityView mView;
    private Context mContext;

    public PaymentMethodProvider(MainActivityView view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    public void getPaymentMethods() {
        Call<List<PaymentMethod>> call = PaymentAppApplication.sPaymentMethodService
                .getPaymentMethods(Configuration.PUBLIC_KEY);

        call.enqueue(new Callback<List<PaymentMethod>>() {
            @Override
            public void onResponse(Response<List<PaymentMethod>> response) {
                if (response.errorBody() != null) {
                    mView.loadErrorView(getErrorMessage(response.code()));
                } else {
                    mView.filterResponse(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mView.loadErrorView(mContext.getString(R.string.network_error));
            }
        });
    }

    public String getErrorMessage(int code) {
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
        return error;
    }

}
