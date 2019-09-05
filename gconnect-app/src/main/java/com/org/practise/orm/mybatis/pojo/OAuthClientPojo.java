/*@Author Abdul-Rehman 
 *Abstract Overview: This file
 *Revision#1: 
 */
package com.org.practise.orm.mybatis.pojo;

import com.tb.gconnect.security.authentication.oauth2.template.OAuth2ClientI;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author abdul.rehman4 12th/07/2019
  * @version 1.0
 * @since v1.0
 * This class represents and stores the information about the authentication
 * client which is authenticated by WebNMS. This class persist the Information
 * about OAuth client in Data Base. This POJO is used.
 * whole information persisted by this class is stored in the plugged in data
 * base
 */
public class OAuthClientPojo implements OAuth2ClientI, Serializable {
	private static final long serialVersionUID = 1L;

	private String clientId; // Mandatory
	private String secret;// Mandatory
	private String owner;// The producer (OPTIONAL)
	private Map<String, String> attributes;// client attributes
														// (OPTIONAL)

	/**
	 * Gets client Id
	 * 
	 * @return clientId or NULL
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * sets client Id
	 * 
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * Sets secret String
	 * 
	 * @param secret
	 *            secret String
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * Sets owner String
	 * 
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Sets attributes
	 * 
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public OAuthClientPojo(String clientId, String secret, String owner,
                           Map<String, List<String>> attributes) {
		this.clientId = clientId;
		this.secret = secret;
		this.owner = owner;
		this.attributes = getImmutableMap(attributes);
	}

	public OAuthClientPojo() {
	}

	private static Map<String, String> getImmutableMap(
			Map<String, List<String>> map) {
		final HashMap<String, String> newMap = new HashMap<String, String>();
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			newMap.put(entry.getKey(), entry.getValue().toString());
		}
		return newMap;
	}

	/**
	 * Gets client Id
	 * 
	 * @return Key or NULL
	 */
	@Override
	public String getId() {
		return this.clientId;
	}

	/**
	 * Gets OAuth Secret String
	 * 
	 * @return Secret or NULL
	 */
	@Override
	public String getSecret() {
		return secret;
	}

	/**
	 * Returns identifier of owner of this client - i.e. who registered the
	 * client .
	 * 
	 * @return owner String or null
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Returns additional attributes associated with the client (e.g. name, URI,
	 * description, etc.)
	 * 
	 * @return name-values pairs of additional attributes
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * Checks in role status
	 * 
	 * @return true or false
	 */
	@Override
	public boolean isInRole(String role) {
		return false;
	}

}
