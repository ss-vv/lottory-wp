package com.xhcms.lottery.admin.web.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.admin.lang.AdminConstant;
import com.xhcms.lottery.admin.web.action.BaseAction;

public class AccessInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -8064595243988495218L;
	private Logger log = LoggerFactory.getLogger(AccessInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = (HttpServletRequest)invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		
		Admin profile = (Admin)request.getSession().getAttribute(AdminConstant.USER);
		operateLog(request, profile, invocation.getAction());
		
		return invocation.invoke();
	}

	private void operateLog(HttpServletRequest request, Admin profile, Object action) {
		if(null == profile) return;
    	String realIP = BaseAction.getRealIP(request);
    	log.info("操作IP={},管理员={},action={}", new Object[]{
    		realIP, profile.getUsername(), action
    	});
    	Map<String, String[]> paramMap = request.getParameterMap();
    	if(null != paramMap && paramMap.size() > 0) {
    		Set<String> set = paramMap.keySet();
    		if(null != set && set.size() > 0) {
    			Iterator<String> iter = set.iterator();
    			while(iter.hasNext()) {
    				String key = iter.next();
    				String[] value = paramMap.get(key);
    				if("password".equals(key) || "pwd".equals(key)) {
    					continue;
    				}
    				log.info("key={},value={}", key, value);
    			}
    		}
    	}
	}
}