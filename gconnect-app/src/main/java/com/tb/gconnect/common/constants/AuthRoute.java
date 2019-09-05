package com.tb.gconnect.common.constants;

/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * {@link AuthRoute} Contant routes for @{@link com.tb.gconnect.controller.AuthController}
 */
public interface AuthRoute extends  BASERoute{
    public static String TEST = "/auth/test";
    public static String REGISTER = "/auth/register";
    public static String REGISTER_OAUTH_CLIENT = "/auth/register/client";
    public static String ACCESS_TOKEN = "/auth/get/accesstoken";
    public static String REFRESH_TOKEN = "/auth/get/refreshtoken";
}
