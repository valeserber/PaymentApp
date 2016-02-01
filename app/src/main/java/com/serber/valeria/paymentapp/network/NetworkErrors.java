package com.serber.valeria.paymentapp.network;

public enum NetworkErrors {

    HTTP_BAD_REQUEST("Bad request", 400),
    HTTP_UNAUTHORIZED("Unauthorized", 401),
    HTTP_NOT_FOUND("Not found", 404),
    UNKNOWN_ERROR("Unknown error", -1);

    private String errorType;
    private final int errorCode;

    NetworkErrors(String errorType, int errorCode) {
        this.errorType = errorType;
        this.errorCode = errorCode;
    }

    public String getErrorType() {
        return errorType;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
