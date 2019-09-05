package com.tb.gconnect.filter;


import com.tb.gconnect.common.constants.BASERoute;
import com.tb.gconnect.logger.LogManager;
import com.tb.gconnect.security.authentication.AuthenticationManager;
import com.tb.gconnect.security.authentication.OAuthMetaDataHandlerI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * The all requests are intercepted at this filterand authenticated. If user is not authorized then un-authenticated response
 * is sent back. The authentication and test resources are whitelisted for authentication
 */

@Component
@Order(1)
public class AuthFilter extends LogManager implements Filter  {

    public AuthFilter(){
        super(AuthFilter.class);
    }

    @Autowired
    OAuthMetaDataHandlerI dbHandler;
    private void init(){
        try{
            AuthenticationManager.init(dbHandler);
        }catch (Exception e){
            error(e.getMessage(),e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        init();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if(req.getRequestURI().indexOf(BASERoute.BASE_ROUTE) != -1 && req.getRequestURI().indexOf("/test") == -1&& req.getRequestURI().indexOf("/callback") == -1) {
            if(!req.getRequestURI().contains(BASERoute.BASE_ROUTE+"/auth")) {
                String accessToken = req.getHeader("Authorization");
                String clientId = req.getHeader("ClientId");
                if(Boolean.FALSE.equals(AuthenticationManager.authenticateByAccessToken(clientId, accessToken))) {
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalid or expired.");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
        return;
    }

}