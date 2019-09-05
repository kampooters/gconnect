package com.tb.gconnect.controller;


import com.tb.gconnect.common.constants.AuthRoute;
import com.tb.gconnect.common.constants.BASERoute;
import com.tb.gconnect.dto.AuthResponseDTO;
import com.tb.gconnect.dto.HttpResonseDTO;
import com.tb.gconnect.logger.LogManager;
import com.tb.gconnect.persistence.service.impl.AuthService;
import com.tb.gconnect.security.authentication.OAuthConstant;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

/**
 * @author Abdulrehman abdul
 * @author abdul 4/May/2018
 * @version 1.0 ContentAPIController holds the all APIs for user
 * analytics @revision-1 : Message structure changes recommended by
 * client team for unified rest interface to support the unfication.
 * The resulted response data: object will contain key name in key and
 * value object in value key. JSON FORMAT
 */
@Api(description = "Authentication APIs", value = "The APIs exposed related to oAuth authentication")
@RestController
@RequestMapping(BASERoute.BASE_ROUTE)
public class AuthController extends LogManager {

    @Autowired
    AuthService userService;

    public AuthController() {
        super(AuthController.class);
    }




    /**
     * @author Abdul Rehman
     *
     *
     * Aqeel Abbas -- Added password validation
     * @param clientId
     * @param secret
     * @return
     */
    @ApiOperation(value = "Register oAuth client", notes = "Client registration with client id (Its email) and secret (Its password)<br />" +
            "The secret is stored in database after encription and is secure." +
            "-- Password must include at least one upper case character (A-Z)<br />" +
            "-- Password must include at least one lower case character (a-z)<br />" +
            "-- Password must include at least one digit (0-9)<br />" +
            "-- Password must include at least one special character (Punctuation)<br />" +
            "-- Password should not start with a digit<br />" +
            "-- Password should not end with a special character")
    @ApiResponse(code = 200, message = "Register Client")
    @PostMapping(AuthRoute.REGISTER)
    public AuthResponseDTO registerClient(
            @ApiParam(value = "Its unique id normally representing email", required = true)
            @RequestHeader("client_id") String clientId,
            @ApiParam(value = "Represents password", required = true)
            @RequestHeader("secret") @Valid @Pattern(regexp = OAuthConstant.VALIDATEPASSWORD) String secret) {
        System.out.println("SECRET: "+secret);
        requestReceived(BASERoute.BASE_ROUTE + AuthRoute.REGISTER, null);
        AuthResponseDTO resp = (AuthResponseDTO)userService.registerClient(clientId, secret);
        requestServed(BASERoute.BASE_ROUTE + AuthRoute.REGISTER, resp);
        resp.setPath(BASERoute.BASE_ROUTE + AuthRoute.REGISTER);
        return resp;

    }

    @ApiOperation(value = "Get Access Token", notes = "This API provides access token." +
            "<br/>" +
            "Access tokens are the thing that applications use to make API requests on behalf of a user. The access token represents the authorization of a specific application to access specific parts of a user’s data.\n" +
            "<br/>" +
            "Access tokens must be kept confidential in transit and in storage. The only parties that should ever see the access token are the application itself, the authorization server, and resource server. The application should ensure the storage of the access token is not accessible to other applications on the same device. The access token can only be used over an https connection, since passing it over a non-encrypted channel would make it trivial for third parties to intercept.\n" +
            "<br/>" +
            "The token endpoint is where apps make a request to get an access token for a user. This section describes how to verify token requests and how to return the appropriate response and errors.")
    @ApiResponse(code = 200, message = "Access token provided")
    @PostMapping(AuthRoute.ACCESS_TOKEN)
    public AuthResponseDTO getAccessToken(
            @ApiParam(value = "Its unique id normally representing email", required = true)
            @RequestHeader("clientId") String email,

            @ApiParam(value = "Represents password", required = true)
            @RequestHeader("secret") String password) {
        requestReceived(BASERoute.BASE_ROUTE + AuthRoute.ACCESS_TOKEN, null);
        AuthResponseDTO resp = (AuthResponseDTO)userService.getAccessToken(email, password);
        requestServed(BASERoute.BASE_ROUTE + AuthRoute.ACCESS_TOKEN, resp);
        resp.setPath(BASERoute.BASE_ROUTE + AuthRoute.ACCESS_TOKEN);
        return resp;

    }

    @ApiOperation(value = "Get refresh Token", notes = "The Refresh Token grant type is used by clients to exchange a refresh token for an access token when the access token has expired.\n" +
            "\n" +
            "This allows clients to continue to have a valid access token without further interaction with the user.")
    @ApiResponse(code = 200, message = "Refresh token provided")
    @PostMapping(AuthRoute.REFRESH_TOKEN)
    public AuthResponseDTO authorize(
            @ApiParam(value = "Its unique id normally representing email", required = true)
            @RequestHeader("clientId") String clientId,
            @ApiParam(value = "Represents Refresh Token, for which you will get the new access token", required = true)
            @RequestHeader("refreshToken") String refreshToken) {
        requestReceived(BASERoute.BASE_ROUTE + AuthRoute.REFRESH_TOKEN, null);
        AuthResponseDTO resp = (AuthResponseDTO) userService.getRefreshToken(clientId, refreshToken);
        requestServed(BASERoute.BASE_ROUTE + AuthRoute.REFRESH_TOKEN, resp);
        resp.setPath(BASERoute.BASE_ROUTE + AuthRoute.REFRESH_TOKEN);
        return resp;
    }

}
