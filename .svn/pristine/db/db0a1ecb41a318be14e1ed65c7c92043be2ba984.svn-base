package com.xhcms.ucenter.sso.web.util;

import java.security.MessageDigest;

public class StringsUtils {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String md5(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {

		}
		return resultString;
	}
	
	public static String authEncode(String source, String key, long expiry) {
		int ckey_length = 4;
		key = md5(key == null ? "" : key);
		String keya = md5(key.substring(0, 15));
		String keyb = md5(key.substring(15, 15));
		String keyc = ckey_length > 0 ? "" : "";
		
		return "";
	}
	
	public static String authDecode(String source, String key, long expiry) {
		return null;
	}
}
