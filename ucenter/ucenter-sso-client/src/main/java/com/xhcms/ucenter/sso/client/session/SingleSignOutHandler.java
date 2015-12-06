package com.xhcms.ucenter.sso.client.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SingleSignOutHandler {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
    private SessionMappingStorage sessionMappingStorage = new HashMapBackedSessionMappingStorage();

    public void setSessionMappingStorage(final SessionMappingStorage storage) {
        this.sessionMappingStorage = storage;
    }

    public SessionMappingStorage getSessionMappingStorage() {
        return this.sessionMappingStorage;
    }

    public void init() {
    }
    
    public boolean isTokenRequest(final HttpServletRequest request) {
        if(log.isDebugEnabled()){
            log.debug("Method=" + request.getMethod() + 
    			", ticket=" + request.getParameter(SSOConstant.SSO_TICKET) + 
    			", uri=" + request.getRequestURI());
        }
        return "POST".equals(request.getMethod()) &&
        StringUtils.isNotBlank(request.getParameter(SSOConstant.SSO_TICKET));
    }

    public boolean isLogoutRequest(final HttpServletRequest request) {
        if(log.isDebugEnabled()){
            log.debug("Method=" + request.getMethod() + 
    			", logoutRequest=" + request.getParameter(SSOConstant.SSO_LOGOUT) + 
    			", URI=" + request.getRequestURI());
        }
    	
        return "POST".equals(request.getMethod()) &&
            StringUtils.isNotBlank(request.getParameter(SSOConstant.SSO_LOGOUT));
    }

    public void recordSession(final HttpServletRequest request) {
        final HttpSession session = request.getSession(true);
        final String token = request.getParameter(SSOConstant.SSO_TICKET);

        if (log.isDebugEnabled()) {
            log.debug("Recording session for token " + token);
        }

        try {
            this.sessionMappingStorage.removeBySessionById(session.getId());
        } catch (final Exception e) {
        	log.error(e.getMessage(), e);
        }

        sessionMappingStorage.addSessionById(token, session);
    }
   
    public void destroySession(final HttpServletRequest request) {
        final String ticket = request.getParameter(SSOConstant.SSO_TICKET);
        if (log.isTraceEnabled()) {
            log.trace ("Logout request:\n" + request.getParameter(SSOConstant.SSO_LOGOUT) +"\nTicket:" + ticket);
        }
        
        HttpSession session = null;
        
        if (StringUtils.isNotBlank(ticket)) {
            session = this.sessionMappingStorage.removeSessionByMappingId(ticket);
        }
        
        if(session == null){
            session = request.getSession(false);
        }
        
        if (session != null) {
            try {
                session.invalidate();
            } catch (final IllegalStateException e) {
                log.debug("Error invalidating session.", e);
            }
        }
    }
}