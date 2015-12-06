package com.xhcms.lottery.commons.exception;

/**
 * 结信运行时异常类，封装了错误代码
 */
public class JXRuntimeException extends RuntimeException{
	
	private static final long serialVersionUID = 8776452102388326339L;
	private int errorCode;	// 可以用来从 resource 中获取错误描述。
	
	public JXRuntimeException(String msg, int errorCode){
		super(msg);
		this.setErrorCode(errorCode);
	}
	
	public JXRuntimeException(String msg, Throwable cause){
		super(msg, cause);
	}

	/**
	 * 用缺省（未知）errorCode 构造 BetException。
	 * @param msg 异常描述。
	 */
	public JXRuntimeException(String msg) {
		this(msg, 0);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
