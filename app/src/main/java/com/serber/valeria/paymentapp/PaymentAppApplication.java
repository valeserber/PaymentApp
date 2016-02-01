package com.serber.valeria.paymentapp;

import android.app.Application;
import android.content.Context;

import com.serber.valeria.paymentapp.network.PaymentMethodService;
import com.serber.valeria.paymentapp.network.RetrofitService;

public class PaymentAppApplication extends Application {

    public static PaymentMethodService sPaymentMethodService;

    private static Context sContext;
    private static PaymentAppApplication sApplication;

    public static Context getAppContext() {
        return sContext;
    }

    public static PaymentAppApplication getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        sContext = getApplicationContext();
        RetrofitService.initialize();
        setUpServices();
    }

    private void setUpServices() {
        this.sPaymentMethodService = RetrofitService.getService(PaymentMethodService.class);
    }
}
