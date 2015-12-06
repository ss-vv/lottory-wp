/**
 * 
 */
package com.xhcms.ucenter.exception;

/**
 * @author bean
 *
 */
public class UCException extends RuntimeException {
	
	private static final long serialVersionUID = -7879600710110603289L;
	
	public String msgKey;
	
	public UCException(String msg) {
		super(msg);
		msgKey = null;
	}
	
	public UCException(String msgKey, String defaultMessage) {
		super(defaultMessage);
		this.msgKey = msgKey;		
	}

	public String getMsgKey() {
		return msgKey;
	}
}
