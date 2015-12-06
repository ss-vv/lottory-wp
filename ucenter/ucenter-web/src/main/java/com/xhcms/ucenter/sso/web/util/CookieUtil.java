/**
 * 
 */
package com.xhcms.ucenter.sso.web.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author bean
 *
 */
public final class CookieUtil {
	private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);
	
	public final static GrantingCookie createGrantingCookie(String cookieValue) {
		if(StringUtils.isBlank(cookieValue)) {
			return null;
		}
		
		//base64 decoding;
		BASE64Decoder base64Decoder = new BASE64Decoder();
		String decoderStr = "";
		try {
			decoderStr = new String(base64Decoder.decodeBuffer(cookieValue), "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		
		String[] value = decoderStr.split("\t");
		if(value.length != 3)
			return null;
		
		GrantingCookie grantingCookie = new GrantingCookie(value[0], Long.parseLong(value[1]), value[2]);
		
		return grantingCookie;
	}
	
	public final static String createGantingCookieValue(String ticket, long userId, String username) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(ticket).append("\t");
		sb.append(userId).append("\t");
		sb.append(username);
		
		BASE64Encoder base64Encoder = new BASE64Encoder();
		String encodeStr = null;
		try {
			encodeStr = base64Encoder.encode(sb.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		
		return encodeStr;
	}
}
