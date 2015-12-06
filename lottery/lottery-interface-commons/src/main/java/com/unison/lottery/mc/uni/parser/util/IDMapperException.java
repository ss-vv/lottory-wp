package com.unison.lottery.mc.uni.parser.util;

/**
 * 转换异常
 * @author Wang Lei
 *
 */
public class IDMapperException extends Exception {
	private static final long serialVersionUID = -6891401370406022132L;

	public IDMapperException(Throwable exception){
		super(exception);
	}
	
	public IDMapperException(String msg, Throwable exception){
		super(msg, exception);
	}
	
	public IDMapperException(String msg){
		super(msg);
	}
}
