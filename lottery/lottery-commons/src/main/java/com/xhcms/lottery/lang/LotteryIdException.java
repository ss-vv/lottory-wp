package com.xhcms.lottery.lang;

/**
 * 彩种id相关异常。
 * 
 * @author Yang Bo
 */
public class LotteryIdException extends Exception {

	private static final long serialVersionUID = 5939055718963663409L;

	public LotteryIdException() {
	}

	public LotteryIdException(String message) {
		super(message);
	}

	public LotteryIdException(Throwable cause) {
		super(cause);
	}

	public LotteryIdException(String message, Throwable cause) {
		super(message, cause);
	}
}
