/**
 * 
 */
package com.xhcms.ucenter.sso.discuz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.util.DiscuzAuthCodeUtils;

/**
 * @author Bean.Long
 *
 */
public class DiscuzSynchronizer {
	@Autowired
	private DiscuzAuthCookieGenerator discuzAuthCookieGenerator;
	@Autowired
	private DiscuzAuthCookieGenerator discuzSaltKeyCookieGenerator;
	
	private String appKey;
	
	public void syncLoginStatus(HttpServletRequest request, HttpServletResponse response, User user) {
		//和论坛做整合,需要生成 auth cookie
		
		String auth = String.format("%s\t%s", DiscuzAuthCodeUtils.generateKey(user.getId()), user.getId());
		String saltkey = discuzSaltKeyCookieGenerator.getCookieValue(request);
		
		if(StringUtils.isEmpty(saltkey)) {
			saltkey = DiscuzAuthCodeUtils.md5(RandomUtils.nextInt() + "").substring(0, 8);
			discuzSaltKeyCookieGenerator.addCookie(response, saltkey);
		}
		
		discuzAuthCookieGenerator.addCookie(request, response, 
				DiscuzAuthCodeUtils.authcodeEncode(auth, DiscuzAuthCodeUtils.generateKey(appKey,
						saltkey)));
	}
	
	public void syncLogoutStatus(HttpServletRequest request, HttpServletResponse response) {
		discuzAuthCookieGenerator.removeCookie(response);
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
}
