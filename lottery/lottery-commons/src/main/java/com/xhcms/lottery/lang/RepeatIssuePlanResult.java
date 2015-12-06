package com.xhcms.lottery.lang;

/**
 * @desc 追号期计划的状态
 * @createTime 2013-8-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public enum RepeatIssuePlanResult {
	
	SUCCESS(0, "success"),	//成功，但不一定保证出票成功
	FAIL_NO_MONEY(1, "fail_no_money"),	//失败，用户资金不足
	FAIL_ISSUE_STOP(2, "fail_issue_stop"),//失败，本期停售
	FAIL_OTHER(99, "fail_other");//失败，其他原因
	
	private int type;
	
	private String name;
	
	private RepeatIssuePlanResult(int type, String name) {
		this.type = type;
		this.name = name;
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
}
