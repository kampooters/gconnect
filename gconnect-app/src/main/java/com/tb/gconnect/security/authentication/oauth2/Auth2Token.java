/*@author Abdul-Rehman 
 *Abstract Overview: This file
 *Revision#1: 
 */
package com.tb.gconnect.security.authentication.oauth2;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

import com.tb.gconnect.security.authentication.oauth2.template.OAuth2ClientI;
import com.tb.gconnect.security.authentication.oauth2.template.OAuth2TokenI;

/**
 * This class represents and stores the information about the authentication
 * token. The stored information can be about access,refresh and authorize token
 * those are issued to specific client (unique client ID)
 */
public class Auth2Token implements OAuth2TokenI {
	private String accessToken;
	private String refreshToken;
	private String authorizeToken;
	private String clientId;
	private String callbackUrl;
	private Principal principal;
	private Set<String> roles;
	private Map<String, String> attribs;
	private long accessTokenCreationTime; // Set Access token creation time
											// while creating access token form
											// handling its expiry

	/**
	 * This constructor initializes the token
	 * 
	 * @param authorizeToken
	 *            authorize Token
	 * @param clientId
	 *            unique client Id
	 * @param callbackUrl
	 *            redirect url
	 * @param principal
	 *            token principals
	 * @param roles
	 *            Authorization roles if any exists
	 * @param attributes
	 *            Token attributes
	 */
	public Auth2Token(String authorizeToken, String clientId,
			String callbackUrl, Principal principal, Set<String> roles,
			Map<String, String> attributes) {
		this.authorizeToken = authorizeToken;
		this.clientId = clientId;
		this.callbackUrl = callbackUrl;
		this.principal = principal;
		this.roles = roles;
		this.attribs = attributes;
		this.accessTokenCreationTime = System.currentTimeMillis();;
	}

	/** The detailed comment please @see {@link OAuth2TokenI} */
	public String getClientId() {
		return clientId;
	}

	/** The detailed comment please @see {@link OAuth2TokenI} */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/** The detailed comment please @see {@link OAuth2TokenI} */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
		this.accessTokenCreationTime = System.currentTimeMillis(); // set access
																	// token
																	// creation
																	// time to
																	// be used
																	// for
																	// expiration
	}

	/** The detailed comment please @see {@link OAuth2TokenI} */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	/** The detailed comment please @see {@link OAuth2TokenI}*/public String getToken() {
		return this.accessToken;
	}

	/** The detailed comment please @see {@link OAuth2TokenI} */
	public String getAccessToken() {
		return this.accessToken;
	}

	/** The detailed comment please @see {@link OAuth2TokenI} */
	public String getRefreshToken() {
		return this.refreshToken;
	}

	@Override
	/** The detailed comment please @see {@link OAuth2TokenI}*/public String getAuthorizeToken() {
		return authorizeToken;
	}

	public void setAuthorizeToken(String authorizeToken) {
		this.authorizeToken = authorizeToken;
	}

	@Override
	/** The detailed comment please @see {@link OAuth2TokenI}*/public OAuth2ClientI getClient() {
		return null;
	}

	@Override
	/** The detailed comment please @see {@link OAuth2TokenI}*/
	public Map<String, String> getAttributes() {
		return attribs;
	}

	@Override
	/** The detailed comment please @see {@link OAuth2TokenI}*/public Principal getPrincipal() {
		return principal;
	}

	@Override
	/** The detailed comment please @see {@link OAuth2TokenI}*/public boolean isInRole(
			String role) {
		return roles.contains(role);
	}

	/** The detailed comment please @see {@link OAuth2TokenI} */
	public String getCallbackUrl() {
		return callbackUrl;
	}

	/** The detailed comment please @see {@link OAuth2TokenI} */
	public long getAccessTokenCreationTime() {
		return accessTokenCreationTime;
	}

	/** The detailed comment please @see {@link OAuth2TokenI} */
	public void setAccessTokenCreationTime(long accessTokenCreationTime) {
		this.accessTokenCreationTime = accessTokenCreationTime;
	}

	@Override
	/** The detailed comment please @see {@link OAuth2TokenI}*/public OAuth2TokenI getOAuthToken(
			String clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	/** The detailed comment please @see {@link OAuth2TokenI} */
	public Set<String> getRoles() {
		return roles;
	}

	/** The detailed comment please @see {@link OAuth2TokenI} */
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}