/*@author Abdul-Rehman
 *Revision#1: 
 */
package com.tb.gconnect.security.authentication.oauth2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tb.gconnect.security.authentication.oauth2.template.OAuth2ClientI;

/**
 * @about This represents and stores the information about the 
 *        oauth2.0 client which is authenticated by WebNMS_CE
 */
/** The detailed comment please @see {@link OAuth2ClientI} */
public class Auth2Client implements OAuth2ClientI {
	private String clientId;
	private String secret;
	private String owner;// The producer
	private Map<String, String> attributes;// oauth2.0 client
														// attributes

	/**
	 * this constructor initializes the client
	 * 
	 * @param clientId
	 *            OAuth2.0 client ID
	 * @param secret
	 *            OAuth2.0 secret. According to the FRC6749:(Resource Owner
	 *            password credential grant) specification it will represent the
	 *            client password
	 * @param owner
	 *            Resource Owner name
	 * @param attributes
	 *            Client's attributes if exists
	 */
	public Auth2Client(String clientId, String secret, String owner,
			Map<String, List<String>> attributes) {
		this.clientId = clientId;
		this.secret = secret;
		this.owner = owner;
		if(null != attributes){
			this.attributes = getImmutableMap(attributes);
		}

	}

	private static Map<String, String> getImmutableMap(
			Map<String, List<String>> map) {
		final Map<String, String> newMap = new HashMap<String, String>();
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			newMap.put(entry.getKey(), entry.getValue().toString());
		}
		return newMap;
	}

	@Override
	/** The detailed comment please @see {@link OAuth2ClientI} */
	public String getId() {
		return this.clientId;
	}

	@Override
	/** The detailed comment please @see {@link OAuth2ClientI} **/
	public String getSecret() {
		return secret;
	}

	/** The detailed comment please @see {@link OAuth2ClientI} */
	public String getOwner() {
		return owner;
	}

	/** The detailed comment please @see {@link OAuth2ClientI} */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	@Override
	/** The detailed comment please @see {@link OAuth2ClientI} */
	public boolean isInRole(String role) {
		return false;
	}

}
