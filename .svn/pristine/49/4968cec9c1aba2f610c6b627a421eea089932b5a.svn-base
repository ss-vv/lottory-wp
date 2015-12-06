package com.xhcms.lottery.lang;

/**
 * @desc 方案类型
 * @createTime 2014-7-17
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public enum SchemeType {

	REAL("实单方案", 0), RECOMMEND("推荐方案", 1);

	private SchemeType(String name, int type) {
		this.name = name;
		this.type = type;
	}

	public static String getByValue(int value) {
		String name = null;
		for (SchemeType letteryType : SchemeType.values()) {
			if (letteryType.getType() == value) {
				name = letteryType.getName();
				break;
			}
		}
		return name;
	}

	private String name;

	private int type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}