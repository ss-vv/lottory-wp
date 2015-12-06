/**
 * 
 */
package com.xhcms.ucenter.sso.service;

import com.xhcms.ucenter.sso.principal.Credentials;
import com.xhcms.ucenter.sso.ticket.IService;
import com.xhcms.ucenter.sso.ticket.impl.GrantingTicket;
import com.xhcms.ucenter.sso.ticket.impl.ServiceTicket;

/**
 * @author bean
 *
 */
public interface IAuthenticationManager {
	/**
	 * 创建 ServiceTickets
	 * @param grantingTicket
	 * @param service
	 * @return
	 */
	public ServiceTicket createServiceTicket(String serviceTicketId, IService service);
	public ServiceTicket createServiceTicket(GrantingTicket grantingTicket, IService service);
	public void validateService(IService service);
	public void registSSOService(String serviceId, String name, String memo);
	
	public GrantingTicket createGrantingTicket(UserProfile userProfile);
	public GrantingTicket createGrantingTicket(String grantingTicketId, UserProfile userProfile);
	
	/**
	 * 用户信息审核
	 * @param credentials
	 * @return
	 */
	public UserProfile authenticate(Credentials credentials);
	/**
	 * 使用第三方验证用户信息
	 * @param credentials
	 * @return
	 */
	public UserProfile authenticateByOpenId(Credentials credentials);
}
