/**
 * 
 */
package com.xhcms.ucenter.sso.web.action;

import java.io.PrintWriter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.sso.ticket.ITicketRegistry;
import com.xhcms.ucenter.sso.ticket.impl.ServiceTicket;

/**
 * @author bean
 *
 */
public class ValidateTicketAction extends BaseAction {

	private static final long serialVersionUID = -1048929757234544766L;

	private String ticket;
	
	@Autowired
	private ITicketRegistry ticketRegistry;
	
	@Override
	public String execute() throws Exception {
		
		PrintWriter writer = response.getWriter();
		if(!StringUtils.isEmpty(ticket)) {
			ServiceTicket serviceTicket = (ServiceTicket)ticketRegistry.getTicket(ticket, ServiceTicket.class);
			if(serviceTicket == null || serviceTicket.isExpired()) {
				writer.write("false");
			} else {
				serviceTicket.update();
				writer.write("true");
			}
			//验证成功
		} else {
			writer.write("false");
		}
		
		return NONE;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
