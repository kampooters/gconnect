/*@author Abdul-Rehman 
 *Abstract Overview: This file will act as resource for providing request, access and refresh token to client
 *Revision#1: 
 */
package com.tb.gconnect.security.authentication;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.tb.gconnect.logger.LogManager;
import com.tb.gconnect.security.authentication.oauth2.parameter.OAuth2Parameters;

/**
 * @author Abdul-Rehman | TMB Inc.
 * 
 *         This class will act as resource for providing request, access and
 *         refresh token to client
 */
@Path("/authentication/oauth")
public class OAuth2AuthenticationResource {
	LogManager logger = new LogManager(this.getClass());
	@GET
	@Path("/access_token")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getAccessToken(@Context ContainerRequestContext httpContext) {
		// TODO Here we will do the authentication for user name/password
		// through pop up Query parameter will not be used
		try {
			logger.error(
					"REQUEST has been received on OAuth2AuthenticationResource");
			// JSONObject json = new JSONObject();
			OAuth2Parameters oAuthReqParams = new OAuth2Parameters()
					.readOAuth2Params(httpContext.getHeaders());

			if (oAuthReqParams != null) {
				String consumerKey = oAuthReqParams
						.getParam(OAuthConstant.OAuthParameter.CLIENT_ID);
				String tokenState = oAuthReqParams
						.getParam(OAuthConstant.OAuthParameter.STATE);
				String tokenScope = oAuthReqParams
						.getParam(OAuthConstant.OAuthParameter.SCOPE);
				if (null == consumerKey || consumerKey.trim().isEmpty()) {
					String erroResponse = OAuthResponseComposer
							.composeErrorResponse(
									OAuthConstant.ERROR_CODE.INVALID_REQUEST,
									OAuthConstant.ERROR_DESCRIPTION.INVALID_REQUEST,
									"", tokenState, OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(erroResponse).build();
				}

				String oAuthGrantType = oAuthReqParams.get("oauth_grant_type");
				if (oAuthGrantType == null || oAuthGrantType.isEmpty()
						|| !oAuthGrantType.equals("password")) {
					String erroResponse = OAuthResponseComposer
							.composeErrorResponse(
									OAuthConstant.ERROR_CODE.INVALID_GRANT,
									OAuthConstant.ERROR_DESCRIPTION.INVALID_GRANT,
									"", "", OAuthConstant.JSON);
					return Response.status(Response.Status.OK)
							.entity(erroResponse).build();
				}

				if (oAuthReqParams.checkMandatoryParam() == false) {
					String erroResponse = OAuthResponseComposer
							.composeErrorResponse(
									OAuthConstant.ERROR_CODE.INVALID_REQUEST,
									OAuthConstant.ERROR_DESCRIPTION.INVALID_REQUEST,
									"", "", OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(erroResponse).build();
				}

				AuthenticationManager.checkTokenExpiry(consumerKey);

				long tokenExpirTime = AuthenticationManager
						.getTokenExpiryTime(consumerKey);
				String oUserName1 = oAuthReqParams
						.getParam(OAuthConstant.OAuthParameter.CLIENT_ID);
				String oPassword = oAuthReqParams
						.get(OAuthConstant.OAuthParameter.PASSWORD);
				if (oUserName1 != null && !oUserName1.trim().isEmpty()
						&& oPassword != null && !oPassword.trim().isEmpty()) {
					if (!AuthenticationManager.authorizeUser(oUserName1,
							oPassword)) {

						String erroResponse = OAuthResponseComposer
								.composeErrorResponse(
										OAuthConstant.ERROR_CODE.UNAUTHORIZED_CLIENT,
										OAuthConstant.ERROR_DESCRIPTION.UNAUTHORIZED_CLIENT,
										"", tokenState, OAuthConstant.JSON);

						return Response.status(Response.Status.OK)
								.entity(erroResponse).build();
					}
					if (!AuthenticationManager.isUserAuthorised(oUserName1,
							"Administrative Operation")) {
						String erroResponse = OAuthResponseComposer
								.composeErrorResponse(
										OAuthConstant.ERROR_CODE.UNAUTHORIZED_CLIENT,
										OAuthConstant.ERROR_DESCRIPTION.UNAUTHORIZED_CLIENT,
										"", tokenState, OAuthConstant.JSON);

						return Response.status(Response.Status.OK)
								.entity(erroResponse).build();
					}
				} else {
					String erroResponse = OAuthResponseComposer
							.composeErrorResponse(
									OAuthConstant.ERROR_CODE.INVALID_REQUEST,
									OAuthConstant.ERROR_DESCRIPTION.INVALID_REQUEST
											+ "[name or password]", "",
									tokenState, OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(erroResponse).build();
				}
				String refreshTokenPresent = AuthenticationManager
						.getRefreshTokenByConsumerKey(consumerKey);
				String accessTokenPresent = AuthenticationManager
						.getAccessTokenByConsumerKey(consumerKey);

				if (accessTokenPresent != null && !accessTokenPresent.isEmpty()) {

					String okResponse = OAuthResponseComposer
							.composeAccessToken(accessTokenPresent, "bearer",
									refreshTokenPresent, tokenExpirTime,
									tokenState, tokenScope, OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(okResponse).build();
				}
				if (accessTokenPresent == null || accessTokenPresent.isEmpty()) {
					String acessToken = AuthenticationManager
							.generateAccessToken(consumerKey);
					String refreshToken = AuthenticationManager
							.generateRefreshToken(consumerKey, acessToken);
					if (null == acessToken && null == refreshToken) {

						String erroResponse = OAuthResponseComposer
								.composeErrorResponse(
										OAuthConstant.ERROR_CODE.INVALID_REQUEST,
										OAuthConstant.ERROR_DESCRIPTION.INVALID_REQUEST,
										"", tokenState, OAuthConstant.JSON);

						return Response.status(Response.Status.OK)
								.entity(erroResponse).build();
					}
					// TODO Here managing the consumer in authentication
					// manager
					MultivaluedMap<String, String> attributes = new MultivaluedHashMap<String, String>();
					attributes.add("name", oUserName1);
					attributes.add("password", oPassword);
					String oauth_secret = oPassword;
					AuthenticationManager.registerConsumer(consumerKey,
							oauth_secret, attributes);
					String okResponse = OAuthResponseComposer
							.composeAccessToken(acessToken, "bearer",
									refreshToken, tokenExpirTime, tokenState,
									tokenScope, OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(okResponse).build();
				} else {
					String okResponse = OAuthResponseComposer
							.composeAccessToken(accessTokenPresent, "bearer",
									refreshTokenPresent, tokenExpirTime,
									tokenState, tokenScope, OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(okResponse).build();
				}

			} else {
				String erroResponse = OAuthResponseComposer
						.composeErrorResponse(
								OAuthConstant.ERROR_CODE.INVALID_REQUEST,
								OAuthConstant.ERROR_DESCRIPTION.INVALID_REQUEST
										+ "[name or password]", "", "",
								OAuthConstant.JSON);

				return Response.status(Response.Status.OK).entity(erroResponse)
						.build();
			}

		} catch (Exception e) {
			// TODO: Logging to handle exception
			try {
				String erroResponse = OAuthResponseComposer
						.composeErrorResponse(
								OAuthConstant.ERROR_CODE.SERVER_ERROR,
								OAuthConstant.ERROR_DESCRIPTION.SERVER_ERROR,
								e.toString(), "", OAuthConstant.JSON);

				return Response.status(Response.Status.OK).entity(erroResponse)
						.build();
			} catch (Exception e2) {
				// TODO: handle exception
				return Response
						.status(Response.Status.NOT_FOUND)
						.entity(OAuthConstant.ERROR_DESCRIPTION.SERVER_ERROR
								+ e.toString()).build();
			}

		}
	}

	@GET
	@Path("/refresh_access_token")
	@Produces(MediaType.TEXT_PLAIN)
	public Response refreshAccessToken(
			@Context ContainerRequestContext httpContext) {
		// JSONObject json = new JSONObject();
		try {
			OAuth2Parameters oAuthReqParams = new OAuth2Parameters()
					.readOAuth2Params(httpContext.getHeaders());

			if (oAuthReqParams != null) {
				String consumerKey = oAuthReqParams
						.getParam(OAuthConstant.OAuthParameter.CLIENT_ID);
				String tokenState = oAuthReqParams
						.getParam(OAuthConstant.OAuthParameter.STATE);
				String tokenScope = oAuthReqParams
						.getParam(OAuthConstant.OAuthParameter.SCOPE);
				if (consumerKey.trim().isEmpty()) { // TODO Do
													// logging
													// here
					String erroResponse = OAuthResponseComposer
							.composeErrorResponse(
									OAuthConstant.ERROR_CODE.INVALID_REQUEST,
									OAuthConstant.ERROR_DESCRIPTION.INVALID_REQUEST
											+ "[clientId]", "", tokenState,
									OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(erroResponse).build();
				}
				String oAuthGrantType = oAuthReqParams.get("oauth_grant_type");
				if (oAuthGrantType == null || oAuthGrantType.isEmpty()
						|| !oAuthGrantType.equals("password")) {
					// throw new WebApplicationException(
					// LogMessage.UNMATCH_HEADER,
					// RestFulConstant.HHTP_STATUS_CODE.BAD_REQUEST);
					String erroResponse = OAuthResponseComposer
							.composeErrorResponse(
									OAuthConstant.ERROR_CODE.INVALID_GRANT,
									OAuthConstant.ERROR_DESCRIPTION.INVALID_GRANT,
									"", "", OAuthConstant.JSON);
					return Response.status(Response.Status.OK)
							.entity(erroResponse).build();
				}

				if (oAuthReqParams.checkMandatoryParam() == false) {
					String erroResponse = OAuthResponseComposer
							.composeErrorResponse(
									OAuthConstant.ERROR_CODE.INVALID_REQUEST,
									OAuthConstant.ERROR_DESCRIPTION.INVALID_REQUEST,
									"", "", OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(erroResponse).build();
				}
				AuthenticationManager.checkTokenExpiry(consumerKey);

				long tokenExpirTime = AuthenticationManager
						.getTokenExpiryTime(consumerKey);
				String refreshTokenReq = oAuthReqParams
						.get(OAuthConstant.OAuthParameter.REFRESH_TOKEN);
				String refreshTokenPresent = AuthenticationManager
						.getRefreshTokenByConsumerKey(consumerKey);
				String accessTokenPresent = AuthenticationManager
						.getAccessTokenByConsumerKey(consumerKey);
				if (accessTokenPresent != null && !accessTokenPresent.isEmpty()
						&& !accessTokenPresent.equals("")) {
					String okResponse = OAuthResponseComposer
							.composeAccessToken(accessTokenPresent, "bearer",
									refreshTokenPresent, tokenExpirTime,
									tokenState, tokenScope, OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(okResponse).build();
				}
				if (refreshTokenPresent.isEmpty()) {
					String erroResponse = OAuthResponseComposer
							.composeErrorResponse(
									OAuthConstant.ERROR_CODE.INVALID_REQUEST,
									OAuthConstant.ERROR_DESCRIPTION.INVALID_REQUEST
											+ "[refresh_token]", "",
									tokenState, OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(erroResponse).build();
				}

				if (accessTokenPresent != null
						&& (!accessTokenPresent.isEmpty()
								&& !refreshTokenPresent.isEmpty() && refreshTokenPresent
									.equals(refreshTokenReq))) {
					String okResponse = OAuthResponseComposer
							.composeAccessToken(accessTokenPresent, "bearer",
									refreshTokenPresent, tokenExpirTime,
									tokenState, tokenScope, OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(okResponse).build();
				}
				if (refreshTokenPresent != null
						&& (!refreshTokenPresent.isEmpty() && refreshTokenPresent
								.equals(refreshTokenReq))) {
					String acessToken = AuthenticationManager
							.reGenerateAccessToken(
									consumerKey,
									AuthenticationManager
											.getRefreshTokenByConsumerKey(consumerKey));
					String okResponse = OAuthResponseComposer
							.composeAccessToken(acessToken, "bearer",
									refreshTokenPresent, tokenExpirTime,
									tokenState, tokenScope, OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(okResponse).build();
				} else {
					String erroResponse = OAuthResponseComposer
							.composeErrorResponse(
									OAuthConstant.ERROR_CODE.INVALID_REQUEST,
									OAuthConstant.ERROR_DESCRIPTION.INVALID_REQUEST
											+ "[refresh_token mismatch]", "",
									tokenState, OAuthConstant.JSON);

					return Response.status(Response.Status.OK)
							.entity(erroResponse).build();
				}
			} else {
				String erroResponse = OAuthResponseComposer
						.composeErrorResponse(
								OAuthConstant.ERROR_CODE.INVALID_REQUEST,
								OAuthConstant.ERROR_DESCRIPTION.INVALID_REQUEST
										+ "[name or password]", "", "",
								OAuthConstant.JSON);

				return Response.status(Response.Status.OK).entity(erroResponse)
						.build();
			}
		} catch (Exception e) {
			// TODO: Logging to handle exception throw new
			try {
				String erroResponse = OAuthResponseComposer
						.composeErrorResponse(
								OAuthConstant.ERROR_CODE.SERVER_ERROR,
								OAuthConstant.ERROR_DESCRIPTION.SERVER_ERROR,
								e.toString(), "", OAuthConstant.JSON);

				return Response.status(Response.Status.OK).entity(erroResponse)
						.build();

			} catch (Exception e2) {
				return Response
						.status(Response.Status.NOT_FOUND)
						.entity(OAuthConstant.ERROR_DESCRIPTION.SERVER_ERROR
								+ e2.toString()).build();
			}
		}
	}

}
