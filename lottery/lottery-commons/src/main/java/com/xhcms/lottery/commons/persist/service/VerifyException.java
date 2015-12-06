package com.xhcms.lottery.commons.persist.service;

import com.xhcms.lottery.commons.exception.JXRuntimeException;

/**
 * 投注时的异常。
 * 
 * @author yangbo
 */
public class VerifyException extends JXRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7284854567747628888L;

	
	

	public VerifyException(String msg, int errorCode) {
		super(msg, errorCode);
		
	}

	public VerifyException(String msg, Throwable cause) {
		super(msg, cause);
		
	}

	public VerifyException(String msg) {
		super(msg);
		
	}
	
}
