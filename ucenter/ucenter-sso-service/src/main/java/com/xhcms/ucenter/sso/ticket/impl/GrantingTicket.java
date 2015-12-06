/**
 * 
 */
package com.xhcms.ucenter.sso.ticket.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.xhcms.ucenter.sso.authentication.IAuthentication;
import com.xhcms.ucenter.sso.service.UserProfile;
import com.xhcms.ucenter.sso.ticket.AbstractTicket;
import com.xhcms.ucenter.sso.ticket.IService;

/**
 * @author bean
 *
 */
public class GrantingTicket extends AbstractTicket {
	public static final String PREFIX = "GT";
	
    private IAuthentication authentication;
	private final HashMap<String, IService> services = new HashMap<String, IService>();
	
	private UserProfile userProfile;
	
	public GrantingTicket(String id) {
		setId(id);
		
		setCreateTime(System.currentTimeMillis());
		setLastTimeUsed(System.currentTimeMillis());
		
		clearCount();
	}
	
	public IAuthentication getAuthentication() {
		return authentication;
	}
	
	public void setAuthentication(IAuthentication authentication) {
		this.authentication = authentication;
	}
	
	public void addSerice(IService service) {
		services.put(service.getId(), service);
	}
	
	public Map<String, IService> getServices() {
		return Collections.unmodifiableMap(services);
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public void expire() {
		Iterator<IService> itr = services.values().iterator();
		while(itr.hasNext()) {
			IService service = itr.next();
			try {
				service.logOutOfService();
			} catch(Throwable e) {
				//TODO
			}
		}
	}
}
