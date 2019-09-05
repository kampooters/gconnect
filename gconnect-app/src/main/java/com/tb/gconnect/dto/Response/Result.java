package com.tb.gconnect.dto.Response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Abdulrehman
 * represents result
 */
@ApiModel(value = "Result", description = "Represents Result message")
public class Result {

    @ApiModelProperty(name = "resultCode", value = "Shows result like SUCCESS or FAIL", required = true)
    private String resultCode = "SUCCESS";

    @ApiModelProperty(name = "resultStatus", value = "Shows result status like S or F", required = true)
    private String resultStatus = "S";

    @ApiModelProperty(name = "resultMessage", value = "Shows result message like success or message", required = true)
    private String resultMessage = "success";

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
