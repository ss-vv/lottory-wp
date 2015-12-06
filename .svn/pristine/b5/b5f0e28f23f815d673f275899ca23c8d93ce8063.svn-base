/**
 * 
 */
package com.xhcms.ucenter.sso.authentication;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import com.xhcms.ucenter.sso.principal.IPrincipal;

/**
 * @author bean
 *
 */
public class DefaultAuthentication implements IAuthentication {
	@SuppressWarnings("unchecked")
    private Map<String, Object> attributes = Collections.EMPTY_MAP;
	
	private Date authenticatedDate;
	private IPrincipal principal;
	
	public DefaultAuthentication(IPrincipal principal, Map<String, Object> attributes) {
		this.principal = principal;
		
		if(attributes != null) {
			this.attributes = attributes;
		}
		
		authenticatedDate = Calendar.getInstance().getTime();
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}
	
	@Override
	public Date getAuthenticatedDate() {
		return authenticatedDate;
	}

	@Override
	public IPrincipal getPrincipal() {
		return principal;
	}

}
