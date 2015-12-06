package com.xhcms.lottery.commons.client;

/**
 * 转换工具相关异常。
 * 
 * @author Yang Bo
 *
 */
public class TranslateException extends Exception {

	private static final long serialVersionUID = -6223111378439681613L;

	public TranslateException(String msg){
		super(msg);
	}
	
	public TranslateException(String msg, Throwable cause){
		super(msg, cause);
	}
}
