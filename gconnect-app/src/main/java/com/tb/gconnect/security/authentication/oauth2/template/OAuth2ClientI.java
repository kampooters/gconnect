/*@author Abdul-Rehman
 *Abstract Overview: This file represents the structure for OAuth2 Client
 *Revision#1: 
 */
package com.tb.gconnect.security.authentication.oauth2.template;

import java.util.Map;

/**
 * @author Abdul-Rehman
 * 
 *         This Interface represents the structure of a registered OAuth2.0
 *         Client. The whole information for OAuth2ClientI will be represented
 *         by concrete implementation of this interface
 */
public interface OAuth2ClientI {
	/**
	 * Returns OAuth2.0 Client id. Which is unique for every user. Id will
	 * remain constant and unique for the whole life of OAuth2.0 Client.
	 * OAuth2.0 Client will be identified on the basis of this key
	 * 
	 * @return OAuth2.0 Client key/NULL
	 */
	public abstract String getId();

	/**
	 * Returns OAuth2.0 Client secret. Will be unique for every OAuth2.0 Client
	 * 
	 * @return OAuth2.0 Client secret
	 */
	public abstract String getSecret();

	/**
	 * Returns a boolean indicating whether this OAuth2.0 Client is authorized
	 * for the specified logical "role". When the oauth filter verifies the
	 * request and no access token is provided (2-legged oauth), it sets the
	 * OAuth2.0 Client object to the security context which then delegates
	 * javax.ws.rs.core.SecurityContext#isUserInRole(String) to this
	 * method.
	 * 
	 * @param role
	 *            a {@code String} specifying the name of the role
	 * 
	 * @return a {@code boolean} indicating whether this token is authorized for
	 *         a given role
	 */
	public abstract boolean isInRole(String role);

	/**
	 * Returns identifier of owner of this OAuth2.0 Client - i.e. who registered
	 * the OAuth2.0 Client.
	 * 
	 * @return owner or NULL
	 */
	public String getOwner();

	/**
	 * Returns additional attributes associated with the OAuth2.0 Client (e.g.
	 * name, URI, description, etc.)
	 * 
	 * @return name-values pairs of additional attributes or NULL
	 */
	public Map<String, String> getAttributes();

}
