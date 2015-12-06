package com.unison.lottery.api.framework.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtocolUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(ProtocolUtils.class);
	public static final String MARK_4_PARSE_LINE = "::";
	public static final String SIZE_OF_ATTACHMENT = "sizeOfAttachment";

	public static String readStrings(BufferedInputStream bis) {
		String result = null;
		if (null != bis) {
			List<String> strs = new ArrayList<String>();
			String strItem;
			try {
				while ((strItem = readString(bis)) != null) {
					strs.add(strItem);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}

			if (!strs.isEmpty()) {
				StringBuffer sb = new StringBuffer();
				for (String str : strs) {
					sb.append(str);
				}
				result = sb.toString().trim();
			}
		}
		return null == result ? null : result.trim();
	}

	public static String readString(BufferedInputStream bis) throws IOException {
		String result = null;
		if (null != bis) {
			StringBuffer sb = new StringBuffer();
			char c = 0;
			int i = 0;
			while (c != 13 && (i = bis.read()) != -1) {
				c = (char) i;
				logger.debug("read a char=" + c);
				sb.append(c);
			}
			result = new String(sb.toString().getBytes("iso-8859-1"), "utf-8");
		}

		return StringUtils.isEmpty(result) ? null : result.trim();
	}

	public static String readStrings(InputStream bis) {
		String result = null;
		if (null != bis) {
			List<String> strs = new ArrayList<String>();
			String strItem;
			try {
				while ((strItem = readString(bis)) != null) {
					strs.add(strItem);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}

			if (!strs.isEmpty()) {
				StringBuffer sb = new StringBuffer();
				for (String str : strs) {
					sb.append(str);
				}
				result = sb.toString().trim();
			}
		}
		return null == result ? null : result.trim();
	}

	public static String readString(InputStream bis) throws IOException {
		String result = null;
		if (null != bis) {
			StringBuffer sb = new StringBuffer();
			char c = 0;
			int i = 0;
			while (c != 13 && (i = bis.read()) != -1) {
				c = (char) i;
				logger.debug("read a char=" + c);
				sb.append(c);
			}
			result = new String(sb.toString().getBytes("iso-8859-1"), "utf-8");
		}

		return StringUtils.isEmpty(result) ? null : result.trim();
	}

	public static String listToString(List<String> list, String separator) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1)// 当循环到最后一个的时候 就不添加分隔符
			{
				str.append(list.get(i));
			} else {
				str.append(list.get(i));
				str.append(separator);
			}
		}
		return str.toString();

	}
}
