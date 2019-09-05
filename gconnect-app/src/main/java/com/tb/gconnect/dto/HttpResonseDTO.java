package com.tb.gconnect.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Abdulrehman
 * Works as parent response DTO for all types of responses sent to client
 */
@ApiModel(value = "ResponseDTO", description = "Represents the standard response provided by Notification REST endpoints")
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

    @ApiModelProperty(name = "timestamp", value = "Represents current timestamp",required = false)
    private Timestamp timestamp = new Timestamp(new Date().getTime());

    @ApiModelProperty(name = "message", value = "Message details in case of success or failure",required = false)
    private String message = "";

    @ApiModelProperty(name = "path", value = "URL for the  invoked http resource",required = true)
    private String path = "";

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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
