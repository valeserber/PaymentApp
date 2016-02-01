package com.serber.valeria.paymentapp.model;

import java.io.Serializable;

public class PaymentMethod implements Serializable {

    private String id;
    private String name;
    private String payment_type_id;
    private String status;
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

    public String getPaymentTypeId() {
        return payment_type_id;
    }

    public String getStatus() {
        return status;
    }

    public String getSecureThumbnail() {
        return secure_thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDeferredCapture() {
        return deferred_capture;
    }
}
