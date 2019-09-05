package com.tb.gconnect.common.constants;


/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * {@link StatusEnm} reprents the statuses for the notification either notification is delivered to
 * target device or not
 *  */
public enum StatusEnm {
    PERSISTED("PERSISTED", "Persisted to database"),
    ENQUED("ENQUED", "Persisted to database"),
    SENT_TO_SMSC("SENT_TO_SMSC", "SMS sent to SMSc"),
    SMS_DELIVERED("SMS_DELIVERED", "Delivered to target MSISDN"),
    SENT_TO_FIREBASE("SENT_TO_FIREBASE", "Sent to Firebase"),
    DELIVERED_TO_APP("DELIVERED_TO_APP", "Push notification is delivered to App"),
    ERROR("ERROR", "Error during processing");
    private String msgCd;
    private String msgInfo;

    private StatusEnm(String msgCd, String msgInfo) {
        this.msgCd = msgCd;
        this.msgInfo = msgInfo;
    }

    public String getMsgCd() {
        return msgCd;
    }

    public String getMsgInfo() {
        return msgInfo;
    }
};
