package com.xhcms.lottery.lang;

/**
 * 用户优惠劵显示状态
 * @author Wang Lei
 *
 */
public enum VoucherUserViewStatus {
	UNUSED("未使用"),
	USED("已使用"),
	EXPIRE("已过期"),
	;
	private final String chineseName;
	
	VoucherUserViewStatus(String name){
		this.chineseName = name;
	}

	public String getChineseName() {
		return chineseName;
	}
	
}
