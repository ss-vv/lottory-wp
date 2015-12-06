package com.xhcms.lottery.utils;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class TextTool implements Serializable {

	private static final long serialVersionUID = 1459047435598559437L;

	/**
	 * 隐藏字符串
	 * @param source
	 * @param start
	 * @return
	 */
	public String hideString(String source, int start) {
		if (StringUtils.isNotBlank(source) && source.length() >= start) {
			StringBuilder sb = new StringBuilder(source.length() - start);
			for (int i = 0; i < source.length() - start; i++) {
				sb.append('*');
			}
			source = source.subSequence(0, start) + sb.toString();
		}
		return source;

	}
	
	/**
	 * 转换IDL关系运算符
	 * @param idl
	 * @return
	 */
	public static String IDLConvert(String idl){
		if(StringUtils.isBlank(idl)){
			return null;
		}
		idl= idl.toUpperCase();
		if("NE".equals(idl)){
			return "!=";
		}else if("EQ".equals(idl)){
			return "=";
		}else if("GE".equals(idl)){
			return ">=";
		}else if("GT".equals(idl)){
			return ">";
		}else if("LE".equals(idl)){
			return "<=";
		}else if("LT".equals(idl)){
			return "<";
		}else if("IN".equals(idl)){
			return "IN";
		}else{
			throw new RuntimeException("IDL关系运算符转换错误！没有匹配项！idl="+idl);
		}
	}

	public static String abbreviate(String text, int maxLength){
		StringBuffer sb = new StringBuffer();
		if (text == null){
			return "";
		}
		if (maxLength < text.length()){
			sb.append(text.substring(0, Math.min(maxLength-3, text.length())));
			sb.append("...");
			return sb.toString();
		}else{
			return text;
		}
	}
}
