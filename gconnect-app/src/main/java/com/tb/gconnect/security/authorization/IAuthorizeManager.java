/*@Author Abdul-Rehman | TMB INC.
 *Revision#1: 
 */
package com.tb.gconnect.security.authorization;

import java.util.Map;

/**
 * @author Abdul
 * This is structural interface for User Authorization. It works as interface
 * adapter to plugin any custom authorization
 */
public interface IAuthorizeManager {
	/**
	 * Checks the user authorization level
	 * 
	 * @param userName
	 *            User Name
	 * @param operation
	 *            Authorized Operation
	 * @return true/false
	 */
	public Boolean isUserAuthorised(String userName, String operation);

	/**
	 * Checks the user presence level
	 * 
	 * @param userName
	 *            User Name
	 * @return true/false
	 */
	public Boolean isUserPresent(String userName);

	/**
	 * This method authorize the user with only user name and password
	 * 
	 * Note:  This method use the dependency injection to inject the session handler dependency
	 * 
	 * @param props key-value props
	 *  * @param servisInjObjt Service injection which will be further used by auth-manager
	 * @return true in case of successful authorization else false
	 */
	public abstract Boolean authorizeUser(Map<String, String> props, Object servisInjObjt) throws Exception;
	
	/**
	 * This method authorize the user with only user name and password
	 * 
	 * @param userName
	 *            User Name
	 * @param password
	 *            Password
	 * @return true in case of successful authorization else false
	 */
	public abstract Boolean authorizeUser(String userName, String password);

	/**
	 * This method authorize the user with user name, password and additional
	 * attributes
	 * 
	 * @param userName
	 *            User Name
	 * @param password
	 *            Password
	 * @param optAttributes
	 *            Optional attributes
	 * @return user id in case of successful authorization else null
	 */
	public abstract String authorizeUser(String userName, String password,
                                         Map<String, String> optAttributes);

}
