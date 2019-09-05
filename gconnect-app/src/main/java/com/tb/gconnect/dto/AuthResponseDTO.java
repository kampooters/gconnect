/*@Author Abdul-Rehman
 *Abstract Overview: This file
 *Revision#1: 
 */
package com.tb.gconnect.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * Represents the authentcation response for @{@link com.tb.gconnect.controller.AuthController}
 * Rest of the documentation is covered by swager
 */

@ApiModel(value = "AuthResponseDTO", description = "For authentication response")
public class AuthResponseDTO extends HttpResonseDTO implements Serializable {

	@ApiModelProperty(name = "accessToken", value = "represents the oAuth access token",required = true)
	private String accessToken;

	@ApiModelProperty(name = "refreshToken", value = "represents the oAuth refresh token",required = true)
	private String refreshToken;

	@ApiModelProperty(name = "authorizeToken", value = "represents the oAuth authorize token",required = false)
	private String authorizeToken;

	@ApiModelProperty(name = "clientId", value = "represents the oAuth client id which is normally email",required = true)
	private String clientId;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getAuthorizeToken() {
		return authorizeToken;
	}

	public void setAuthorizeToken(String authorizeToken) {
		this.authorizeToken = authorizeToken;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
