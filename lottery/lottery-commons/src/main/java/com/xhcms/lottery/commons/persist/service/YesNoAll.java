package com.xhcms.lottery.commons.persist.service;

public enum YesNoAll {
	ALL(0, "全部"),
	NO(1, "无提成"),
	YES(2, "有提成");
	
	private int value;
	private String desc;
	
	private YesNoAll(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static YesNoAll getEnumByValue(Integer value) {
		if(value != null) {
			YesNoAll[] values = YesNoAll.values();
			for(int i = 0; i < values.length; i++) {
				if(values[i].getValue() == value)
					return values[i];
			}
		}
		
		return ALL;
	}
}
