/**
 * 
 */
package com.xhcms.ucenter.sso.client.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author bean
 *
 */
public class SSOSessionListener implements HttpSessionListener {
	private SessionMappingStorage sessionMappingStorage;
	
	@Override
    public void sessionCreated(final HttpSessionEvent event) {
    }

	@Override
    public void sessionDestroyed(final HttpSessionEvent event) {
    	if (sessionMappingStorage == null) {
    	    sessionMappingStorage = getSessionMappingStorage();
    	}
    	
        final HttpSession session = event.getSession();
        sessionMappingStorage.removeBySessionById(session.getId());
    }

    protected static SessionMappingStorage getSessionMappingStorage() {
    	return SSOAuthentication.getSessionMappingStorage();
    }

}
