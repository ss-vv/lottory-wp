package com.xhcms.lottery.lang;

/**
 * @desc 微博认证用户类型
 * @author lei.li@unison.net.cn
 * @createTime 2014-7-10
 * @version 1.0
 */
public enum CertificationUserType {

	ANALYSIS_EXPERT("分析达人",1),
	EXPLOITS_EXPERT("战绩达人",2);

	private CertificationUserType(String name, int type) {
		this.name = name;
		this.type = type;
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