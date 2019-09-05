/*@Author Abdul-Rehman
 *Abstract Overview: This file
 *Revision#1: 
 */
package com.tb.gconnect.persistence.authplugin;

import java.io.Serializable;
import java.security.Principal;
import java.util.Map;
import java.util.Set;

import com.tb.gconnect.security.authentication.oauth2.template.OAuth2ClientI;
import com.tb.gconnect.security.authentication.oauth2.template.OAuth2TokenI;

/**
 * @author abdul.rehman4 12th/07/2019
  * @version 1.0
 * @since v1.0
 * This is the sample POJO for Token to persist in the Data Base used
 */
public class OAuthTokenPojo implements OAuth2TokenI, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4028099321197947839L;
	private String accessToken;
	private String refreshToken;
	private String authorizeToken;
	private String clientId;
	private String callbackUrl;
	private Map<String, String> attributes;
	private long accessTokenCreationTime; // Set Access token creation time
											// while creating access token form
											// handling its expire

	public OAuthTokenPojo() {
	}

	/**
	 * This method sets authentication token attributes
	 */
	public OAuthTokenPojo(String accessToken, String refreshToken,
						  String authorizeToken, String clientId, long accessCreationTime,
						  String callbackUrl, Principal principal, Set<String> roles,
						  Map<String, String> attributes) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.authorizeToken = authorizeToken;
		this.clientId = clientId;
		this.accessTokenCreationTime = accessCreationTime;
		if (callbackUrl == null)
			callbackUrl = "";
		this.callbackUrl = callbackUrl;
		// this.principal = principal;
		// this.roles = roles;
		this.attributes = attributes;
	}

	public void setAuthorizeToken(String setAuthorizeToken) {
		this.authorizeToken = authorizeToken;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public Set<String> getRoles() {
		return null;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public void setRoles(Set<String> roles) {
		// this.roles = roles;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public String getClientId() {
		return clientId;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
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

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	/** Detailed Comment present in {@link OAuth2TokenI}*/public String getToken() {
		return this.accessToken;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public String getAccessToken() {
		return this.accessToken;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public String getRefreshToken() {
		return this.refreshToken;
	}

	@Override
	/** Detailed Comment present in {@link OAuth2TokenI}*/public String getAuthorizeToken() {
		return authorizeToken;
	}

	@Override
	/** Detailed Comment present in {@link OAuth2TokenI}*/public OAuth2ClientI getClient() {
		return null;
	}

	@Override
	/** Detailed Comment present in {@link OAuth2TokenI}*/
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public Map<String, String> setAttributes(Map<String, String> attrib) {
		return this.attributes = attrib;
	}

	@Override
	/** Detailed Comment present in {@link OAuth2TokenI}*/public Principal getPrincipal() {
		return null;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public Principal setPrincipal(Principal principal) {
		return null;
	}

	@Override
	/** Detailed Comment present in {@link OAuth2TokenI}*/public boolean isInRole(
			String role) {
		// return roles.contains(role);
		return false;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public String getCallbackUrl() {
		return callbackUrl;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public String setCallbackUrl(String callbackUrl) {
		return this.callbackUrl = callbackUrl;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public long getAccessTokenCreationTime() {
		return accessTokenCreationTime;
	}

	/** Detailed Comment present in {@link OAuth2TokenI} */
	public void setAccessTokenCreationTime(long accessTokenCreationTime) {
		this.accessTokenCreationTime = accessTokenCreationTime;
	}

	@Override
	/** Detailed Comment present in {@link OAuth2TokenI}*/public OAuth2TokenI getOAuthToken(
			String clientId) {
		// TODO Auto-generated method stub
		return null;
	}

}
