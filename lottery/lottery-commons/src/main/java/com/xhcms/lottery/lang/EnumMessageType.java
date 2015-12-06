package com.xhcms.lottery.lang;

import java.util.HashMap;
import java.util.Map;

public enum EnumMessageType {
	SYSTEM_MESSAGE("系统消息",0);
	
	private String description;
	private int value;
	public static Map<Integer,String> messageMap = new HashMap<Integer,String>();
	
	private EnumMessageType(String desc, int value) {
		this.description = desc;
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	static {
		for(EnumMessageType message : EnumMessageType.values()) {
			messageMap.put(message.getValue(), message.getDescription());
		}
	}
	
	public static String getMessageName(String value){
		return messageMap.get(value);
	}
}
