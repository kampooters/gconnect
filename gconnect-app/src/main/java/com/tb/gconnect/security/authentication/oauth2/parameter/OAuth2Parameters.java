package com.tb.gconnect.security.authentication.oauth2.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.tb.gconnect.security.authentication.OAuthConstant;

/**
 * @author Abdul-Rehman <br>
 *         A data structure class that represents OAuth2.0 protocol parameters.
 *         When request parameters are submitted to this class then this class
 *         fetches and stores the oauth2.0 parameters (the parameters are
 *         managed in a key-value pair form in map)
 */
@SuppressWarnings("serial")
public class OAuth2Parameters extends HashMap<String, String> {

	private static Vector<String> paramVect = OAuthConstant.OAuthParameter
			.populateOAuth2Params();
	public static final String SCHEME = "OAuth";
	private static final String SCHEME_SPACE = SCHEME + ' ';
	private boolean duplicateParamCheck = false;// checks the duplicate header

	// parameters enteries

	/**
	 * Sets the OAuth2.0 parameter
	 * 
	 * @param paramName
	 *            parameter name
	 * @param paramValue
	 *            parameter value
	 */
	public void setParam(String paramName, String paramValue) {
		if (paramName == null || paramName.isEmpty() || paramValue == null
				|| paramValue.isEmpty())
			return;
		put(paramName, paramValue);
	}

	/**
	 * Returns the OAuth parameter if exists
	 * 
	 * @param paramName
	 *            parameter name
	 * @return String or null
	 */
	public String getParam(String paramName) {
		if (paramName == null || paramName.isEmpty())
			return null;
		return get(paramName);
	}

	/**
	 * Removes (optional) quotation marks encapsulating parameter values in the
	 * Authorization header and returns the result.
	 */
	private static String dequote(String value) {
		int length = value.length();
		return ((length >= 2 && value.charAt(0) == '"' && value
				.charAt(length - 1) == '"') ? value.substring(1, length - 1)
				: value);
	}

	public boolean checkMandatoryParam() {
		try {
			String clientId = this.get(OAuthConstant.OAuthParameter.CLIENT_ID);
			String password = this.get(OAuthConstant.OAuthParameter.PASSWORD);
			if (null == clientId || clientId.isEmpty()
					|| clientId.toLowerCase().equals("null")
					|| null == password || password.isEmpty()
					|| password.toLowerCase().equals("null"))
				return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * When request parameters are submitted to this class then this class
	 * fetches and stores the oauth2.0 parameters
	 * 
	 * @param paramMap
	 *            {@link Map} object containing request parameters
	 * @return {@link OAuth2Parameters} object
	 */
	public OAuth2Parameters readOAuth2Params(Map<String, List<String>> paramMap) {
		if (null == paramMap)
			return null;
		List<String> headers = paramMap
				.get(OAuthConstant.OAuthParameter.AUTHORIZATION_HEADER);
		if (null == headers) {
			return this;
		}

		for (String header : headers) {
			if (!header.regionMatches(true, 0, SCHEME_SPACE, 0,
					SCHEME_SPACE.length())) {
				continue;
			}
			for (String param : header.substring(SCHEME_SPACE.length()).trim()
					.split(",(?=(?:[^\"]*\"[^\"]*\")+$)")) {
				String[] nv = param.split("=", 2);
				if (nv.length != 2) {
					continue;
				}
				String key = UriComponent.decode(nv[0].trim(),
						UriComponent.Type.UNRESERVED);
				if (null == key)
					continue;
				if (!paramVect.contains(key))
					continue;
				if (null != get(key))
					this.duplicateParamCheck = true;
				String value1 = UriComponent.decode(dequote(nv[1].trim()),
						UriComponent.Type.UNRESERVED);
				put(key, value1);
			}
		}

		return this;
	}

	/**
	 * @return <code>true</code> if there are duplicate params in Authorization
	 *         header else false
	 */
	public boolean checkDuplicateHeader() {
		return this.duplicateParamCheck;
	}

}
