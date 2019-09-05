/*@author Usman Khan
 *Abstract Overview: This file contains constants for CE restful service
 *Revision#1: 
 */
package com.tb.gconnect.security.authentication;

import java.util.Vector;

/**
 * @author Abdul-Rehman | TMB Inc.
 * 
 *         This class contains all the constants those are used inside the
 *         authentication module restful services
 */
public final class OAuthConstant {
	public static final String JSON = "json";
	public static final String XML = "xml";

	//Password Regex exp
	public static final String VALIDATEPASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	public static final String FAILED_VALIDATION ="Secret Must Contain At Least Eight Characters, Including Uppercase, " +
			"Lowercase Letters, One Special Character And Numbers.";
	public static class OAuthParameter {
		public static final String AUTHORIZATION_HEADER = "Authorization";
		public static final String CLIENT_ID = "oauth_client_id";
		public static final String VERSION = "oauth_version";

		// OAUTH2.0 RFC6749:Authorization Code Grant variables || REQUEST
		public static final String RESPONSE_TYPE = "oauth_response_type";
		public static final String REDIRECT_URI = "oauth_redirect_uri";
		public static final String SCOPE = "oauth_scope";
		public static final String STATE = "oauth_state";
		public static final String AUTH_CODE = "oauth_code";

		// OAUTH2.0 RFC6749:Authorization Code Grant variables || RESPONSE
		public static final String GRANT_TYPE = "oauth_grant_type";
		public static final String ACCESS_TOKEN = "oauth_access_token";
		public static final String EXPIRES_IN = "oauth_expires_in";
		public static final String TOKEN_TYPE = "oauth_token_type";
		public static final String REFRESH_TOKEN = "oauth_refresh_token";
		public static final String USERNMAE = "oauth_user_name";
		public static final String PASSWORD = "oauth_password";

		public static final Vector<String> populateOAuth2Params() {
			Vector<String> paramVect = new Vector<String>();
			paramVect.add(AUTHORIZATION_HEADER);
			paramVect.add(RESPONSE_TYPE);
			paramVect.add(CLIENT_ID);
			paramVect.add(VERSION);
			paramVect.add(REDIRECT_URI);
			paramVect.add(SCOPE);
			paramVect.add(STATE);
			paramVect.add(AUTH_CODE);
			paramVect.add(GRANT_TYPE);
			paramVect.add(ACCESS_TOKEN);
			paramVect.add(EXPIRES_IN);
			paramVect.add(TOKEN_TYPE);
			paramVect.add(REFRESH_TOKEN);
			paramVect.add(PASSWORD);
			return paramVect;
		}
	}

	public static class HHTP_STATUS_CODE {
		public static final int BAD_REQUEST = 400;
		public static final int UN_AUTH = 401;
		public static final int FORBIDDEN = 403;
		public static final int RESOURCE_UNAVAILABLE = 404;
		public static final int UNMATCH_HEADERS = 406;
		public static final int OK = 200;
		public static final int RESULT = 201;
		public static final int ACCEPTED = 201;
	}

	public static final class ERROR_CODE {
		public static final String INVALID_REQUEST = "invalid_request";
		public static final String INVALID_CLIENT = "invalid_client";
		public static final String INVALID_GRANT = "invalid_grant";
		public static final String INVALID_SCOPE = "invalid_scope";
		public static final String UNAUTHORIZED_CLIENT = "unauthorized_client";
		public static final String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";
		public static final String SERVER_ERROR = "server_error";
	}

	public static final class ERROR_DESCRIPTION {
		public static final String INVALID_REQUEST = " The request is missing a required parameter or includes an unsupported parameter value";
		public static final String DUPLICATE_PARAM = " The request contains duplicate parameter entry in authorization header";
		public static final String INVALID_CLIENT = "Client authentication failed";
		public static final String INVALID_GRANT = "invalid_grant";
		public static final String INVALID_SCOPE = "The requested scope is invalid, unknown, malformed, or exceeds the scope granted by the resource owner";
		public static final String UNAUTHORIZED_CLIENT = "The authenticated client is not authorized to use this authorization grant type from WebNMS";
		public static final String UNSUPPORTED_GRANT_TYPE = "The authorization grant type is not supported by the authorization server";
		public static final String SERVER_ERROR = "The authorization server encountered an unexpected condition that prevented it from fulfilling the request.";
	}

	public static class StatusMessage {
		public static final String EMPTY_ARGUMENT = "Provided argument is empty:";
		public static final String ALREADY_EXIST = "Object of this type already exists";
		public static final String UN_AUTH_REQUEST = "Un authenticated request";
		public static final String FAIL_AUTHRIZATION = "The authenticated client is not authorized";

		public static final String INVALID_AUTHORIZE_TOKEN = "Invalid Authorize Token:";
		public static final String EXECUTION_EXCEPTION = "Exception during execution";
		public static final String PRE_AUTH_REQUEST = "Its pre authentication request, please get refresh first";
		public static final String INVALID_REQUEST = "Its invalid request";
	}
}
