package com.tb.gconnect.controller;

import com.tb.gconnect.common.constants.*;
import com.tb.gconnect.dto.Response.RefundResonseDTO;
import com.tb.gconnect.dto.Response.ResponseDTO;
import com.tb.gconnect.dto.Response.Result;
import com.tb.gconnect.dto.request.PaymentRequest;
import com.tb.gconnect.dto.request.RefundRequest;
import com.tb.gconnect.logger.LogManager;
import com.tb.gconnect.persistence.service.impl.PaymentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Abdulrehman
 * {@link PaymentController} exposes the endpoints to payement, refund and cancel
 */

@Api( description = "Payment APIs", value = "The APIs exposed for Payment, Refund and cancel the transaction")
@RestController
@RequestMapping(BASERoute.BASE_ROUTE)
public class PaymentController extends LogManager {


    public PaymentController(){
        super(PaymentController.class);
    }

    @Autowired
    private PaymentService service;

    @GetMapping(path = PaymentRoute.TEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO test() {
        requestReceived(BASERoute.BASE_ROUTE+PaymentRoute.TEST, null );
        ResponseDTO resp =  new ResponseDTO();

        Result result = new Result();
            result.setResultCode("Test");
            result.setResultStatus("T");
            result.setResultMessage("test");
            resp.setResult(result);
        requestServed(BASERoute.BASE_ROUTE+PaymentRoute.TEST, resp);
        return resp;
    }

    @ApiOperation(value = "Refund Payment", notes = "Api provides interface to refund specific amount paid earlier" +
            "If the user has paid some amount earlier and wants to refund it then this API will facilitate to refund that money" +
            "Connect (AliPay) routes the refund requst to PSP (EasyPaisa). Under normal circumstance, refund which is routed to PSP will not fail.")
    @ApiResponse(code = 200, message = "Refund API response")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "oAuth access token, The authentication token will always will come in header", paramType = "header"),
                    @ApiImplicitParam(name = "ClientId", value = "Client id that was used at the time of registering.", paramType = "header")})

    @PostMapping(PaymentRoute.REFUND)
    public RefundResonseDTO refund(@Valid @RequestBody RefundRequest reqObj) {
        requestReceived(BASERoute.BASE_ROUTE+PaymentRoute.REFUND, reqObj);
        RefundResonseDTO resp =  (RefundResonseDTO)service.refund(reqObj);
        requestServed(BASERoute.BASE_ROUTE+PaymentRoute.REFUND, resp);
        return resp;

    }


    @ApiOperation(value = "Cancel Payment", notes = "Api provides interface to cancel specific amount paid earlier" +
            "If the user has paid some amount earlier and wants to refund it then this API will facilitate to refund that money" +
            "Connect (AliPay) routes the refund requst to PSP (EasyPaisa). Under normal circumstance, refund which is routed to PSP will not fail.")
    @ApiResponse(code = 200, message = "Cancel Transaction API response")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "oAuth access token, The authentication token will always will come in header", paramType = "header"),
                    @ApiImplicitParam(name = "ClientId", value = "Client id that was used at the time of registering.", paramType = "header")})

    @PostMapping(PaymentRoute.CANCEL)
    public ResponseDTO cancelTransaction(@Valid @RequestBody PaymentRequest reqObj) {
        requestReceived(BASERoute.BASE_ROUTE+PaymentRoute.CANCEL, reqObj);
        ResponseDTO resp =  service.cancel(reqObj);
        requestServed(BASERoute.BASE_ROUTE+PaymentRoute.CANCEL, resp);
        return resp;

    }

}
