package com.xhcms.lottery.lang;

/**
 * @desc 追号期计划的执行状态
 * @createTime 2013-8-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public enum RepeatIssuePlanExecuteStatus {
	
	NOT_EXECUTED(false, "not_executed"),//未执行
	EXECUTED(true, "executed");			//已执行
	
	private boolean type;
	
	private String name;
	
	private RepeatIssuePlanExecuteStatus(boolean type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}
}
