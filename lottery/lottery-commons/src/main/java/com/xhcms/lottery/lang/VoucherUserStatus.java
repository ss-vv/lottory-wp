package com.xhcms.lottery.lang;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户优惠劵状态
 * @author Wang Lei
 *
 */
public enum VoucherUserStatus {
	/** 未使用 */
	UNUSED( 0, "未使用"),
	/** 锁定 */
	LOCKED( 1, "锁定"),
	/** 已使用 */
	USED( 2, "已使用"),
	;
	private final int value;
	private final String name;
	public static Map<Integer,String> voucherMap = new HashMap<Integer,String>();
	VoucherUserStatus(int value, String name){
		this.value = value;
		this.name = name;
	}

	static{
		for (VoucherUserStatus voucher : VoucherUserStatus.values()) {
			voucherMap.put(voucher.getValue(), voucher.getName());
		}
	}
	
	public static String getVoucherName(int id){
		return voucherMap.get(id);
	}
	
	public int getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
	
}
