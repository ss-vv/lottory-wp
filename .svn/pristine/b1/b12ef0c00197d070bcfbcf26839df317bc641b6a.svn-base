package com.jx.commons.web.interceptor.exception;

import java.util.Locale;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;
import com.opensymphony.xwork2.inject.Container;
import com.xhcms.commons.web.struts2.interceptor.ExceptionHandler;
import com.xhcms.lottery.commons.exception.JXRuntimeException;

public class JXExceptionHandler implements ExceptionHandler, LocaleProvider {
	private TextProvider textProvider;

	public String handle(ActionInvocation invocation, Throwable t) {
		ActionContext ctx = invocation.getInvocationContext();
		int code = ((JXRuntimeException)t).getErrorCode();
		if (code != 0) {
			return getTextProvider(ctx).getText("error." + code);
		}
		return t.getMessage();
	}

	public Locale getLocale() {
		ActionContext ctx = ActionContext.getContext();
		if (ctx != null) {
			return ctx.getLocale();
		}
		return null;
	}

	private TextProvider getTextProvider(ActionContext ctx) {
		if (this.textProvider == null) {
			TextProviderFactory tpf = new TextProviderFactory();
			Container container = (Container) ctx
					.get("com.opensymphony.xwork2.ActionContext.container");
			if (container != null) {
				container.inject(tpf);
			}
			this.textProvider = tpf.createInstance(getClass(), this);
		}
		return this.textProvider;
	}

	public boolean match(Throwable t) {
		return t instanceof JXRuntimeException;
	}
}
