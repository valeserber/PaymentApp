package com.serber.valeria.paymentapp.model;

public enum PaymentType {

    TICKET("ticket"),
    ATM("atm"),
    CREDIT_CARD("credit_card"),
    DEBIT_CARD("debit_card"),
    PREPAID_CARD("prepaid_card");

    private String type_id;

    PaymentType(String type_id) {
        this.type_id = type_id;
    }

    public String getTypeId() {
        return type_id;
    }
}

