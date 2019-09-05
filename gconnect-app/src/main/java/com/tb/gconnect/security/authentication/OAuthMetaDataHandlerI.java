/*@Author Abdul-Rehman
 *Abstract Overview: This file contains the structure to communicate with DB and OAuth Data
 *Revision#1: 
 */
package com.tb.gconnect.security.authentication;

import java.util.Collection;

import com.tb.gconnect.persistence.authplugin.OAuthClientPojo;
import com.tb.gconnect.security.authentication.oauth2.template.OAuth2ClientI;
import com.tb.gconnect.security.authentication.oauth2.template.OAuth2TokenI;

/**
 * @author Abdul-Rehman | TMB Inc.
 * 
 *         This Interface contains the method declarations to insert and
 *         retrieve the OAuth data like authorize, access and refresh tokens from
 *         data base or local storage. Custom implementations can be provided.
 *         Custom implementation can be provided according to the requirements
 *         This interface works as interface ADAPTER
 */
public interface OAuthMetaDataHandlerI {

	/**
	 * This method gets the {@link OAuth2ClientI} object by matching the client
	 * Id
	 * 
	 * @param clientId
	 *            Unique client Id
	 * @return {@link OAuth2ClientI} object OR NULL
	 */
	public OAuth2ClientI getClientByKey_DB(String clientId);

	/**
	 * This method inserts the {@link OAuth2ClientI} object in plugged in
	 * persistence source (Data base or Local storage)
	 * 
	 * @param client
	 *            client Object
	 * @return {@link OAuth2ClientI} object OR NULL
	 * */
	public OAuth2ClientI insertClientByKey_DB(OAuth2ClientI client);

	/**
	 * This method gets the {@link OAuth2TokenI} object by matching the client
	 * key from pluged in persistence source (Data base or Local storage)
	 * 
	 * @param clientId
	 *            Unique client Id
	 * @return {@link OAuth2TokenI} object OR NULL
	 * */
	public OAuth2TokenI getOAuthTokenByKey_DB(String clientId);

	/**
	 * This methods provides the available clients (registered)
	 * 
	 * @return {@link Collection} or NULL
	 */
	public Collection<OAuth2ClientI> getClientList();

	/**
	 * This methods provides the available tokens (registered)
	 * 
	 * @return {@link OAuth2TokenI}
	 */
	public Collection<OAuth2TokenI> getOAuthTokenList();

	/**
	 * This method first of all checks either {@link OAuth2TokenI} exists
	 * against the provided clientId. If exists then updates whether inserts as
	 * new entry
	 * 
	 * @param clientId
	 *            Unique client Id
	 * @param token
	 *            OAuth Token Object
	 * @return true or false
	 */
	public boolean insertOrUpdateOAuthToken_DB(String clientId,
                                               OAuth2TokenI token);

	void deleteClient(OAuthClientPojo oAuthClientPojo);
}
