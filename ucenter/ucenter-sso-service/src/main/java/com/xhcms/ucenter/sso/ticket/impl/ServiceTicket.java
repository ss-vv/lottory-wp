/**
 * 
 */
package com.xhcms.ucenter.sso.ticket.impl;

import com.xhcms.ucenter.sso.ticket.AbstractTicket;
import com.xhcms.ucenter.sso.ticket.IService;

/**
 * @author bean
 *
 */
public class ServiceTicket extends AbstractTicket {
	public final static String PREFIX = "ST";
	
	private IService service;
	private String grantingTicketId;
	
	public ServiceTicket(String id, IService service) {
		setId(id);
		service.setAttribute(Service.TICKET, id);
		
		this.service = service;
		this.lastTimeUsed = System.currentTimeMillis();
		this.createTime = System.currentTimeMillis();
		this.lastPreTimeUsed = this.lastTimeUsed;
	}

	public IService getService() {
		return service;
	}

	public String getGrantingTicketId() {
		return grantingTicketId;
	}

	public void setGrantingTicketId(String grantingTicketId) {
		this.grantingTicketId = grantingTicketId;
	}
}
