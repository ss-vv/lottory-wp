/**
 * 
 */
package com.unison.lottery.wap.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.CookieGenerator;

/**
 * @author bean
 *
 */
public class UserNameCookieGenerator extends CookieGenerator {
	private int rememberMeMaxAge = 7889231;
	
	public void addCookie(final HttpServletRequest request, 
    		final HttpServletResponse response,final String cookieValue) {
            final Cookie cookie = createCookie(cookieValue);
            cookie.setMaxAge(this.rememberMeMaxAge);
            if (isCookieSecure()) {
                cookie.setSecure(true);
            }
            response.addCookie(cookie);
	}
	
	public String getCookieValue(final HttpServletRequest request) {
        final Cookie cookie = org.springframework.web.util.WebUtils.getCookie(
                request, getCookieName());
        return cookie == null ? null : cookie.getValue();
	}
}
