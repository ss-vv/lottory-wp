package com.xhcms.ucenter.lang;

import com.xhcms.ucenter.exception.UCException;

public enum EnumBindType {
	BIND_EMAIL("绑定邮箱", 1),
	BIND_MOBILE("绑定手机", 2);

	private String description;
	private int value;

	private EnumBindType(String desc, int value) {
		this.description = desc;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static EnumBindType getEnumStatus(int type) {
		EnumBindType[] status = EnumBindType.values();
		for (int i = 0; i < status.length; i++) {
			if (status[i].getValue() == type) {
				return status[i];
			}
		}

		throw new UCException("unsupported enum code!");
	}
}
