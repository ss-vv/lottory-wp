package com.xhcms.lottery.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 编解码工具。
 * @author Yang Bo
 *
 */
public class EncodingUtils {

	public static String md5StringWithUTF8(String content) throws UnsupportedEncodingException{
		return DigestUtils.md5Hex(content.getBytes("UTF8"));
	}
}
