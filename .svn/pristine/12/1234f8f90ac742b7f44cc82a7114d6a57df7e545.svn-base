/**
 * 
 */
package com.xhcms.ucenter.lang;

import com.xhcms.ucenter.exception.UCException;

/**
 * @author bean
 * 对象删除状态枚举类型
 */
public enum EnumLoginStatus {
	STATUS_ENABLE("有效", 0),
	STATUS_DISABLE("无效", 1);
	
	private String description;
	private int value;
	
	private EnumLoginStatus(String desc, int value) {
		this.description = desc;
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public int getValue() {
		return value;
	}
	
	public static EnumLoginStatus getEnumStatus(int type) {
		EnumLoginStatus[] status = EnumLoginStatus.values();
		for(int i = 0; i < status.length; i++) {
			if(status[i].getValue() == type) {
				return status[i];
			}
		}
		
		throw new UCException("unsupported enum code!");
	}
}
