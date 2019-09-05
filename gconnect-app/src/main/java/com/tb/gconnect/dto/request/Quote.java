package com.tb.gconnect.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * {@link Quote}  refundQuote is the exchange rate between refundAmount and refundFromAmount
 * Rest of the documentation is covered by swager
 */
@ApiModel(value = "Amount", description = "Reprsents refundQuote is the exchange rate between refundAmount and refundFromAmount")
public class Quote {
    @Size(min=1, message = "quoteId is invalid")
    @ApiModelProperty(name = "quoteId", value = "quoteId id",required = false)
    private long quoteId;

    @ApiModelProperty(name = "currency", value = "quoteCurrencyPair reprsents currency pair like JPY/KRW etc",required = false)
    private String quoteCurrencyPair;

    @Size(min=1, message = "quotePrice is invalid")
    @ApiModelProperty(name = "quotePrice", value = "quotePrice represent quote price like 10.0000",required = false)
    private float quotePrice;

    public long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(long quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuoteCurrencyPair() {
        return quoteCurrencyPair;
    }

    public void setQuoteCurrencyPair(String quoteCurrencyPair) {
        this.quoteCurrencyPair = quoteCurrencyPair;
    }

    public float getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(float quotePrice) {
        this.quotePrice = quotePrice;
    }
}
