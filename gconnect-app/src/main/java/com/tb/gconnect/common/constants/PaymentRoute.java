package com.tb.gconnect.common.constants;

/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * Constant routes for @{@link com.tb.gconnect.controller.PushNotificationController}
 */
public interface PaymentRoute extends BASERoute {
    public static String TEST = "/payment/test";
    public static String PAYMENT = "/payment";
    public static String REFUND = "/payment/refund";
    public static String CANCEL = "/payment/cancel";
}
