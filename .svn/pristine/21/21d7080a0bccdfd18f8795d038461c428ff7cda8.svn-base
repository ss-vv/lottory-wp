package com.xhcms.ucenter.lang;


/**
 * Title:
 * 
 * @author xulang
 * @version 1.0
 */
public enum EnumLoginType {
	USERNAME(1, "用户名登录"),
	EMAIL(2, "邮箱登录"),
	MOBILE(3, "手机登录"),
	UNKNOWN(0, "未知类型");

	private String desc;
	private int value;

	private EnumLoginType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static EnumLoginType getEnumStatus(int value) {
		EnumLoginType[] values = EnumLoginType.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getValue() == value) {
				return values[i];
			}
		}
		return UNKNOWN;
	}

	public String getDesc() {
		return desc;
	}

	public int getValue() {
		return value;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
