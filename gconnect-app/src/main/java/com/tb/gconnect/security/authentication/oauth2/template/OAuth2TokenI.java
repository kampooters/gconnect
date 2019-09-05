/*@author Abdul-Rehman
 *Abstract Overview: This file represents the structure for OAuthToken
 *Revision#1: 
 */
package com.tb.gconnect.security.authentication.oauth2.template;

/**
 * @author Abdul-Rehman
 *
 *  This interface represents an OAuth token (i.e. access token or authorize token or Refresh).
 */

import java.security.Principal;
import java.util.Map;
import java.util.Set;

public interface OAuth2TokenI {
	/**
	 * Returns string representing the access token.
	 * 
	 * @return string representing the token OR NULL
	 */
	public abstract String getToken();

	/**
	 * Returns the token secret which is unique for each user.
	 * 
	 * @return token secret OR NULL
	 */
	public abstract String getAuthorizeToken();

	public abstract void setAuthorizeToken(String authorizeToken);

	/**
	 * Returns client issued for this token.
	 * 
	 * @return {@link OAuth2ClientI} or null
	 */
	public abstract OAuth2ClientI getClient();

	/**
	 * Returns additional custom attributes associated with the token.
	 * 
	 * @return immutable map of custom attributes or NULL
	 */
	public abstract Map<String, String> getAttributes();

	/**
	 * Returns a {@link Principal} object containing the name of
	 * the user the request containing this token is authorized to act on behalf
	 * of. When the oauth filter verifies the request with this token is
	 * properly authenticated, it injects this token into a security context
	 * which then delegates
	 * javax.ws.rs.core.SecurityContext#getUserPrincipal() to this
	 * method.
	 * 
	 * @return Principal corresponding to this token, or null if the token is
	 *         not authorized or NULL
	 */
	public abstract Principal getPrincipal();

	/**
	 * Returns a boolean indicating whether this token is authorized for the
	 * specified logical "role". When the oauth filter verifies the request with
	 * this token is properly authenticated, it injects this token into a
	 * security context which then delegates
	 * javax.ws.rs.core.SecurityContext#isUserInRole(String) to this
	 * method.
	 * 
	 * @param role
	 *            a {@code String} specifying the name of the role
	 * 
	 * @return a {@code boolean} indicating whether this token is authorized for
	 *         a given role or false
	 */
	public abstract boolean isInRole(String role);

	/**
	 * This method return clientId to which this token is associated
	 * 
	 * @return clientId if exists or NUll
	 */
	public abstract String getClientId();

	/**
	 * Sets the client Id
	 * 
	 * @param clientId
	 */
	public void setClientId(String clientId);

	/**
	 * Sets the OAuth access Token
	 * 
	 * @param accessToken
	 */
	public void setAccessToken(String accessToken);

	/**
	 * Sets the OAuth refresh Token
	 * 
	 * @param refreshToken
	 */
	public void setRefreshToken(String refreshToken);

	/**
	 * Gets the OAuth access Token
	 * 
	 * @return String or NULL
	 */
	public String getAccessToken();

	/**
	 * Gets the OAuth refresh Token
	 * 
	 * @return String or NULL
	 */
	public String getRefreshToken();

	/**
	 * Returns redirect URI for this token (applicable just to authorize tokens)
	 * 
	 * @return redirect URI or NULL
	 */
	public String getCallbackUrl();

	public long getAccessTokenCreationTime();

	/**
	 * Sets the Token creation time
	 * 
	 * @param accessTokenCreationTime
	 */
	public void setAccessTokenCreationTime(long accessTokenCreationTime);

	/**
	 * Gets the OAuth Token by matching the client Id
	 * 
	 * @param clientId
	 *            Unique client Id
	 * @return link OAuth2TokenI or Null
	 */
	public OAuth2TokenI getOAuthToken(String clientId);

	/**
	 * Returns the set of issued Roles if any available
	 * 
	 * @return set or NULL
	 */
	public Set<String> getRoles();

	/**
	 * Sets the set of issued roles
	 * 
	 * @param roles set
	 */
	public void setRoles(Set<String> roles);
}
