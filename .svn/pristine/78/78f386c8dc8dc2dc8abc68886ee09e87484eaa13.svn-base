package com.unison.weibo.admin.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.unison.weibo.admin.commons.WeiboAdminConstants;
import com.unison.weibo.admin.model.Admin;

public class AccessInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = -6958056100781593351L;

	@Override
	protected String doIntercept(ActionInvocation actionInvoke) throws Exception {
		ActionContext ac = actionInvoke.getInvocationContext();
		ServletContext servletContext = (ServletContext) ac.get(StrutsStatics.SERVLET_CONTEXT);
		ServletResponse servletResponse = (ServletResponse) ac.get(StrutsStatics.HTTP_RESPONSE);
		
		Admin loginUser = (Admin) ac.getSession().get(WeiboAdminConstants.SES_USER);
		// 用户未登录
		if (loginUser == null) {
			servletContext.getServletContextName();
			String cp = servletContext.getContextPath();
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
			
			servletResponse.getWriter().write(sb.toString());
			return Action.NONE;
		}
		return actionInvoke.invoke();
	}

}
