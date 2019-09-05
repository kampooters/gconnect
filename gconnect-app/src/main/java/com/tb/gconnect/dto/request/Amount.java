package com.tb.gconnect.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * {@link Amount} Reprsents the amount and currency
 * Rest of the documentation is covered by swager
 */
@ApiModel(value = "Amount", description = "Reprsents the amount and currency")
public class Amount {
    @Size(min=1, message = "value = value is invalid")
    @ApiModelProperty(name = "value", value = "value represents the amount value, its always greater than 0",required = true)
    private long value;

    @Size(min=1, message = "currency cann't be empty")
    @ApiModelProperty(name = "currency", value = "currency reprsents currency like PKR etc",required = true)
    private String currency;


    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
