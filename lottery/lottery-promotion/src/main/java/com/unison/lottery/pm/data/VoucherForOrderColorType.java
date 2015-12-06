package com.unison.lottery.pm.data;

import java.util.HashMap;
import java.util.Map;

public enum VoucherForOrderColorType {

	SCJ_1("SCJ_1", "送彩金1", 1),
	SCJ_2("SCJ_2", "送彩金2", 2),
	SCJ_3("SCJ_3", "送彩金3", 3),
	SCJ_4("SCJ_4", "送彩金4", 4),
	SCJ_5("SCJ_5", "送彩金5", 5),
	SCJ_6("SCJ_6", "送彩金6", 6),
	SCJ_7("SCJ_7", "送彩金7", 7),
	SCJ_8("SCJ_8", "送彩金8", 8),
	SCJ_9("SCJ_9", "送彩金9", 9),
	SCJ_10("SCJ_10", "送彩金10", 10),
	
	SCJ_1_Client("SCJ_1_Client", "送彩金1", 1),
	SCJ_2_Client("SCJ_2_Client", "送彩金2", 2),
	SCJ_3_Client("SCJ_3_Client", "送彩金3", 3),
	SCJ_4_Client("SCJ_4_Client", "送彩金4", 4),
	SCJ_5_Client("SCJ_5_Client", "送彩金5", 5),
	SCJ_6_Client("SCJ_6_Client", "送彩金6", 6),
	SCJ_7_Client("SCJ_7_Client", "送彩金7", 7),
	SCJ_8_Client("SCJ_8_Client", "送彩金8", 8),
	SCJ_9_Client("SCJ_9_Client", "送彩金9", 9),
	SCJ_10_Client("SCJ_10_Client", "送彩金10", 10);
	
	public static Map<Integer,String> voucherGrantMapForALl = new HashMap<Integer,String>();
	
	public static Map<Integer,String> voucherGrantMapForClient = new HashMap<Integer,String>();
	
	static{
		for (VoucherForOrderColorType voucher : VoucherForOrderColorType.values()) {
			if(voucher.getId().endsWith("Client")) {
				voucherGrantMapForClient.put(voucher.getGrantAmount(), voucher.id);
			} else {
				voucherGrantMapForALl.put(voucher.getGrantAmount(), voucher.id);
			}
		}
	}
	
	private String id;
	
	private String name;
	
	private int grantAmount;
	
	VoucherForOrderColorType(String id, String name, int grantAmount) {
		this.id = id;
		this.name = name;
		this.grantAmount = grantAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(int grantAmount) {
		this.grantAmount = grantAmount;
	}
	
}
