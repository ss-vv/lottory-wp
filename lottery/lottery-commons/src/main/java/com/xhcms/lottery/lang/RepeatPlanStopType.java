package com.xhcms.lottery.lang;

/**
 * @desc 追号计划停止类型
 * @createTime 2013-8-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public enum RepeatPlanStopType {
	
	CONTINUE(0, "continue"),		//持续执行
	PRIZED_STOP(1, "prizedStop"),	//中奖即停
	BONUS_FOR_STOP(2, "bonusForStop");//盈利达标标准
	
	private int type;
	
	private String name;
	
	private RepeatPlanStopType(int type, String name) {
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
