/*@Author Abdul-Rehman 
 *Abstract Overview: This file
 *Revision#1: 
 */
package com.tb.gconnect.security.authentication;

import com.tb.gconnect.security.authentication.oauth2.Auth2Client;
import com.tb.gconnect.security.authentication.oauth2.Auth2Token;
import com.tb.gconnect.security.authentication.oauth2.template.OAuth2ClientI;
import com.tb.gconnect.security.authentication.oauth2.template.OAuth2TokenI;
import com.tb.gconnect.security.authorization.IAuthorizeManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.ws.rs.core.MultivaluedMap;
import java.util.*;


/**
 * @author Abdul-Rehman | TMB Inc.
 * 
 *         This is the main input-output interface for OAUTH authentication
 *         component exposing the whole necessary methods about OAUTH_TOKEN and
 *         OAUTH_CONSUMER management
 */
//@Service
public class AuthenticationManager {
	/**
	 * This class works as utility type for constants those will be used only
	 * inside AuthenticationManager
	 */
	//@Autowired
	private static OAuthMetaDataHandlerI oauthHandler;

	private static class AuthCONSTANT {
		public static IAuthorizeManager AUTHRIZE_MANAGER = null;
		public static long accessTokenExpiryTime = (60 * 30); // Time will be in
		public static String OWNER = "OWNER";
	}

	/**
	 * This method initializes the fields for AuthenticationManager. The objects
	 * only will be initialized for once with same implementation (values)
	 * 
	 * -Design-Pattern:Singleton
	 * 
	 * @param oAuthDataHandler
	 *            Implementation of {@link OAuthMetaDataHandlerI}. If
	 *            mplementation is not provided then default local storage
	 *            plugin will be used
	 * 
	 * @param AUTHRIZE_MANAGER
	 *            Implementation of {@link IAuthorizeManager}. Implementation is
	 *            Mandatory
	 * @param oAuthTokenExpiryTime
	 *            The time after which the OAuth access token will be expired.
	 *            If time is not provided then the default time (60 * 30)
	 *            seconds will be used
	 * @param oAuthOwner
	 *            OAuth owner, default value=OWNER
	 * @throws NullPointerException
	 *             Exception thrown when mandatory parameter NULL
	 * @throws Exception
	 *             Any exception during INIT process
	 */
	public static void init(OAuthMetaDataHandlerI oAuthDataHandler,
			IAuthorizeManager AUTHRIZE_MANAGER, long oAuthTokenExpiryTime,
			String oAuthOwner) throws NullPointerException, Exception {
		try {
			if (AUTHRIZE_MANAGER == null) {
				throw new NullPointerException(
						OAuthConstant.StatusMessage.EMPTY_ARGUMENT);
			}
			if (AuthCONSTANT.AUTHRIZE_MANAGER == null)
				AuthCONSTANT.AUTHRIZE_MANAGER = AUTHRIZE_MANAGER;

			if (oauthHandler.getClass().getName()
					.equals(OAuthLocalStorageHandler.class.getName())
					&& oAuthDataHandler != null)
				oauthHandler = oAuthDataHandler;

			if (AuthCONSTANT.OWNER.equals("OWNER") && oAuthOwner != null)
				AuthCONSTANT.OWNER = oAuthOwner;
			if (oAuthTokenExpiryTime > 600)
				AuthCONSTANT.accessTokenExpiryTime = oAuthTokenExpiryTime;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This method initializes the IAuthorizeManager for AuthenticationManager.
	 * The object only will be initialized for once with same implementation
	 * 
	 * Crerational-Design_Pattern Singleton
	 * 
	 * @param AUTHRIZE_MANAGER
	 *            Implementation of {@link IAuthorizeManager}. Implementation is
	 *            Mandatory
	 * @throws NullPointerException
	 *             When implementation of OAuthMetaDataHandlerI in NULL
	 * @throws Exception
	 *             When Object is already initialized
	 */
	public static void init(IAuthorizeManager AUTHRIZE_MANAGER)
			throws NullPointerException, Exception {
		try {
			if (AUTHRIZE_MANAGER == null) {
				throw new NullPointerException(
						OAuthConstant.StatusMessage.EMPTY_ARGUMENT);
			}
			if (AuthCONSTANT.AUTHRIZE_MANAGER == null)
				AuthCONSTANT.AUTHRIZE_MANAGER = AUTHRIZE_MANAGER;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This method initializes the OAuthMetaDataHandlerI for
	 * AuthenticationManager. The object only will be initialized for once with
	 * same implementation
	 * 
	 * -Design-Pattern: Singleton
	 * 
	 * @param oAuthDataHandler
	 *            Implementation of OAuthMetaDataHandlerI
	 * @throws NullPointerException
	 *             When implementation of OAuthMetaDataHandlerI in NULL
	 * @throws Exception
	 *             When Object is already initialized
	 */
//	public static void init(OAuthMetaDataHandlerI oAuthDataHandler)
//			throws NullPointerException, Exception {
//		if (oauthHandler.getClass().getName()
//				.equals(OAuthLocalStorageHandler.class.getName())
//				&& oAuthDataHandler != null)
//			oauthHandler = oAuthDataHandler;
//		else {
//			throw new Exception(OAuthConstant.StatusMessage.ALREADY_EXIST);
//		}
//	}

	public static void init(OAuthMetaDataHandlerI oAuthDataHandler)
			throws NullPointerException, Exception {
		if ( oauthHandler == null && null != oAuthDataHandler)
			oauthHandler = oAuthDataHandler;
	}
	
	
	/**
	 * This method return new request token
	 */
	public static OAuth2TokenI getAuthToken(String consumerKey,
			String secret) {
		if (consumerKey == null || consumerKey.equals("null") || consumerKey.isEmpty())
			return null;

		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (null == token) {
			token = new Auth2Token(newUUIDString(), consumerKey, null, null, null,
					null);
			token.setAccessToken(newUUIDString());
			token.setRefreshToken(newUUIDString());
		}
		if (checkTokenExpiry(token) == false) {
			token.setAccessToken(newUUIDString());
			token.setAccessTokenCreationTime(System.currentTimeMillis());

		}
		if(token.getAuthorizeToken() != null && token.getAuthorizeToken().isEmpty()) {
			token.setAuthorizeToken(newUUIDString());
		}
		if(token.getAccessToken().isEmpty()) {
			token.setAccessToken(newUUIDString());
		}
		if(token.getRefreshToken().isEmpty()) {
			token.setRefreshToken(newUUIDString());
		}
		oauthHandler.insertOrUpdateOAuthToken_DB(consumerKey,
				token);
		return token;
	}
	
	
	

	public static OAuth2ClientI getConsumer(String consumerKey) {
		if (consumerKey == null || consumerKey.isEmpty())
			return null;
		return oauthHandler.getClientByKey_DB(consumerKey);
	}

	public static OAuth2ClientI verifyConsumer(String consumerKey, String secret) {
		if (consumerKey == null || consumerKey.isEmpty())
			return null;
		OAuth2ClientI client = getConsumer(consumerKey);
		if (null == client || !new BCryptPasswordEncoder().matches(secret, client.getSecret())) {
			return null;
		}
		return client;
	}



	public static void checkTokenExpiry(String consumerKey) {
		if (consumerKey == null || consumerKey.isEmpty())
			return;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (null == token)
			return;
		if ((System.currentTimeMillis() - token.getAccessTokenCreationTime()) / 1000 >= AuthCONSTANT.accessTokenExpiryTime) {
			token.setAccessToken("");
			token.setAuthorizeToken("");
			oauthHandler.insertOrUpdateOAuthToken_DB(consumerKey,
					token);
		}
	}

	/**
	 * Checks token expiry
	 * @param token
	 * @return true if token is valid else false
	 */
	public static boolean checkTokenExpiry(OAuth2TokenI token) {
		if (token == null)
			return false;
		if ((System.currentTimeMillis() - token.getAccessTokenCreationTime()) / 1000 >= AuthCONSTANT.accessTokenExpiryTime) {
			return false;
		}
		return true;
	}

	/**
	 * Register a new consumer configured with Consumer Key.
	 * 
	 * @param consumerKey
	 *            Consumer key.
	 * @param secret
	 *            Consumer key secret.
	 * @param attributes
	 *            Additional attributes (name-values pairs - to store additional
	 *            information about the consumer, such as name, URI,
	 *            description, etc.)
	 * @return true/false
	 */
	public static OAuth2ClientI registerConsumer(String consumerKey,
			String secret, MultivaluedMap<String, String> attributes) {
		if (consumerKey == null || consumerKey.isEmpty())
			return null;
		OAuth2ClientI c = new Auth2Client(consumerKey, secret,
				AuthCONSTANT.OWNER, attributes);
		oauthHandler.insertClientByKey_DB(c);
		return c;
	}

	/**
	 * Returns a set of consumers registered by a given owner.
	 * 
	 * @param owner
	 *            Identifier of the owner that registered the consumers to be
	 *            retrieved.
	 * @return consumers registered by the owner.
	 */
	public static Set<OAuth2ClientI> getConsumersByOwner(String owner) {
		if (owner == null || owner.isEmpty())
			return null;
		Set<OAuth2ClientI> result = new HashSet<OAuth2ClientI>();
		for (OAuth2ClientI consumer : oauthHandler.getClientList()) {
			if (consumer.getOwner().equals(owner)) {
				result.add(consumer);
			}
		}
		return result;
	}

	/**
	 * Returns a list of access tokens authorized with the supplied principal
	 * name.
	 * 
	 * @param principalName
	 *            Principal name for which to retrieve the authorized tokens.
	 * @return authorized access tokens.
	 */
	public static Set<OAuth2TokenI> getAccessTokens(String principalName) {
		if (principalName == null || principalName.isEmpty())
			return null;
		Set<OAuth2TokenI> tokens = new HashSet<OAuth2TokenI>();
		for (OAuth2TokenI token : oauthHandler.getOAuthTokenList()) {
			if (principalName.equals(token.getPrincipal().getName())) {
				tokens.add(token);
			}
		}
		return tokens;
	}

	/**
	 * This method authenticate request by consumer key and request token
	 */
	public static Boolean authenticateByAccessToken(String consumerKey,
			String accesssToken) {
		if (accesssToken == null || accesssToken.isEmpty()
				|| consumerKey == null || consumerKey.isEmpty())
			return false;

		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (token != null) {
			if ((System.currentTimeMillis() - token
					.getAccessTokenCreationTime()) / 1000 >= AuthCONSTANT.accessTokenExpiryTime) {
				token.setAccessToken("");
				token.setAuthorizeToken("");
				return false;
			}
			if (token != null && token.getAccessToken().equals(accesssToken)) {
				token.setAccessTokenCreationTime(System.currentTimeMillis());// reset
																				// access
																				// token
																				// timer
				return oauthHandler.insertOrUpdateOAuthToken_DB(
						consumerKey, token);
			}
		}
		return false;
	}

	/**
	 * This method return consumer key associated with provided consumer key
	 */
	public static String getAccessTokenByConsumerKey(String consumerKey) {
		if (consumerKey == null || consumerKey.isEmpty())
			return null;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (token != null) {
			return token.getAccessToken();
		}
		return null;
	}

	/**
	 * This method return request key associated with provided consumer key
	 */
	public static String getAuthorizeTokenByConsumerKey(String consumerKey) {
		if (consumerKey == null || consumerKey.isEmpty())
			return null;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (token != null) {
			return token.getAuthorizeToken();
		}
		return null;
	}

	/**
	 * This method return refresh key associated with provided consumer key
	 */
	public static String getRefreshTokenByConsumerKey(String consumerKey) {
		if (consumerKey == null || consumerKey.isEmpty())
			return null;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (token != null) {
			return token.getRefreshToken();
		}
		return null;
	}

	/**
	 * This method return new request token
	 */
	public static String generateAuthorizeToken(String consumerKey,
			String secret) {
		if (consumerKey == null || consumerKey.isEmpty())
			return null;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (null == token) {
			token = new Auth2Token(newUUIDString(), secret, null, null, null,
					null);
			token.setAccessToken("");
			token.setRefreshToken("");
			oauthHandler.insertOrUpdateOAuthToken_DB(consumerKey,
					token);
			return token.getAuthorizeToken();
		} else {
			token.setAccessToken("");
			token.setRefreshToken("");
			token.setAuthorizeToken(newUUIDString());
			oauthHandler.insertOrUpdateOAuthToken_DB(consumerKey,
					token);
			return token.getAuthorizeToken();
		}
	}

	/**
	 * This method return new access token
	 */
	public static String generateAccessToken(String consumerKey,
			String authorizeToken) {
		if (consumerKey == null || consumerKey.isEmpty()
				|| authorizeToken == null || authorizeToken.isEmpty())
			return null;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (token != null) {
			if (token.getAuthorizeToken().equals(authorizeToken)) {
				token.setAccessToken(newUUIDString());
				token.setAuthorizeToken("");
				oauthHandler.insertOrUpdateOAuthToken_DB(
						consumerKey, token);
				return token.getAccessToken();
			}
		}
		return null;
	}

	/**
	 * This method return new access token
	 */
	public static String generateAccessToken(String userName) {
		if (userName == null || userName.isEmpty())
			return null;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(userName);
		if (token != null) {
			// if (token.getRequestToken().equals(requestToken)) {
			token.setAccessToken(newUUIDString());
			token.setAuthorizeToken("");
			oauthHandler.insertOrUpdateOAuthToken_DB(userName,
					token);
			return token.getAccessToken();
			// }
		} else {
			token = new Auth2Token(newUUIDString(), userName, null, null, null,
					null);
			token.setAuthorizeToken("");
			token.setRefreshToken("");
			oauthHandler.insertOrUpdateOAuthToken_DB(userName,
					token);
			return token.getAccessToken();
		}
	}

	/**
	 * This method return new refresh token
	 */
	public static String generateRefreshToken(String consumerKey,
			String accessToken) {
		if (consumerKey == null || consumerKey.isEmpty() || accessToken == null
				|| accessToken.isEmpty())
			return null;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (token != null) {
			if (token.getAccessToken().equals(accessToken)) {
				token.setRefreshToken(newUUIDString());
				oauthHandler.insertOrUpdateOAuthToken_DB(
						consumerKey, token);
				return token.getRefreshToken();
			}
		}
		return null;
	}

	/**
	 * This method authorize refresh token with consumer key
	 */
	private static Boolean authorizeRefreshToken(String consumerKey,
			String refreshToken) {
		if (consumerKey == null || consumerKey.isEmpty()
				|| refreshToken == null || refreshToken.isEmpty())
			return null;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (token != null) {
			if (token.getRefreshToken().equals(refreshToken)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method return new access token by providing refresh token
	 */
	public static String reGenerateAccessToken(String consumerKey,
			String refreshToken) {
		if (consumerKey == null || consumerKey.isEmpty()
				|| refreshToken == null || refreshToken.isEmpty())
			return null;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (token != null) {
			if (authorizeRefreshToken(consumerKey, refreshToken)) {
				token.setAccessToken(newUUIDString());
				oauthHandler.insertOrUpdateOAuthToken_DB(
						consumerKey, token);
				return token.getAccessToken();
			}
		}
		return null;
	}

	/**
	 * Generates a new random string (used for token/customer strings, secrets
	 * and verifier.
	 * 
	 * @return Random UUID string.
	 */
	private static String newUUIDString() {
		String tmp = UUID.randomUUID().toString();
		return tmp.replaceAll("-", "");
	}

	// @Override
	/**
	 * This returns the request token matching by the provided key
	 * 
	 * @param consumerKey
	 *            unique consumer key
	 * @return {@link OAuth2TokenI} or null
	 */
	public static OAuth2TokenI getAuthorizeToken(String consumerKey) {
		if (consumerKey == null || consumerKey.isEmpty())
			return null;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (token != null) {
			if (token.getAuthorizeToken().isEmpty() == false)
				return token;
		}
		return null;
	}

	@Deprecated
	public static OAuth2TokenI newAuthorizeToken(String consumerKey,
			String callbackUrl, Map<String, List<String>> attributes) {
		if (consumerKey == null || consumerKey.isEmpty())
			return null;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(consumerKey);
		if (token == null) {
			token = new Auth2Token(newUUIDString(), consumerKey, callbackUrl,
					null, null, null);
			oauthHandler.insertOrUpdateOAuthToken_DB(consumerKey,
					token);
			return token;
		}
		return null;
	}

	/**
	 * This returns the access token matching by the provided key
	 * 
	 * @param requestToken
	 *            Request Token
	 * @param verifier
	 *            Verifier String
	 * @return {@link OAuth2TokenI} or null
	 */
	public static OAuth2TokenI newAccessToken(OAuth2TokenI requestToken,
			String verifier) {
		if (verifier == null || verifier.isEmpty() || requestToken == null)
			return null;
		{
			requestToken.setAccessToken(newUUIDString());
			requestToken.setAuthorizeToken("");
			oauthHandler.insertOrUpdateOAuthToken_DB(
					requestToken.getClientId(), requestToken);
			return requestToken;
		}
	}

	/**
	 * This returns the access token matching by the provided key
	 * 
	 * @param consumerKey
	 *            unique consumer key
	 * @return {@link OAuth2TokenI} or null
	 */
	public static OAuth2TokenI getAccessToken(String consumerKey) {
		if (consumerKey == null || consumerKey.equals("null") || consumerKey.isEmpty())
			return null;
		return oauthHandler.getOAuthTokenByKey_DB(consumerKey);
	}

//	/**
//	 * This returns the consumer property by matching the given one if exists
//	 * any
//	 * 
//	 * @param consumerKey
//	 *            unique consumer key
//	 * @return {@link OAuth2TokenI} or null
//	 */
//	public static String getConsumerProperty(String consumerKey,
//			String propertyName) {
//		if (consumerKey == null || consumerKey.isEmpty()
//				|| propertyName == null || propertyName.isEmpty())
//			return null;
//		OAuth2ClientI consumer = oauthHandler
//				.getClientByKey_DB(consumerKey);
//		if (consumer == null)
//			return null;
//		MultivaluedMap<String, String> attribs = consumer.getAttributes();
//		if (attribs != null && attribs.size() > 0) {
//			List<String> consumerProp = attribs.get(propertyName);
//			if (consumerProp == null)
//				return null;
//			return consumerProp.get(0);
//		}
//		return null;
//
//	}

	/**
	 * This method authorize the user
	 * 
	 * @param userName
	 *            User name
	 * @param password
	 *            Password
	 * @return true in case of successful authorization else false
	 */
	public static Boolean authorizeUser(String userName, String password) {
		if (AuthCONSTANT.AUTHRIZE_MANAGER == null)
			return false;
		try {
			return AuthCONSTANT.AUTHRIZE_MANAGER.authorizeUser(userName,
					password);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/**
	 * Verifies the user presence by using the pluged in authorize manager
	 * 
	 * @param userName
	 *            user name
	 * @return true/false
	 */
	public static Boolean isUserPresent(String userName) {
		if (AuthCONSTANT.AUTHRIZE_MANAGER == null)
			return false;
		try {
			return AuthCONSTANT.AUTHRIZE_MANAGER.isUserPresent(userName);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/**
	 * Verifies the user authorize level by using the pluged in authorize
	 * manager
	 * 
	 * @param userName
	 *            user name
	 * @param operation
	 *            Operation for which you want to authorize
	 * @return true/false
	 */
	public static Boolean isUserAuthorised(String userName, String operation) {
		if (AuthCONSTANT.AUTHRIZE_MANAGER == null)
			return false;
		try {
			return AuthCONSTANT.AUTHRIZE_MANAGER.isUserAuthorised(userName,
					operation);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	/**
	 * This method authorize the user
	 * 
	 * @param userName
	 *            User Name
	 * @param password
	 *            password
	 * @param optAttributes
	 *            Optional attributes
	 * @return user id in case of successful authorization else null
	 */
	@Deprecated
	public static String authorizeUser(String userName, String password,
			Map<String, String> optAttributes) {
		if (AuthCONSTANT.AUTHRIZE_MANAGER == null)
			return null;
		try {
			return AuthCONSTANT.AUTHRIZE_MANAGER.authorizeUser(userName,
					password, optAttributes);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/**
	 * This method provides access Token's expiry time
	 * 
	 * @param clientId
	 *            Client Id
	 * @return long
	 */
	public static long getTokenExpiryTime(String clientId) {
		if (clientId == null || clientId.isEmpty())
			return 0;
		OAuth2TokenI token = oauthHandler
				.getOAuthTokenByKey_DB(clientId);
		if (null == token)
			return 0;
		long expireTime = System.currentTimeMillis()
				- token.getAccessTokenCreationTime();
		if ((expireTime) / 1000 >= AuthCONSTANT.accessTokenExpiryTime) {
			token.setAccessToken("");
			token.setAuthorizeToken("");
			oauthHandler.insertOrUpdateOAuthToken_DB(clientId,
					token);
			return 0;
		} else{
			long time1 =  (AuthCONSTANT.accessTokenExpiryTime)-(expireTime/1000);
			return time1;
		}
			

	}

}
