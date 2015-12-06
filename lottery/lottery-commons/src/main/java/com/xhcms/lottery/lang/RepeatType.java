package com.xhcms.lottery.lang;

/**
 * @desc 追号类型
 * @createTime 2013-8-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public enum RepeatType {
	
	NO_REPEAT(0, "noRepeat"),	//不追号
	REPEAT(1, "repeat"),		//追号
	REPEAT_SUITE(3, "repeatSuite");//追号套餐
	
	private int type;
	
	private String name;
	
	private RepeatType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	//根据追号类型判断是否是一个追号计划
	public static boolean isRepeatPlan(int repeatType) {
		if(repeatType == REPEAT.type ||
				repeatType == REPEAT_SUITE.type) {
			return true;
		}
		return false;
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
