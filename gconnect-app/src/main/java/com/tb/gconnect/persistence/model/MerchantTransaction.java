package com.tb.gconnect.persistence.model;

public class MerchantTransaction {
    private float amount;
    private String currency;
    private String merchant;
    private String message;

    private String merchant_id;
    private String payment_req_id;

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getPayment_req_id() {
        return payment_req_id;
    }

    public void setPayment_req_id(String payment_req_id) {
        this.payment_req_id = payment_req_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }
}