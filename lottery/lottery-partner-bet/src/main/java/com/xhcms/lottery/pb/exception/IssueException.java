package com.xhcms.lottery.pb.exception;

public class IssueException extends PartnerException {

	private static final long serialVersionUID = -4579070880926419959L;
	
	public IssueException(String msg, int errorCode){
		super(msg);
	}
	public IssueException(String msg, Throwable cause){
		super(msg,cause);
	}
	public IssueException(String msg){
		super(msg);
	}
}	
