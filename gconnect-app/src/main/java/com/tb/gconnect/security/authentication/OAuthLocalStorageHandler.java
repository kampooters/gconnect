/*@Author Abdul-Rehman
 *Abstract Overview: This file
 *Revision#1:
 */
package com.tb.gconnect.security.authentication;

import com.tb.gconnect.persistence.authplugin.OAuthClientPojo;
import com.tb.gconnect.security.authentication.oauth2.template.OAuth2ClientI;
import com.tb.gconnect.security.authentication.oauth2.template.OAuth2TokenI;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Abdul-Rehman | TMB Inc.
 * <p>
 * This class handles the OAuth variable locally (Run time). The scope
 * of the managed data is equal to the life cycle of the Application.
 * Once the application is closed the whole data will be lost
 */
public class OAuthLocalStorageHandler implements OAuthMetaDataHandlerI {
    private static final ConcurrentHashMap<String, OAuth2ClientI> clientByClientId = new ConcurrentHashMap<String, OAuth2ClientI>();
    private static final ConcurrentHashMap<String, OAuth2TokenI> tokenByClientId = new ConcurrentHashMap<String, OAuth2TokenI>();

    /**
     * For detailed comment please @see {@link OAuthMetaDataHandlerI}
     **/
    @Override
    public OAuth2ClientI getClientByKey_DB(String clientKey) {
        return clientByClientId.get(clientKey);
    }

    /**
     * For detailed comment please @see {@link OAuthMetaDataHandlerI}
     **/
    @Override
    public OAuth2ClientI insertClientByKey_DB(OAuth2ClientI client) {
        return clientByClientId.put(client.getId(), client);
    }

    /**
     * For detailed comment please @see {@link OAuthMetaDataHandlerI}
     **/
    @Override
    public Collection<OAuth2ClientI> getClientList() {
        return clientByClientId.values();
    }

    /**
     * For detailed comment please @see {@link OAuthMetaDataHandlerI}
     **/
    @Override
    public OAuth2TokenI getOAuthTokenByKey_DB(String clientKey) {
        return tokenByClientId.get(clientKey);
    }

    /**
     * For detailed comment please @see {@link OAuthMetaDataHandlerI}
     **/
    @Override
    public Collection<OAuth2TokenI> getOAuthTokenList() {
        return tokenByClientId.values();
    }

    /**
     * For detailed comment please @see {@link OAuthMetaDataHandlerI}
     **/
    @Override
    public boolean insertOrUpdateOAuthToken_DB(String clientKey,
                                               OAuth2TokenI token) {
        tokenByClientId.put(clientKey, token);
        return true;
    }

    @Override
    public void deleteClient(OAuthClientPojo oAuthClientPojo) {

    }

}
