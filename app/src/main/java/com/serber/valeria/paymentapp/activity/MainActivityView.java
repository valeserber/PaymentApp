package com.serber.valeria.paymentapp.activity;

import com.serber.valeria.paymentapp.model.PaymentMethod;

import java.util.List;

public interface MainActivityView {

    void loadErrorView(String message);

    void filterResponse(List<PaymentMethod> list);
}
