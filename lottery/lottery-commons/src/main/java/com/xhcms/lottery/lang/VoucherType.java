package com.xhcms.lottery.lang;

/**
 * 优惠劵类型
 * @author Wang Lei
 *
 */
public enum VoucherType {
	recharge("充值"),
	grant("赠款")
	;
	private final String name;
	VoucherType(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
