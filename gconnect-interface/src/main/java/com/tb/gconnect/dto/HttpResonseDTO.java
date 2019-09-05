package com.tb.gconnect.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 *  Works as parent response DTO for all types of responses sent to client
 *  */
@ApiModel(value = "HttpResonseDTO", description = "Represents the standard response provided by Notification REST endpoints")
public class HttpResonseDTO {
    /**
     * Standard HTTP Code
     */
    @ApiModelProperty(name = "code", value = "http status codes like 200 for success",required = true)
    private int code = 200;
    /**
     * Have value if {@link HttpResonseDTO} code is not 200, error type
     */
    @ApiModelProperty(name = "error", value = "Have value if code is not 200, it provides the complete details about the error occured",required = true)
    private String error = "";


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
