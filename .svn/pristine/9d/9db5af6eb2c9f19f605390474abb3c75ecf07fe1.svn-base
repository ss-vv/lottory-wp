package com.xhcms.lottery.pb.exception;

public class PartnerException extends Exception {
	
	private static final long serialVersionUID = -662258089135791877L;

	public PartnerException(String msg, int errorCode){
		super(msg);
		this.setErrorCode(errorCode);
	}
	public PartnerException(String msg, Throwable cause){
		super(msg,cause);
	}
	public PartnerException(String msg){
		super(msg);
	}
	
	private int errorCode;
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
