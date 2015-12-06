package com.unison.lottery.weibo.exception;

/**
 * DAO异常。
 * 
 * @author Yang Bo
 */
public class DaoException extends RuntimeException {

	private static final long serialVersionUID = -7224008597968530598L;

	public DaoException() {
		super();
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

}
