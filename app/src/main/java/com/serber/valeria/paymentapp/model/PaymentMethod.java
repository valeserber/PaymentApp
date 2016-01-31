package com.serber.valeria.paymentapp.model;

import java.io.Serializable;

public class PaymentMethod implements Serializable {

    private String id;
    private String name;
    private PaymentType payment_type_id;
    private PaymentStatus status;
    private String secure_thumbnail;
    private String thumbnail;
    private String deferred_capture;

    public PaymentMethod() {
        super();
    }

    public PaymentMethod(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PaymentType getPayment_type_id() {
        return payment_type_id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public String getSecure_thumbnail() {
        return secure_thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDeferred_capture() {
        return deferred_capture;
    }
}
