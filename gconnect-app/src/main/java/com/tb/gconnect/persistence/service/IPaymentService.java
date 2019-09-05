package com.tb.gconnect.persistence.service;

import com.tb.gconnect.dto.Response.ResponseDTO;

/**
 * @author abdul.rehman4 12th/07/2019
  * @version 1.0
 * @since v1.0
 * {@link IPaymentService} provides interface to define the {@link com.tb.gconnect.controller.PaymentController} services
 */
public interface IPaymentService {

    ResponseDTO refund(Object object);
    ResponseDTO cancel(Object object);
    ResponseDTO transfer(Object object);


}
