package com.serber.valeria.paymentapp.model;

import com.serber.valeria.paymentapp.PaymentAppApplication;
import com.serber.valeria.paymentapp.R;

public enum PaymentType {

    TICKET("ticket", PaymentAppApplication.getAppContext().getString(R.string.ticket)),
    ATM("atm", PaymentAppApplication.getAppContext().getString(R.string.atm)),
    CREDIT_CARD("credit_card", PaymentAppApplication.getAppContext().getString(R.string.credit_card)),
    DEBIT_CARD("debit_card", PaymentAppApplication.getAppContext().getString(R.string.debit_card)),
    PREPAID_CARD("prepaid_card", PaymentAppApplication.getAppContext().getString(R.string.prepaid_card));

    private String type_id;
    private String name;

    PaymentType(String type_id, String name) {
        this.type_id = type_id;
        this.name = name;
    }

    public String getTypeId() {
        return type_id;
    }

    public String getName() {
        return name;
    }

}

