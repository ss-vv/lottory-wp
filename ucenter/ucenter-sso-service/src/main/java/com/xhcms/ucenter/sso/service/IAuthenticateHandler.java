/**
 * 
 */
package com.xhcms.ucenter.sso.service;

import com.xhcms.ucenter.sso.principal.Credentials;

/**
 * @author bean
 *
 */
public interface IAuthenticateHandler {

	public UserProfile authenticate(Credentials credentials);

	public UserProfile loadUserProfile(UserProfile userProfile);

}
