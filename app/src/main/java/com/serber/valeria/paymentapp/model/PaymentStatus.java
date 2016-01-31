package com.serber.valeria.paymentapp.model;

public enum PaymentStatus {

    ACTIVE("active"),
    DEACTIVE("deactive"),
    TEMPORALLY_DEACTIVE("temporally_deactive");

    private String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
