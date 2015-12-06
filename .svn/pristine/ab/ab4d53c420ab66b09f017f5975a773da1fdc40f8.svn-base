package com.xhcms.ucenter.web.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.client.UserProfile;
import com.xhcms.ucenter.sso.client.session.SSOConstant;

/**
 * @author bean
 * 
 */
public class LoginValidateInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -6692376522749130236L;

	@Autowired
	private IUserService userServiceCache;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) ctx.get(StrutsStatics.HTTP_RESPONSE);
		ServletContext servletContext = (ServletContext) ctx.get(StrutsStatics.SERVLET_CONTEXT);
		// 判断是否登录
		UserProfile profile = (UserProfile)request.getSession(true).getAttribute(SSOConstant.SSO_USER_PROFILE);
		
		
		if (profile == null || profile.isGuest()) {
			String referer = retriveServiceUrl(request, response, true);
			// 没有登录跳转到登录页
			String contextPath = servletContext.getContextPath();
			if (!StringUtils.isEmpty(contextPath) && contextPath.length() > 1) {
				response.sendRedirect(contextPath + "/login.do?referer=" + referer);
			} else {
				response.sendRedirect("/login.do?referer=" + referer);
			}
		}
		//TODO 这部分条件是暂时加上去的，防止空指针
		if (profile != null ){
			User user = userServiceCache.getUser(profile.getId());
			request.getSession().setAttribute(Constant.USER_KEY, user);
		}
		return invocation.invoke();

	}
	
	private String retriveServiceUrl(HttpServletRequest request, HttpServletResponse response, boolean encode) {
		StringBuilder sb = new StringBuilder();
		
		String serverName = request.getServerName();
		sb.append("http://").append(serverName);
		sb.append(request.getServerPort() == 80 ? "" : ":" + request.getServerPort());
		sb.append(request.getRequestURI());
		
		String requestURI = request.getQueryString();
		
		//过滤
		Map<String, String> paramsMap = new HashMap<String, String>();
		if(StringUtils.isNotBlank(requestURI)) {
			String[] params = requestURI.split("&");
			if(params.length > 0) {
				for(int i = 0; i < params.length; i++) {
					String[] nameValuseParis = params[i].split("=");
					if(nameValuseParis.length == 2 && !"ticket".equalsIgnoreCase(nameValuseParis[0])) {
						paramsMap.put(nameValuseParis[0], nameValuseParis[1]);
					}
				}
			}
		}
		
		if(paramsMap.size() > 0) {
			sb.append("?");
			Iterator<Entry<String, String>> itr = paramsMap.entrySet().iterator();
			int count = 0;
			while(itr.hasNext()) {
				Entry<String, String> entry = itr.next();
				if(count > 0) {
					sb.append("&");
				}
				sb.append(entry.getKey()).append("=").append(entry.getValue());
				count++;
			}
		}
		
		if(encode) {
			try {
				return URLEncoder.encode(sb.toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}

}