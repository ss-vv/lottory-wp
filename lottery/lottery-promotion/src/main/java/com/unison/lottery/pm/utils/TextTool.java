package com.unison.lottery.pm.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

public class TextTool {
	/**
	 * 隐藏字符串
	 * @param source
	 * @param start
	 * @return
	 */
	public static String hideString(String source, int start) {
		try {
			int strlen = source.trim().getBytes("gbk").length;
			if (StringUtils.isNotBlank(source) && strlen > start) {
				byte[] sbyte = source.trim().getBytes("gbk");
				byte[] rebyte = new byte[start];
				for(int i = 0; i < start; i++) {
					rebyte[i] = sbyte[i];					
				}
				source = new String(rebyte, "gbk") + "…";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return source;

	}
	
}
