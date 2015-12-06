/**
 * 
 */
package com.xhcms.ucenter.sso.client.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.xhcms.ucenter.sso.client.UserProfile;

/**
 * 和{@link SSOContextFilter} 配套使用，在web.xml中必须配置在SSOContextFilter后面
 * 
 * @author bean
 *
 */
public final class SSOAuthenticationFilter implements Filter {
    
    /**
     * SSO的登录URL
     */
    private String ssoLoginUrl;
    
    /**
     * 当前应用的登录URL
     */
    private String appLoginUrl;
    
	@Override
	public void init(FilterConfig config) throws ServletException {
		ssoLoginUrl = config.getInitParameter("ssoLoginUrl");
		appLoginUrl = config.getInitParameter("appLoginUrl");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	    
	    HttpServletRequest req = (HttpServletRequest)request;
	    UserProfile profile = (UserProfile)req.getSession().getAttribute(SSOConstant.SSO_USER_PROFILE);
	    
	    // 检查用户身份
	    if(profile == null || profile.isGuest()){
	        handleNotLogin(req, (HttpServletResponse)response);
	    }else{
	    	//check ticket 
	    	if(StringUtils.isNotEmpty(request.getParameter("ticket"))) {
	    		((HttpServletResponse)response).sendRedirect(retriveServiceUrl((HttpServletRequest)request, (HttpServletResponse)response, false));
	    	} else {
	    		chain.doFilter(req, response);
	    	}
	    }
	}
	
	private void handleNotLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    if(isAjaxRequest(request)){
            // Ajax请求
            PrintWriter writer = response.getWriter();
            writer.print("{\"success\":false,\"code\":\"C00001\",\"data\":\"Not Login\"}");
            writer.close();
        }else{
            response.sendRedirect(retriveRedirect(request, response, true));
        }
	}
	
	private String retriveRedirect(HttpServletRequest request, HttpServletResponse response, boolean encode){
	    if(appLoginUrl == null){
	        return ssoLoginUrl + "?referer=" + retriveServiceUrl(request, response, encode);
	    }
	    String url = appLoginUrl + "?backurl=" + retriveServiceUrl(request, response, encode);
	    
	    if(encode){
	        try {
                return ssoLoginUrl + "?referer=" + URLEncoder.encode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
	    }
	    return ssoLoginUrl + "?referer=" + url;
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
	
	private boolean isAjaxRequest(HttpServletRequest request){
	    return "ajax".equals(request.getParameter("_t"));
	}
	
	@Override
	public void destroy() {
	}
}
