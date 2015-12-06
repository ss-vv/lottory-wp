package com.xhcms.lottery.lang;

/**
 * @desc 追号计划的状态
 * @createTime 2013-8-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public enum RepeatPlanStatus {
	
	EXECUTE(0, "execute"),	//可执行
	STOPED(1, "stoped"),	//被终止了，不允许被执行
	COMPLETE(2, "complete");//执行完成
	
	private int type;
	
	private String name;
	
	private RepeatPlanStatus(int type, String name) {
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
