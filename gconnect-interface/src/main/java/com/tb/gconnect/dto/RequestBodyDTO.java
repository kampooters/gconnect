package com.tb.gconnect.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.sql.Timestamp;

/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * {@link RequestBodyDTO} represents the request data received at the POS/PUT requests
 * Rest of the documentation is covered by swager
 */
@ApiModel(value = "Request Body", description = "The POSTSS data required to process the notifications")
public class RequestBodyDTO {

    @Size(min=1, message = "compaign_id = Compaign is invalid")
    @ApiModelProperty(name = "compaign_id", value = "compaign id for which the notifications are processed, it always will be integral value, its always greater than 0",required = false)
    private String compaign_id;

    @Size(min=1, message = "app_id = Application is invalid")
    @ApiModelProperty(name = "app_id", value = "app_id; Application id represents any the Callee Application",required = false)
    private String app_id;

    @Size(min=1, max = 100, message = "reference_id = Reference ID is invalid")
    @ApiModelProperty(name = "reference_id", value = "reference_id; Client can use this ID as reference to refer any specific message",required = false)
    private String reference_id;

    @ApiModelProperty(name = "schedule_date", value = "Optional, Timestamp if you want to schedule the notifications" +
            ".Example  \"schedule_date\": {\n" +
            "    \"date\": 0,\n" +
            "    \"day\": 0,\n" +
            "    \"hours\": 0,\n" +
            "    \"minutes\": 0,\n" +
            "    \"month\": 0,\n" +
            "    \"nanos\": 0,\n" +
            "    \"seconds\": 0,\n" +
            "    \"time\": 0,\n" +
            "    \"timezoneOffset\": 0,\n" +
            "    \"year\": 0\n" +
            "  }" +
            "" +
            ".The above object represents to a Long number for example 1234, here 1 is date , 2 is day and so on ", required = false)
    private Timestamp schedule_date;

    @ApiModelProperty(name = "expiry_date", value = "Timestamp on which the message will expire and deleted from Database" +
            ".Example  \"expiry_date\": {\n" +
            "    \"date\": 0,\n" +
            "    \"day\": 0,\n" +
            "    \"hours\": 0,\n" +
            "    \"minutes\": 0,\n" +
            "    \"month\": 0,\n" +
            "    \"nanos\": 0,\n" +
            "    \"seconds\": 0,\n" +
            "    \"time\": 0,\n" +
            "    \"timezoneOffset\": 0,\n" +
            "    \"year\": 0\n" +
            "  }" +
            "" +
            ".The above object represents to a Long number for example 1234, here 1 is date , 2 is day and so on ", required = true)
    private Timestamp expiry_date;

    @Min(value = 1, message = "end_window; cannt be less than 1 ")
    @Max(value = 24, message = "end_window; cannt be greater than 24 ")
    @ApiModelProperty(name = "start_window", value = "start_window; Day hour to send the notifications", required = true)
    private int start_window;

    @Min(value = 1, message = "end_window; cannt be less than 1 ")
    @Max(value = 24, message = "end_window; cannt be greater than 24 ")
    @ApiModelProperty(name = "end_window", value = "end_window; Day hour till the notifications can be sent", required = true)
    private int end_window;


    @Min(value = 1, message = "priority; cannt be less than 1 ")
    @Max(value = 10, message = "priority; cannt be greater than 10 ")
    @ApiModelProperty(name = "priority", value = "priority; 1-10 defines the priority less to high", required = false)
    private int priority;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

    public Timestamp getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Timestamp expiry_date) {
        this.expiry_date = expiry_date;
    }

    public int getStart_window() {
        return start_window;
    }

    public void setStart_window(int start_window) {
        this.start_window = start_window;
    }

    public int getEnd_window() {
        return end_window;
    }

    public void setEnd_window(int end_window) {
        this.end_window = end_window;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getCompaign_id() {
        return compaign_id;
    }

    public void setCompaign_id(String compaign_id) {
        this.compaign_id = compaign_id;
    }

    public Timestamp getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(Timestamp schedule_date) {
        this.schedule_date = schedule_date;
    }
}
