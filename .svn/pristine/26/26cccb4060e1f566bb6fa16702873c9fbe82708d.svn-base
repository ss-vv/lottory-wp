/**
 * 
 */
package com.xhcms.ucenter.sso.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.CookieGenerator;

import com.xhcms.ucenter.sso.principal.RememberMeCredentials;

/**
 * @author bean
 *
 */
public class GrantingTicketCookieGenerator extends CookieGenerator {
	private int rememberMeMaxAge = 7889231;
	
	public void addCookie(final HttpServletRequest request, 
    		final HttpServletResponse response, final String cookieValue) {
		if(StringUtils.isEmpty(request.getParameter(RememberMeCredentials.REQUEST_PARAMETER_REMEMBER_ME))) {
			super.addCookie(response, cookieValue);
		} else {
            final Cookie cookie = createCookie(cookieValue);
            cookie.setMaxAge(this.rememberMeMaxAge);
            if (isCookieSecure()) {
                cookie.setSecure(true);
            }
            response.addCookie(cookie);
		}
	}
	
	public String getCookieValue(final HttpServletRequest request) {
        final Cookie cookie = org.springframework.web.util.WebUtils.getCookie(
                request, getCookieName());
        return cookie == null ? null : cookie.getValue();
	}
}
