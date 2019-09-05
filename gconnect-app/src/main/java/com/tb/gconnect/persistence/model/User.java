package com.tb.gconnect.persistence.model;

public class User {
    private String msisdn;
    private String pin;
    private String otp;
    private String message;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private MerchantTransaction transactionDetail;

    public MerchantTransaction getTransactionDetail() {
        return transactionDetail;
    }

    
    public void setTransactionDetail(MerchantTransaction transactionDetail) {
        this.transactionDetail = transactionDetail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "User{" +
                "msisdn='" + msisdn + '\'' +
                ", pin='" + pin + '\'' +
                ", otp='" + otp + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}