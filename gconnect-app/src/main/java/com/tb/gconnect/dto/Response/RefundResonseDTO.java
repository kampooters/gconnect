package com.tb.gconnect.dto.Response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author Abdulrehman
 * Works as parent response DTO for all types of responses sent to client
 */
@ApiModel(value = "RefundResonseDTO", description = "Represents the standard response provided by gConnect REST endpoints")
public class RefundResonseDTO extends ResponseDTO {
    @ApiModelProperty(name = "refundId", value = "Represents refund ID",required = true)
    private String refundId;

    @ApiModelProperty(name = "refundTime", value = "Represents refund date",required = true)
    private Date refundTime = new Date();

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }
}
