package com.xhcms.lottery.admin.web.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.admin.lang.AdminConstant;

public class AccessInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -8064595243988495218L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// ActionContext ctx = invocation.getInvocationContext();
		HttpServletResponse response = (HttpServletResponse)invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
		HttpServletRequest request = (HttpServletRequest)invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		ServletContext sc = (ServletContext)invocation.getInvocationContext().get(StrutsStatics.SERVLET_CONTEXT);
		
		Admin profile = (Admin)request.getSession().getAttribute(AdminConstant.USER);
		// 用户未登录
		if (profile == null) {
			String cp = sc.getContextPath();
			String loginUrl = "";
			
			if (StringUtils.isNotBlank(cp) && cp.length() > 1) {
				loginUrl = cp + "/login.do";
			} else {
				loginUrl = "/login.do";
			}
			StringBuilder sb = new StringBuilder();
			
			sb.append("<script type=\"text/javascript\">");
			sb.append("	if(window.parent) {");
			sb.append("		window.parent.location.href = \"" + loginUrl + "\";");
			sb.append("	} else {");	
			sb.append("		window.location.href = \"" + loginUrl + "\";");
			sb.append("	}");
			sb.append("</script>");
			
			response.getWriter().write(sb.toString());
			return Action.NONE;
		}
		return invocation.invoke();
	}

}
