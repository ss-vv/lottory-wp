package com.unison.lottery.api.vGroupPublishScheme.exception;

public class SchemeIdOrGroupIdOrUserIdExcption extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SchemeIdOrGroupIdOrUserIdExcption(String msg){
		super(msg);
	}
	
	public SchemeIdOrGroupIdOrUserIdExcption(){
		super();
	}
}
