package com.tb.gconnect.security.authentication;

/**
 * @author Abdul-Rehman This is message composer class which compose the error
 *         and access token response strings either in JSON or XML string format
 *         according to the RFC6749 standard specifications
 * 
 */
public class OAuthResponseComposer {
	private static final String ERROR = "error";
	private static final String ERROR_DESCRIPTION = "error_description";
	private static final String ERROR_URI = "error_uri";
	private static final String STATE = "state";

	/**
	 * Composes the error response either in JSON or XML
	 * 
	 * @param errorCode
	 *            REQUIRED. A single ASCII [USASCII] error code from the
	 *            following:<br>
	 * 
	 *            invalid_request<br>
	 *            The request is missing a required parameter, includes an invalid
	 *            parameter value, includes a parameter more than once, or is
	 *            otherwise malformed. <br>
	 * 
	 *            unauthorized_client<br>
	 *            The client is not authorized to request an access token using this
	 *            method. <br>
	 * 
	 *            access_denied<br>
	 *            The resource owner or authorization server denied the request.
	 *            <br>
	 * 
	 *            unsupported_response_type<br>
	 *            The authorization server does not support obtaining an access
	 *            token using this method. <br>
	 * 
	 *            invalid_scope<br>
	 *            The requested scope is invalid, unknown, or malformed. <br>
	 * 
	 *            server_error<br>
	 *            The authorization server encountered an unexpected condition that
	 *            prevented it from fulfilling the request. (This error code is
	 *            needed because a 500 Internal Server Error HTTP status code cannot
	 *            be returned to the client via an HTTP redirect.) <br>
	 * 
	 *            temporarily_unavailable<br>
	 *            The authorization server is currently unable to handle the request
	 *            due to a temporary overloading or maintenance of the server. (This
	 *            error code is needed because a 503 Service Unavailable HTTP status
	 *            code cannot be returned to the client via an HTTP redirect.) <br>
	 * 
	 *            Values for the "error" parameter MUST NOT include characters
	 *            outside the set %x20-21 / %x23-5B / %x5D-7E.
	 * @param errorDescription
	 *            OPTIONAL. Human-readable ASCII [USASCII] text providing additional
	 *            information, used to assist the client developer in understanding
	 *            the error that occurred. Values for the "error_description"
	 *            parameter MUST NOT include characters outside the set %x20-21 /
	 *            %x23-5B / %x5D-7E.
	 * @param errorURI
	 *            OPTIONAL. A URI identifying a human-readable web page with
	 *            information about the error, used to provide the client developer
	 *            with additional information about the error. Values for the
	 *            "error_uri" parameter MUST conform to the URI-reference syntax and
	 *            thus MUST NOT include characters outside the set %x21 / %x23-5B /
	 *            %x5D-7E.
	 * @param state
	 *            REQUIRED if a "state" parameter was present in the client
	 *            authorization request. The exact value received from the client
	 * @param dataType
	 *            REsponse type like JSON or XML etc
	 * @return error response String
	 */
	public static final String composeErrorResponse(String errorCode, String errorDescription, String errorURI,
			String state, String dataType) throws NullPointerException, Exception {
		if (errorCode == null || errorCode.isEmpty() || dataType == null || dataType.isEmpty())
			throw new NullPointerException();
		try {
			
			final com.google.gson.JsonObject respJsonObject = new com.google.gson.JsonObject();
			respJsonObject.addProperty(ERROR, errorCode);
			if (null != errorDescription)
				respJsonObject.addProperty(ERROR_DESCRIPTION, errorDescription);
			else
				respJsonObject.addProperty(ERROR_DESCRIPTION, "");
			if (null != errorURI)
				respJsonObject.addProperty(ERROR_URI, errorURI);
			else
				respJsonObject.addProperty(ERROR_URI, "");
			if (null != state)
				respJsonObject.addProperty(STATE, state);

			return respJsonObject.toString();
			// }

		} catch (Exception e) {
			throw new Exception(e.toString());
		}

	}

	/**
	 * This method composes the access token response to send back to client
	 * 
	 * @param accessToken
	 *            REQUIRED. The access token issued by the authorization server.
	 * @param tokenType
	 *            REQUIRED. The type of the token issued as described in Section
	 *            7.1. Value is case insensitive.
	 * @param refresfToken
	 *            REQUIRED. The access token issued by the authorization server.
	 * @param accessTokenExpiryTime
	 *            RECOMMENDED. The lifetime in seconds of the access token. For
	 *            example, the value "3600" denotes that the access token will
	 *            expire in one hour from the time the response was generated. If
	 *            omitted, the authorization server SHOULD provide the expiration
	 *            time via other means or document the default value.
	 * @param state
	 *            REQUIRED if the "state" parameter was present in the client
	 *            authorization request. The exact value received from the client.
	 * @param scope
	 *            OPTIONAL, if identical to the scope requested by the client;
	 *            otherwise, REQUIRED. The scope of the access token as described by
	 *            Section 3.3.
	 * @param dataType
	 *            OPTIONAL response type either JSON or XML
	 * @return response string either in JSON or XML
	 * @throws NullPointerException
	 * @throws Exception
	 */
	public static final String composeAccessToken(String accessToken, String tokenType, String refresfToken,
			long accessTokenExpiryTime, String state, String scope, String dataType)
			throws NullPointerException, Exception {
		if (null == accessToken || accessToken.isEmpty() || null == tokenType || tokenType.isEmpty()
				|| null == refresfToken || refresfToken.isEmpty() || null == dataType || dataType.isEmpty())
			throw new NullPointerException();
		try {
			// if (dataType.equalsIgnoreCase(OAuthConstant.JSON)) {
			// com.google.gson.JsonObject jsonObject = new
			// com.google.gson.JsonObject();
			final com.google.gson.JsonObject respJsonObject = new com.google.gson.JsonObject();
			respJsonObject.addProperty(OAuthConstant.OAuthParameter.ACCESS_TOKEN, accessToken);
			respJsonObject.addProperty(OAuthConstant.OAuthParameter.TOKEN_TYPE, tokenType);
			respJsonObject.addProperty(OAuthConstant.OAuthParameter.REFRESH_TOKEN, refresfToken);
			if (0 <= accessTokenExpiryTime)
				respJsonObject.addProperty(OAuthConstant.OAuthParameter.EXPIRES_IN, accessTokenExpiryTime);
			else
				respJsonObject.addProperty(OAuthConstant.OAuthParameter.EXPIRES_IN, "");
			if (null != scope)
				respJsonObject.addProperty(OAuthConstant.OAuthParameter.SCOPE, scope);
			else
				respJsonObject.addProperty(OAuthConstant.OAuthParameter.SCOPE, "");
			if (null != state)
				respJsonObject.addProperty(STATE, state);

			return respJsonObject.toString();
			// }

		} catch (Exception e) {
			throw new Exception(e.toString());
		}

	}

}
