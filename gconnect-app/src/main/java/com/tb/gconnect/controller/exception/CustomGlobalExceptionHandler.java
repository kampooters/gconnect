package com.tb.gconnect.controller.exception;

import com.tb.gconnect.dto.Response.ResponseDTO;
import com.tb.gconnect.dto.Response.Result;
import com.tb.gconnect.security.authentication.OAuthConstant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * CustomGlobalExceptionHandler handles the exceptions thrown
 * before the endpoints invocation like in request body parsing and validation
 */
@ControllerAdvice
@Component
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles validation errors
     * @param ex @{@link MethodArgumentNotValidException}
     * @param headers @{@link HttpHeaders}
     * @param status @{@link HttpStatus}
     * @param request @{@link WebRequest}
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);
        ResponseDTO resp = new ResponseDTO();
        Result result = new Result();
        result.setResultMessage("fail");
        result.setResultCode("FAIL");
        result.setResultStatus("F");
        resp.setResult(result);


        return new ResponseEntity<>(resp, headers, status);

    }

    /**
     * Handles illegal argument exceptions
     * @param exception {@link ConstraintViolationException}
     * @return {@link Map}
     */
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(ConstraintViolationException exception) {
        return error(exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));
    }

    private Map error(Object message) {
        //changing message;
        String msg = OAuthConstant.FAILED_VALIDATION;
        Map<String, String> resp = new HashMap<>();
        resp.put("code", "400");
        resp.put("error", msg);
        return  resp;
        //return Collections.singletonMap("error", msg);
    }

}