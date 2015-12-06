package com.xhcms.lottery.account.service;

/**
 * 奖金明细相关异常。
 * 
 * @author Yang Bo
 */
public class BonusDetailException extends Exception {
	private static final long serialVersionUID = -5080366635117392392L;

	public BonusDetailException() {
		super();
	}

	public BonusDetailException(String message, Throwable cause) {
		super(message, cause);
	}

	public BonusDetailException(String message) {
		super(message);
	}

	public BonusDetailException(Throwable cause) {
		super(cause);
	}
}
