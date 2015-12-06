package com.xhcms.ucenter.lang;

import com.xhcms.ucenter.exception.UCException;

public enum EnumMessageType {
	SYSTEM_MESSAGE("系统消息",0);
	
	private String description;
	private int value;
	
	private EnumMessageType(String desc, int value) {
		this.description = desc;
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public int getValue() {
		return value;
	}
	
	public static EnumMessageType getEnumStatus(int type) {
		EnumMessageType[] status = EnumMessageType.values();
		for(int i = 0; i < status.length; i++) {
			if(status[i].getValue() == type) {
				return status[i];
			}
		}
		
		throw new UCException("unsupported enum code!");
	}
}
