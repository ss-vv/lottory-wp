/**
 * 
 */
package com.xhcms.ucenter.sso.discuz;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.CookieGenerator;

import com.xhcms.ucenter.sso.principal.RememberMeCredentials;


/**
 * @author Bean.Long
 *
 */
public class DiscuzAuthCookieGenerator extends CookieGenerator {
	private int rememberMeMaxAge = 7889231;
	
	public void addCookie(final HttpServletRequest request, 
    		final HttpServletResponse response, final String cookieValue) {
		
		String value = cookieValue;
		
		try {
			value = URLEncoder.encode(cookieValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isEmpty(request.getParameter(RememberMeCredentials.REQUEST_PARAMETER_REMEMBER_ME))) {
			super.addCookie(response, value);
		} else {
            final Cookie cookie = createCookie(value);
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
