package com.tb.gconnect.dto.Response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Abdulrehman
 * Works as parent response DTO for all types of responses sent to client
 */
@ApiModel(value = "ResponseDTO", description = "Represents the standard response provided by gConnect REST endpoints")
public class ResponseDTO {
    @ApiModelProperty(name = "result", value = "Represents complete result message depicting the success or Fail case",required = true)
    @Valid
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
