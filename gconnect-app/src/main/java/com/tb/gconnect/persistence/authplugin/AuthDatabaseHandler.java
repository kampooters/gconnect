/*@Author Abdul-Rehman
 *Abstract Overview: This file
 *Revision#1: 
 */
package com.tb.gconnect.persistence.authplugin;

import com.tb.gconnect.security.authentication.OAuthMetaDataHandlerI;
import com.tb.gconnect.persistence.authplugin.mapper.OAuthClientMapper;
import com.tb.gconnect.persistence.authplugin.mapper.OAuthTokenMapper;
import com.tb.gconnect.security.authentication.oauth2.template.OAuth2ClientI;
import com.tb.gconnect.security.authentication.oauth2.template.OAuth2TokenI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author abdul.rehman4 12th/07/2019
 *  * @version 1.0
 *  * @since v1.0
 *  This class handles the OAuth variable locally (Run time). The scope
 *         of the managed data is equal to the life cycle of the Application.
 *         Once the application is closed the whole data will be lost
 */
@Service
public class AuthDatabaseHandler implements OAuthMetaDataHandlerI {

	@Autowired
	OAuthClientMapper clientMapper;

	@Autowired
	OAuthTokenMapper tokenMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/** For detailed comment please @see {@link OAuthMetaDataHandlerI} **/
	@Override
	public OAuth2ClientI getClientByKey_DB(String clientKey) {
		List<OAuthClientPojo> list = clientMapper.getById(clientKey);
		if(null != list && list.size()>=1){
			return  list.get(0);
		}
		return  null;
	}

	/** For detailed comment please @see {@link OAuthMetaDataHandlerI} **/
	@Override
	public OAuth2ClientI insertClientByKey_DB(OAuth2ClientI client) {
		clientMapper.insert(copyData(client));
		return client;
	}

	/** For detailed comment please @see {@link OAuthMetaDataHandlerI} **/
	@Override
	public Collection<OAuth2ClientI> getClientList() {

		return null;
	}

	/** For detailed comment please @see {@link OAuthMetaDataHandlerI} **/
	@Override
	public OAuth2TokenI getOAuthTokenByKey_DB(String clientKey) {
		OAuthTokenPojo list = tokenMapper.getById(clientKey);
		if(null != list){
			return  list;
		}
		return  null;

	}

	/** For detailed comment please @see {@link OAuthMetaDataHandlerI} **/
	@Override
	public Collection<OAuth2TokenI> getOAuthTokenList()
	{
//		return tokenByClientId.values();
		return  null;
	}

	/** For detailed comment please @see {@link OAuthMetaDataHandlerI} */
	@Override
	public boolean insertOrUpdateOAuthToken_DB(String clientKey,
			OAuth2TokenI token) {
		OAuthTokenPojo tokenList = tokenMapper.getById(clientKey);
		if(null == tokenList){
			tokenMapper.insert(copyData(token));
		}else{
			tokenMapper.update(copyData(token));
		}

		return true;
	}

	@Override
	public void deleteClient(OAuthClientPojo oAuthClientPojo) {
		clientMapper.delete(oAuthClientPojo);
	}

	private OAuthTokenPojo copyData(OAuth2TokenI token){
		OAuthTokenPojo pojo = new OAuthTokenPojo();
		pojo.setAccessToken(token.getAccessToken());

		pojo.setClientId(token.getClientId());
		pojo.setRefreshToken(token.getRefreshToken());
		pojo.setAuthorizeToken(token.getAuthorizeToken());
		pojo.setCallbackUrl(token.getCallbackUrl());
		pojo.setAccessTokenCreationTime(token.getAccessTokenCreationTime());
		return pojo;
	}

	private OAuthClientPojo copyData(OAuth2ClientI client){
		OAuthClientPojo pojo = new OAuthClientPojo();
		pojo.setOwner(client.getOwner());

		pojo.setClientId(client.getId());
		pojo.setSecret(this.passwordEncoder.encode(client.getSecret()));
		return pojo;
	}

}
