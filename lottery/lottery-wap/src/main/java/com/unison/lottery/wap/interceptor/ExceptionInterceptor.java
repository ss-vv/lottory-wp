package com.unison.lottery.wap.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xhcms.exception.XHRuntimeException;

/**
 * 拦截 XHRuntimeException 异常，显示前台消息，记录错误日志。
 * 
 * @author Yang Bo
 */
public class ExceptionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 7923039489204596134L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		try{
			return invocation.invoke();
		}catch(XHRuntimeException xhe){
			if (action instanceof ActionSupport){
				ActionSupport as = (ActionSupport)action;
				String error = as.getText("error."+xhe.getCode());
				as.addActionError(error);
			}
			logger.error("Action XHRuntimeException.", xhe);
		}
		return "exception";
	}

}
