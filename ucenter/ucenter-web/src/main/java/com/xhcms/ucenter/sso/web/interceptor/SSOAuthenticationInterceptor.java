/**
 * 
 */
package com.xhcms.ucenter.sso.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.sso.service.IAuthenticationManager;
import com.xhcms.ucenter.sso.ticket.IService;
import com.xhcms.ucenter.sso.ticket.impl.GrantingTicket;
import com.xhcms.ucenter.sso.ticket.impl.Service;
import com.xhcms.ucenter.sso.ticket.impl.ServiceTicket;
import com.xhcms.ucenter.sso.ticket.impl.TicketRegistryDefault;
import com.xhcms.ucenter.sso.web.util.GrantingTicketCookieGenerator;

/**
 * @author bean 认证的拦截器
 */
public final class SSOAuthenticationInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 8427881386596881854L;
	
	@Autowired
	private GrantingTicketCookieGenerator cookieGenerator;
	@Autowired
	private IAuthenticationManager authenticationManager;
	@Autowired
	private TicketRegistryDefault ticketRegistry;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		ActionContext ctx = invocation.getInvocationContext();
		
		HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx.get(StrutsStatics.HTTP_RESPONSE);
		
		IService service = extractService(request);
		
		if(service != null) {
			//一次单点登录请求
			//check cookie, 如果已经登录,生成新的Ticket
			GrantingTicket grantingTicket = loadServiceTicketFromCookie(request);
			if(grantingTicket != null && isGrantedTicket(grantingTicket.getId())) {
				grantingTicket = 
					(GrantingTicket)authenticationManager.createGrantingTicket(grantingTicket.getId(), grantingTicket.getUserProfile());
				
                if (service != null) {
                    grantingTicket.addSerice(service);
                }
                
                //TODO 需要加入过期的策略
				ServiceTicket  serviceTicket = authenticationManager.createServiceTicket(grantingTicket, service);
				response.sendRedirect(constructServiceUrl(service, serviceTicket.getId()));
			} else {
				request.getSession(true).setAttribute(Constant.SERVICE_KEY_IN_SESSION, service);
			}
		}
		
		return invocation.invoke();
	}

	private GrantingTicket loadServiceTicketFromCookie(HttpServletRequest request) {
		GrantingTicket grantingTicket = (GrantingTicket) ticketRegistry.getTicket(
				cookieGenerator.getCookieValue(request), GrantingTicket.class);
		return grantingTicket;
	}
	
	private IService extractService(HttpServletRequest request) {
		String url = cleanupUrl(request.getParameter("referer"));
		
		IService service = null;
		
		if (!StringUtils.isEmpty(url)) {
			service = new Service(url);
			// 去除URL中的jsessionid
			service.setAttribute(Service.SERVICE_URL, url);
			service.setAttribute(Service.CHECK_DATE, System.currentTimeMillis());
		}
		
		return service;
	}
	
	private String constructServiceUrl(IService service, String ticket) {
		StringBuilder sb = new StringBuilder();
		sb.append(service.getId());
		String backUrl = service.getId();
		if(backUrl.indexOf("?") > 0) {
			sb.append("&");
		} else {
			sb.append("?");
		}
		
		sb.append("ticket=").append(ticket);
		
		return sb.toString();
	}
	
	protected static String cleanupUrl(final String url) {
        if (url == null) {
            return null;
        }

        final int jsessionPosition = url.indexOf(";jsession");

        if (jsessionPosition == -1) {
            return url;
        }

        final int questionMarkPosition = url.indexOf("?");

        if (questionMarkPosition < jsessionPosition) {
            return url.substring(0, url.indexOf(";jsession"));
        }

        return url.substring(0, jsessionPosition)
            + url.substring(questionMarkPosition);
    }
	
	private boolean isGrantedTicket(String grantingTicket) {
		return true;
	}
}
