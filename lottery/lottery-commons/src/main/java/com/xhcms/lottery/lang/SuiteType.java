package com.xhcms.lottery.lang;


/**
 * @desc 套餐类型
 * @createTime 2013-8-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public enum SuiteType {
	
	SUITE_MONTH(1, 14, "SUITE_MONTH"),	//包月套餐
	SUITE_QUART(3, 40, "SUITE_QUART"),	//季度套餐
	SUITE_HALF(6, 79, "SUITE_HALF"),	//半年套餐
	SUITE_YEAR(12, 154, "SUITE_YEAR");	//包年套餐
	
	private int type;
	
	//对应套餐的期数
	private int issues;
	
	private String name;
	
	private SuiteType(int type, int issues, String name) {
		this.type = type;
		this.issues = issues;
		this.name = name;
	}
	
	public static SuiteType get(int type) {
		SuiteType[] arr = SuiteType.values();
		for(SuiteType suiteType : arr) {
			if(type == suiteType.type) {
				return suiteType;
			}
		}
		return null;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIssues() {
		return issues;
	}

	public void setIssues(int issues) {
		this.issues = issues;
	}
}
