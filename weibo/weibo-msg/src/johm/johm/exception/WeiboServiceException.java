package com.unison.lottery.weibo.msg.service.johm.exception;

public class WeiboServiceException extends Exception {

	private static final long serialVersionUID = 8755180608124588349L;

	public WeiboServiceException() {
		super();
	}

	public WeiboServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeiboServiceException(String message) {
		super(message);
	}

	public WeiboServiceException(Throwable cause) {
		super(cause);
	}

}
