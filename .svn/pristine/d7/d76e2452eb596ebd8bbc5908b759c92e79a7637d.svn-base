package com.xhcms.lottery.admin.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketPermsWithShiroFilter extends AuthorizationFilter {

	private static final Logger log = LoggerFactory.getLogger(TicketPermsWithShiroFilter.class);
	
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
		boolean isPermitted = false;
		
		HttpServletRequest httpReq = (HttpServletRequest) request;
		String url = httpReq.getRequestURI();
		if(url.endsWith("ticket/detail.do")) {
			return true;
		}
		
		String lotteryPlatformId = request.getParameter("lotteryPlatformId");
		String ticketPerm = "ticket:" + lotteryPlatformId;
		Subject subject = getSubject(request, response);
        if (subject.isPermitted(ticketPerm)) {
            isPermitted = true;
        } else {
        	log.info("user={}, url, lotteryPlatformId={},正在尝试访问没有权限的实体店出票数据!",
        			new Object[]{subject.getPrincipal(), url, lotteryPlatformId});
        }
		
        return isPermitted;
    }
	
}