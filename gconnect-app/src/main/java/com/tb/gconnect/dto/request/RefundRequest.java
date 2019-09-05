package com.tb.gconnect.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;


/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * {@link RefundRequest} represents the request data received at the POS/PUT requests for REFUND payment request. Connect routes the refund requst to PSP. Under normal circumstance, refund which is routed to PSP will not fail.
 * Rest of the documentation is covered by swager
 */
@ApiModel(value = "Refund Request Body", description = "Connect routes the refund requst to PSP. Under normal circumstance, refund which is routed to PSP will not fail. ")
public class RefundRequest extends PaymentRequest {


    @ApiModelProperty(name = "refundAmount", value = "describes the 100JPY that PSP should pay out.",  required = true)
    @Valid
    private Amount refundAmount;

    @ApiModelProperty(name = "refundQuote", value = "refundQuote is the exchange rate between refundAmount and refundFromAmount",  required = false)
    @Valid
    private Quote refundQuote;

    @ApiModelProperty(name = "refundFromAmount", value = "refundFromAmount describes the 1000KRW that PSP should receive",  required = false)
    @Valid
    private Amount refundFromAmount;




    public Amount getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Amount refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Amount getRefundFromAmount() {
        return refundFromAmount;
    }

    public void setRefundFromAmount(Amount refundFromAmount) {
        this.refundFromAmount = refundFromAmount;
    }

    public Quote getRefundQuote() {
        return refundQuote;
    }

    public void setRefundQuote(Quote refundQuote) {
        this.refundQuote = refundQuote;
    }
}
