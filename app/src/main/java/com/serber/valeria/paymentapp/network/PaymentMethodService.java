package com.serber.valeria.paymentapp.network;

import com.serber.valeria.paymentapp.model.PaymentMethod;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PaymentMethodService {

    @GET("/v1/payment_methods")
//    void getPaymentMethods(@Query("public_key") String public_key,
//                           Callback<List<PaymentMethod>> callback);

    Call<List<PaymentMethod>> getPaymentMethods(@Query("public_key") String public_key);

}
