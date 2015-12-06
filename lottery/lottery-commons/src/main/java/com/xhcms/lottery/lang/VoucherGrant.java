package com.xhcms.lottery.lang;

import java.util.HashMap;
import java.util.Map;

public enum VoucherGrant {
	SCJ_10(10),
	SCJ_100(100);
	
	private Integer grant;
	public static Map<Integer,String> voucherGrantMap = new HashMap<Integer,String>();
	
	VoucherGrant(Integer grant){
		this.grant = grant;
	}
	
	static{
		for (VoucherGrant voucher : VoucherGrant.values()) {
			voucherGrantMap.put(voucher.grant, voucher.name());
		}
	}

	public Integer getGrant() {
		return grant;
	}

	public void setGrant(Integer grant) {
		this.grant = grant;
	}
	
}
