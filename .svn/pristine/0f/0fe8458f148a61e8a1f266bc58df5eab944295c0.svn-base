/**
 * 
 */
package com.xhcms.ucenter.sso.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author bean
 *
 */
public class PassKeyUtils {
	private static final String RANDOM_KEY = "&bjradio#2008@bjradio!";
	/**
	 * 生成可以访问业务的Key
	 * @param uid
	 * @param username
	 * @return
	 */
	public static String generatePasskey(Long uid, String username)
	{
		if(uid == null)
			uid = 0L;
		
		if(username == null)
			username = "";
		
		try{
			username = URLEncoder.encode(username, "utf-8");
		}catch(UnsupportedEncodingException e){
		}
		
		String ret = StringsUtils.md5((uid >0 ? "" + uid : "") + RANDOM_KEY + username).substring( 5, 17);
		return ret;
	}
}
