/**
 * 
 */
package com.xhcms.ucenter.sso.web.action;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.sso.discuz.DiscuzSynchronizer;
import com.xhcms.ucenter.sso.ticket.IService;
import com.xhcms.ucenter.sso.ticket.ITicketRegistry;
import com.xhcms.ucenter.sso.ticket.impl.GrantingTicket;
import com.xhcms.ucenter.sso.ticket.impl.Service;
import com.xhcms.ucenter.sso.web.util.GrantingTicketCookieGenerator;

/**
 * @author bean
 * 单点退出
 */
public class LogoutAction extends BaseAction {
	private static final long serialVersionUID = -8682754010430863087L;
	
	private static final Logger logger = LoggerFactory.getLogger(LogoutAction.class);
	
	@Autowired
	private ITicketRegistry ticketRegistry;
	
	@Autowired
	private GrantingTicketCookieGenerator grantingTicketCookieGenerator;
	
	@Autowired
	private DiscuzSynchronizer discuzSynchronizer;
	
	private String referer;
	
	@Override
	public String execute() throws Exception {
		String grantingTicketId = grantingTicketCookieGenerator.getCookieValue(request);
		
		if(!StringUtils.isEmpty(grantingTicketId)) {
			GrantingTicket grantingTicket = 
				(GrantingTicket)ticketRegistry.getTicket(grantingTicketId, GrantingTicket.class);
			if(grantingTicket != null) {
				Map<String, IService> servicesMap = grantingTicket.getServices();
				Iterator<Entry<String, IService>> itr = servicesMap.entrySet().iterator();
				while(itr.hasNext()) {
					Entry<String, IService> entry = itr.next();
					try {
						IService service = entry.getValue();
						ticketRegistry.deleteTicket((String)service.getAtrribute(Service.TICKET));
						service.logOutOfService();
					} catch (Throwable exp) {
						logger.error("logout failed!", exp);
					}
				}
			}
			
			ticketRegistry.deleteTicket(grantingTicketId);
		}
		
		request.getSession().invalidate();
		grantingTicketCookieGenerator.removeCookie(response);
		
		discuzSynchronizer.syncLogoutStatus(request, response);
		
		if(StringUtils.isNotBlank(referer)){
		    response.sendRedirect(referer);
		    return NONE;
		}
		return LOGIN;
	}
	
	public void setReferer(String referer){
        this.referer = referer;
    }

}
