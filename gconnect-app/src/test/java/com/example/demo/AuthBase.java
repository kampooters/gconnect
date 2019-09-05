package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.gconnect.dto.AuthResponseDTO;
import com.tb.gconnect.persistence.authplugin.AuthDatabaseHandler;
import com.tb.gconnect.persistence.authplugin.OAuthClientPojo;
import com.tb.gconnect.persistence.service.impl.AuthService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class AuthBase {

    protected static final String clientId = "testing@123";
    protected static final String secret = "Testing123@";
    protected static AuthResponseDTO authResponseDTO;
    @Autowired
    AuthService authService;
    @Autowired
    private AuthDatabaseHandler authDatabaseHandler;

    @Before
    public void registerAndGetAccessToken() {
        authResponseDTO = (AuthResponseDTO) authService.registerClient(clientId, secret);
        if (authResponseDTO.getCode() != 200) {
            authResponseDTO = (AuthResponseDTO) authService.getAccessToken(clientId, secret);
        }
    }


    @Test
    public void _performCleanup() {
        OAuthClientPojo oAuthClientPojo = new OAuthClientPojo();
        oAuthClientPojo.setClientId(authResponseDTO.getClientId());
        authDatabaseHandler.deleteClient(oAuthClientPojo);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AuthResponseDTO getAuthResponseDTO() {
        return authResponseDTO;
    }
}
