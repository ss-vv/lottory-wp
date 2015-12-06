package com.xhcms.lottery.pb.model;

public enum MESSAGE_TYPE {
	
	ISSUE_INFO_REQ("001"),
	ISSUE_INFO_RESP("101"),
	BET_REQ("002"),
	BET_RESP("102"),
	DRAW_TICKET_REQ("003"),
	DRAW_TICKET_RESP("103"),
	WIN_INFO_REQ("004"),
	WIN_INFO_RESP("104"),
	WITHDRAW_REQ("005"),
	WITHDRAW_RESP("105"),
	WITHDRAW_RESULT_REQ("006"),
	WITHDRAW_RESULT_RESP("106"),
	INVALID_MSG("999");
	
	private String code;
	
	private MESSAGE_TYPE(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 检测是否是该枚举类对象
	 * @param code
	 * @return
	 */
	public static boolean isMineMember(String code){
		MESSAGE_TYPE[] mt = MESSAGE_TYPE.values();
		for (int i = 0; i < mt.length; i++) {
			if(mt[i].code.equals(code)){
				return true;
			}
		}
		return false;
	}
}
