package com.tb.gconnect.controller;

import javax.validation.Valid;

import com.tb.gconnect.common.constants.BASERoute;
import com.tb.gconnect.persistence.model.MerchantTransaction;
import com.tb.gconnect.persistence.model.User;
import com.tb.gconnect.persistence.service.IPaymentService;
import com.tb.gconnect.persistence.service.impl.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Api( description = "Payment APIs", value = "The APIs exposed for Payment, Refund and cancel the transaction")
@Controller
public class MerchantAuthController {
//    @Autowired
//    private LocaleMessageSource localeMessageSource;

    @Autowired
    private MessageSource messageSource;




    @Autowired
    private PaymentService service;

    @ApiOperation(value = "Callback URL", notes = "Will return a callback URL, which will further redirect to user authentication page")
    @GetMapping("/callback")
    public Object callback(User user, BindingResult result, Model model,  @RequestParam String merchant_id, @RequestParam String payment_req_id,
                           @RequestParam String amount, @RequestParam String currency) {


        user = new User();
        MerchantTransaction transaction = new MerchantTransaction();
       transaction.setMerchant_id(merchant_id);
       transaction.setPayment_req_id(payment_req_id);
       if(amount.isEmpty()){
           transaction.setAmount(0);
       }else{
           transaction.setAmount(Float.valueOf(amount));
       }


       transaction.setCurrency(currency);
//       user.setMessage(payment_req_id);
       user.setKey(payment_req_id);
       user.setTransactionDetail(transaction);
       model.addAttribute("user", user);
       String messsae = service.saveCallBackInfo(user);

       if(!messsae.isEmpty()){
           model.addAttribute("message", messsae);
           return "error";
       }
        return "index";


    }

    @PostMapping("/pin/verification")
    public String pinVerification(@Valid User user, BindingResult result, Model model) {
        if(user.getMsisdn().equals("1111") && user.getPin().equals("1111")){
            User storedUser = service.getUser(user.getKey());
            storedUser.setMsisdn(user.getMsisdn());
            storedUser.setPin(user.getPin());
            service.saveUser(storedUser);
            model.addAttribute(storedUser);
            return "otp";
        }
        user.setMessage("PIN and MSSISDN does not match");
        model.addAttribute("user",user);
        return "index";

    }

    @PostMapping("/otp/verification")
    public String otpVerification(@Valid User user, BindingResult result, Model model, MerchantTransaction transaction) {
        if(user.getOtp().equals("1111")){
            User storedUser = service.getUser(user.getKey());
            storedUser.setMsisdn(user.getMsisdn());
            storedUser.setPin(user.getPin());
            service.saveUser(storedUser);
            transaction = storedUser.getTransactionDetail();
            transaction.setMerchant("Uber");
            model.addAttribute("transaction", transaction);
            return "transaction";
        }
        user.setMessage("OTP does not match");
        model.addAttribute(user);
        return "otp";
    }

//    @RequestMapping(value="/transaction", method=RequestMethod.POST, params="action=transaction")
    @PostMapping("/transaction")
    public String transaction(MerchantTransaction transaction, BindingResult result, Model model) {

            transaction.setMessage("Successful Transaction");
            model.addAttribute("transaction",transaction);
            return "result";

    }
//    @RequestMapping(value="/transaction", method=RequestMethod.POST, params="action=cancel")
//    public String cancelTransaction(BindingResult result, Model model, MerchantTransaction transaction) {
//        transaction.setMessage("Transaction is cancelled");
//        model.addAttribute(transaction);
//        return "result";
//    }

//    @GetMapping("/signup")
//    public String showSignUpForm(User user) {
//        return "index__2";
//    }

//
//    @PostMapping("/adduser")
//    public String addUser(@Valid User user, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "add-user";
//        }
////        userRepository.save(user);
////        model.addAttribute("users", userRepository.findAll());
//        return "index";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String showUpdateForm(@PathVariable("id") long id, Model model) {
//        //User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        User user = new User("abdul","a@a.com");
//        model.addAttribute("user", user);
//        return "update-user";
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
////        if (result.hasErrors()) {
////            user.setId(id);
////            return "update-user";
////        }
////
////        userRepository.save(user);
//         user = new User("abdul","a@a.com");
//        List<User> list = new ArrayList<>();
//        list.add(user);
//        model.addAttribute("users", list);
//        return "index";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteUser(@PathVariable("id") long id, Model model) {
////        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
////        userRepository.delete(user);
//       User user = new User("abdul","a@a.com");
//        List<User> list = new ArrayList<>();
//        list.add(user);
//        model.addAttribute("users", list);
////        model.addAttribute("users", userRepository.findAll());
//        return "index";
//    }

}
