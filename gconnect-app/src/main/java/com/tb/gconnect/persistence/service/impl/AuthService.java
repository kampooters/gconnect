package com.tb.gconnect.persistence.service.impl;

import com.tb.gconnect.dto.*;
import com.tb.gconnect.logger.LogManager;
import com.tb.gconnect.security.authentication.OAuthMetaDataHandlerI;
import com.tb.gconnect.security.authentication.AuthenticationManager;
import com.tb.gconnect.security.authentication.oauth2.template.OAuth2ClientI;
import com.tb.gconnect.security.authentication.oauth2.template.OAuth2TokenI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * Provides implementation for {@link }
 */
@Service
public class AuthService extends  LogManager  {


    @Autowired
    OAuthMetaDataHandlerI dbHandler;
    private void init(){
        try{
            AuthenticationManager.init(dbHandler);
        }catch (Exception e){
            error(e.getMessage(),e);
        }
    }
    public AuthService(){
        super((AuthService.class));
    }


    /**
     * Registers oAuth client along secret
     * @param clientId unique id representing client
     * @param secret password
     * @return {@link HttpResonseDTO}
     */
    public HttpResonseDTO registerClient(String clientId,String secret) {
        AuthResponseDTO resp = new AuthResponseDTO();
        try {
            init();
            OAuth2ClientI client = AuthenticationManager.getConsumer(clientId);
            if(null == client) {
                AuthenticationManager.registerConsumer(clientId,secret,null);
                //here we returning the access token and other related data
                resp = (AuthResponseDTO)getAccessToken(clientId,secret);
            }else {
                resp.setCode(HttpStatus.ALREADY_REPORTED.value());
                resp.setError("Invalid Client Id or SECRET");
            }

        } catch (Exception e) {
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resp.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        return resp;

    }


    /**
     * Provides access token if user name and secret are OK
     * @param email unique id representing client
     * @param password password
     * @return {@link HttpResonseDTO}
     */
    public HttpResonseDTO getAccessToken(String email,String password) {

        AuthResponseDTO resp = new AuthResponseDTO();
        try {
            init();
            OAuth2ClientI client = AuthenticationManager.verifyConsumer(email,password);

            if (null == client ) {
                resp.setCode(HttpStatus.UNAUTHORIZED.value());
                resp.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            } else {
                OAuth2TokenI oAuthToken = AuthenticationManager.getAuthToken(email, password);
                resp.setAccessToken(oAuthToken.getAccessToken());
                resp.setRefreshToken(oAuthToken.getRefreshToken());
                resp.setAuthorizeToken(oAuthToken.getAuthorizeToken());
                resp.setClientId(oAuthToken.getClientId());
            }

        } catch (Exception e) {
            resp.setCode(HttpStatus.UNAUTHORIZED.value());
            resp.setError(e.getMessage());
        }
        return  resp;

    }



    /**
     * Provides access token if client id and refresh token are matched
     * @param clientId unique id representing client
     * @param refreshToken password
     * @return {@link AuthResponseDTO}
     */
    public Object getRefreshToken(String clientId, String refreshToken) {
        AuthResponseDTO resp = new AuthResponseDTO();
        try {
            init();
            OAuth2TokenI oAuthToken = AuthenticationManager.getAccessToken(clientId);
            //User reUser = regUserRepo.findByEmail(email);
            if (oAuthToken == null || !oAuthToken.getRefreshToken().equals(refreshToken)) {
                resp.setCode(HttpStatus.UNAUTHORIZED.value());
                resp.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            } else {
                oAuthToken = AuthenticationManager.getAuthToken(clientId, "SECRET");
                resp.setAccessToken(oAuthToken.getAccessToken());
                resp.setRefreshToken(oAuthToken.getRefreshToken());
                resp.setAuthorizeToken(oAuthToken.getAuthorizeToken());
                resp.setClientId(oAuthToken.getClientId());
            }

        } catch (Exception e) {
            resp.setCode(HttpStatus.UNAUTHORIZED.value());
            resp.setError(e.getMessage());
        }
        return  resp;
    }

}
