package com.tb.gconnect.persistence.service.impl;

import com.tb.gconnect.dto.Response.RefundResonseDTO;
import com.tb.gconnect.dto.Response.ResponseDTO;
import com.tb.gconnect.dto.Response.Result;
import com.tb.gconnect.logger.ILogManager;
import com.tb.gconnect.logger.LogManager;
import com.tb.gconnect.persistence.model.MerchantTransaction;
import com.tb.gconnect.persistence.model.User;
import com.tb.gconnect.persistence.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * Provides implementation for {@link IPaymentService}
 */
@Service
public class PaymentService implements IPaymentService {

private static Map<String, User> users = new HashMap();
//    private static final Logger logger = LogM.getLogger(NotificationService.class);
private static final ILogManager logger = new LogManager(PaymentService.class);
    @Autowired
    private MessageSource messageSource;

    Locale locale = new Locale("");





    @Override
    public ResponseDTO refund(Object object) {
        RefundResonseDTO respDTO = new RefundResonseDTO();
        Result result = new Result();
        try {
            result.setResultCode("SUCCESS");
            result.setResultStatus("S");
            result.setResultMessage("success");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            result.setResultCode("FAIL");
            result.setResultStatus("F");
            result.setResultMessage("fail");
        }
        respDTO.setResult(result);
        respDTO.setRefundId("7668768768768768687");
        return respDTO;
    }

    @Override
    public ResponseDTO cancel(Object object) {
        ResponseDTO respDTO = new ResponseDTO();
        Result result = new Result();
        try {
            result.setResultCode("SUCCESS");
            result.setResultStatus("S");
            result.setResultMessage("success");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            result.setResultCode("FAIL");
            result.setResultStatus("F");
            result.setResultMessage("fail");
        }
        respDTO.setResult(result);
//        respDTO.setAcquirerId("7668768768768768687");
//        respDTO.setPspId("7668768768768768687");
        return respDTO;
    }

    @Override
    public ResponseDTO transfer(Object object) {
        return refund(object);
    }

    public String saveCallBackInfo(User user){

        String message = "";
        Object [] obj = new Object[0];
        if(null == user || null == user.getTransactionDetail()){
            message =    messageSource.getMessage("transaction.invalid", obj, locale);
            logger.error(message);
            return  message;
        }

        MerchantTransaction transaction = user.getTransactionDetail();
        if(null == transaction.getPayment_req_id() || transaction.getPayment_req_id().isEmpty()){
            message =    messageSource.getMessage("payment_req_id.invalid", obj, locale);
            logger.error(message);
            return  message;
        }
        if(null == transaction.getMerchant_id() || transaction.getMerchant_id().isEmpty()){
            message =    messageSource.getMessage("merchant_id.invalid", obj, locale);
            logger.error(message);
            return  message;
        }
        if(0 >= transaction.getAmount()){
            message =   messageSource.getMessage("amount.invalid", obj, locale);
            logger.error(message);
            return  message;
        }
        if(null == transaction.getCurrency() || transaction.getCurrency().isEmpty()){
            message =   messageSource.getMessage("currency.invalid", obj, locale);
            logger.error(message);
            return  message;
        }
        saveUser(user);
      return  message;
    }

    public void saveUser(User user){
        users.put(user.getKey(), user);
    }
    public User getUser(String key){
        return users.get(key);
    }
}
